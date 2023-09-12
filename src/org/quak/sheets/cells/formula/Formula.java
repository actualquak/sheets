package org.quak.sheets.cells.formula;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.quak.sheets.CellPosition;
import org.quak.sheets.CellSelection;
import org.quak.sheets.SheetRegistry;
import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.NumberCell;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.quak.sheets.cells.formula.FormulaException.PARSE_EXCEPTION;
import static org.quak.sheets.cells.formula.FormulaException.TYPE_EXCEPTION;

public class Formula {
    private final Cell cellReallyNoClobber;
    private static final class FormulaEvaluator
            extends FormulaGrammarBaseVisitor<Object>
            implements FormulaGrammarVisitor<Object> {
        // yes, we do RETURN exceptions. what's the problem?
        private final Cell cellNoClobberName;
        private final SheetRegistry registry;
        FormulaEvaluator(SheetRegistry registry, Cell cell) {
            this.cellNoClobberName = cell;
            this.registry = registry;
        }
        private Object doListItem(Supplier<Object> supply,
                            BigDecimal initialValue,
                            BiFunction<BigDecimal, BigDecimal, Object> output) {
            var value = initialValue;
            for(var c = supply.get(); c != null; c = supply.get()) {
                if(c instanceof BigDecimal bd) {
                    var q = output.apply(value, bd);
                    if(q instanceof BigDecimal bd2) value = bd2;
                    else return FormulaException.TYPE_EXCEPTION;
                } else if(c instanceof CellSelection cs) {
                    var csit = cs.iterator();
                    while(csit.hasNext()) {
                        var qbd = atCell(registry.at(csit.next()));
                        if(qbd instanceof BigDecimal bd) {
                            var q = output.apply(value, bd);
                            if(q instanceof BigDecimal bd2) value = bd2;
                            else return FormulaException.TYPE_EXCEPTION;
                        } else return FormulaException.TYPE_EXCEPTION;
                    }
                } else if(c instanceof FormulaException fs) return fs;
                else return FormulaException.TYPE_EXCEPTION;
            }
            return value;
        }
        private Object callBd(Object bd,
                              Function<BigDecimal, Object> f) {
            if (bd instanceof BigDecimal bd1) return f.apply(bd1);
            else if (bd instanceof FormulaException e) return e;
            else return FormulaException.TYPE_EXCEPTION;
        }
        private final MathContext mc = new MathContext(10,
                RoundingMode.HALF_UP);
        private Object atCell(Cell c) {
            if(c.equals(cellNoClobberName))
                // this doesn't catch mutual recursion
                return FormulaException.RECURSIVE_FORMULA_EXCEPTION;
            if(c instanceof NumberCell n) return n.num;
            else if(c instanceof FormulaCell f) try {
                return new BigDecimal(f.displayed());
            } catch(NumberFormatException e) {
                return TYPE_EXCEPTION;
            } else if(c instanceof DummyCell) return BigDecimal.ZERO;
            else return TYPE_EXCEPTION;
        }
        @Override public Object
        visitStartRule(FormulaGrammarParser.StartRuleContext ctx) {
            return visit(ctx.formula());
        }
        @Override public Object
        visitFormula(FormulaGrammarParser.FormulaContext ctx) {
            return visit(ctx.numeric_expression());
        }
        @Override public Object visitSum(FormulaGrammarParser.SumContext ctx) {
            var it = ctx.cells().iterator();
            return doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, BigDecimal.ZERO, (bd1, bd2) -> bd1.add(bd2, mc));
        }
        @Override public Object
        visitProd(FormulaGrammarParser.ProdContext ctx) {
            var it = ctx.cells().iterator();
            return doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, BigDecimal.ONE, (bd1, bd2) -> bd1.multiply(bd2, mc));
        }
        @Override public Object
        visitMean(FormulaGrammarParser.MeanContext ctx) {
            var ref = new Object() {
                int count = 0;
            };
            var it = ctx.cells().iterator();
            var l = doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, BigDecimal.ZERO, (bd1, bd2) -> {
                ref.count++;
                return bd1.add(bd2, mc);
            });
            if(l instanceof BigDecimal bd)
                return bd.divide(BigDecimal.valueOf(ref.count), mc);
            else return l;
        }
        @Override public Object
        visitStddev(FormulaGrammarParser.StddevContext ctx) {
            var ref = new Object() {
                int count = 0;
            };
            var it = ctx.cells().iterator();
            var l = doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, BigDecimal.ZERO, (bd1, bd2) -> {
                ref.count++;
                return bd1.add(bd2, mc);
            });
            if(!(l instanceof BigDecimal lbd)) return l;
            var mean = lbd.divide(BigDecimal.valueOf(ref.count), mc);
            var meanSquared = mean.pow(2, mc);
            var it2 = ctx.cells().iterator();
            var l2 = doListItem(() -> {
                if(!it2.hasNext()) return null;
                return visit(it2.next());
            }, BigDecimal.ZERO, (bd1, bd2) -> bd1.add(bd2.pow(2, mc), mc));
            if(!(l2 instanceof BigDecimal l2bd)) return l2;
            var squaredMean = l2bd.divide(BigDecimal.valueOf(ref.count), mc);
            var variance = squaredMean.subtract(meanSquared, mc);
            return variance.sqrt(mc);
        }
        @Override public Object visitMax(FormulaGrammarParser.MaxContext ctx) {
            var it = ctx.cells().iterator();
            return doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, null, (bd1, bd2) -> bd1 == null ? bd2 : bd1.min(bd2));
        }
        @Override public Object visitMin(FormulaGrammarParser.MinContext ctx) {
            var it = ctx.cells().iterator();
            return doListItem(() -> {
                if(!it.hasNext()) return null;
                return visit(it.next());
            }, null, (bd1, bd2) -> bd1 == null ? bd2 : bd1.min(bd2));
        }
        @Override public Object visitSub(FormulaGrammarParser.SubContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    bd1 -> callBd(visit(ctx.numeric_expression(1)),
                            bd2 -> bd1.subtract(bd2, mc)));
        }
        @Override public Object visitDiv(FormulaGrammarParser.DivContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    bd1 -> callBd(visit(ctx.numeric_expression(1)),
                            bd2 -> bd1.divide(bd2, mc)));
        }
        @Override public Object
        visitSqrt(FormulaGrammarParser.SqrtContext ctx) {
            return callBd(visit(ctx.numeric_expression()),
                    b -> b.sqrt(mc));
        }
        @Override public Object
        visitRound(FormulaGrammarParser.RoundContext ctx) {
            return callBd(visit(ctx.numeric_expression()),
                    b -> b.setScale(0, RoundingMode.HALF_UP));
        }
        @Override public Object visitAbs(FormulaGrammarParser.AbsContext ctx) {
            return callBd(visit(ctx.numeric_expression()),
                    b -> b.abs(mc));
        }
        @Override public Object
        visitIfgtz(FormulaGrammarParser.IfgtzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) > 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitIfltz(FormulaGrammarParser.IfltzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) < 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitIfgteqz(FormulaGrammarParser.IfgteqzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) >= 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitIflteqz(FormulaGrammarParser.IflteqzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) <= 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitIfeqz(FormulaGrammarParser.IfeqzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) == 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitIfneqz(FormulaGrammarParser.IfneqzContext ctx) {
            return callBd(visit(ctx.numeric_expression(0)),
                    b -> b.compareTo(BigDecimal.ZERO) != 0
                            ? callBd(visit(ctx.numeric_expression(1)),
                            b2 -> b2)
                            : callBd(visit(ctx.numeric_expression(2)),
                            b2 -> b2));
        }
        @Override public Object
        visitNumber(FormulaGrammarParser.NumberContext ctx) {
            return new BigDecimal(ctx.getText());
        }
        @Override public Object
        visitCell(FormulaGrammarParser.CellContext ctx) {
            var p = CellPosition.valueOf(ctx.CELL().getText());
            var c = registry.at(p);
            return atCell(c);
        }
        @Override public Object
        visitCellRange(FormulaGrammarParser.CellRangeContext ctx) {
            return CellSelection.makeSelection(
                    CellPosition.valueOf(ctx.CELL(0).getText()),
                    CellPosition.valueOf(ctx.CELL(1).getText()));
        }
        @Override public Object
        visitExpr(FormulaGrammarParser.ExprContext ctx) {
            return visit(ctx.numeric_expression());
        }
    }

    private static final class FormulaPrinter
            extends FormulaGrammarBaseVisitor<String>
            implements FormulaGrammarVisitor<String> {
        // TODO: relative positions
        private String cell(TerminalNode ctx) {
            return clean(ctx.getText());
        }
        private String clean(String text) {
            return text.replaceAll(" \t\r\n", "").toUpperCase();
        }
        @Override public String
        visitStartRule(FormulaGrammarParser.StartRuleContext ctx) {
            return visit(ctx.formula());
        }
        @Override public String
        visitFormula(FormulaGrammarParser.FormulaContext ctx) {
            return '=' + visit(ctx.numeric_expression());
        }
        @Override public String visitSum(FormulaGrammarParser.SumContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "SUM[", "]"));
        }
        @Override public String
        visitProd(FormulaGrammarParser.ProdContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "PROD[", "]"));
        }
        @Override public String
        visitMean(FormulaGrammarParser.MeanContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "MEAN[", "]"));
        }
        @Override public String
        visitStddev(FormulaGrammarParser.StddevContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "STDDEV[", "]"));
        }
        @Override public String visitMax(FormulaGrammarParser.MaxContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "MAX[", "]"));
        }
        @Override public String visitMin(FormulaGrammarParser.MinContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "MAX[", "]"));
        }
        @Override public String visitSub(FormulaGrammarParser.SubContext ctx) {
            return "SUB[" + visit(ctx.numeric_expression(0))
                    + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String visitDiv(FormulaGrammarParser.DivContext ctx) {
            return "DIV[" + visit(ctx.numeric_expression(0))
                    + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String
        visitSqrt(FormulaGrammarParser.SqrtContext ctx) {
            return "SQRT[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String
        visitRound(FormulaGrammarParser.RoundContext ctx) {
            return "ROUND[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String visitAbs(FormulaGrammarParser.AbsContext ctx) {
            return "ABS[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String
        visitIfgtz(FormulaGrammarParser.IfgtzContext ctx) {
            return "IFGTZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitIfltz(FormulaGrammarParser.IfltzContext ctx) {
            return "IFLTZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitIfgteqz(FormulaGrammarParser.IfgteqzContext ctx) {
            return "IFGTEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitIflteqz(FormulaGrammarParser.IflteqzContext ctx) {
            return "IFLTEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitIfeqz(FormulaGrammarParser.IfeqzContext ctx) {
            return "IFEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitIfneqz(FormulaGrammarParser.IfneqzContext ctx) {
            return "IFNEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String
        visitNumber(FormulaGrammarParser.NumberContext ctx) {
            return clean(ctx.NUMBER().getText());
        }
        @Override public String
        visitCell(FormulaGrammarParser.CellContext ctx) {
            return cell(ctx.CELL());
        }
        @Override public String
        visitCellRange(FormulaGrammarParser.CellRangeContext ctx) {
            return cell(ctx.CELL(0)) + ':' + cell(ctx.CELL(1));
        }
        @Override public String
        visitExpr(FormulaGrammarParser.ExprContext ctx) {
            return visit(ctx.numeric_expression());
        }
        @Override public String visit(ParseTree tree) {
            return clean(super.visit(tree));
        }
    }
    final FormulaGrammarParser.FormulaContext ctx;
    final SheetRegistry registry;
    public Formula(String formula, SheetRegistry registry, Cell cell)
            throws FormulaException {
        this.cellReallyNoClobber = cell;
        this.registry = registry;
        FormulaGrammarParser.FormulaContext ctx1;
        var lexer = new FormulaGrammarLexer(CharStreams.fromString(formula));
        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
        var parser = new FormulaGrammarParser(new CommonTokenStream(lexer));
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.setErrorHandler(new BailErrorStrategy());
        try {
            ctx1 = parser.formula();
        } catch (ParseCancellationException e) {
            throw PARSE_EXCEPTION;
        }
        ctx = ctx1;
    }
    public String value() {
        var p = new FormulaPrinter();
        return p.visit(ctx);
    }
    public String displayed() {
        var e = new FormulaEvaluator(registry, this.cellReallyNoClobber);
        var o = e.visit(ctx);
        if (o instanceof FormulaException fe) return fe.getMessage();
        else if (o instanceof BigDecimal bd) return bd.toPlainString();
        else return o.toString();
    }
}
