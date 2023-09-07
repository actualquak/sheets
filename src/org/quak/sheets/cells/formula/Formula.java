package org.quak.sheets.cells.formula;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.quak.sheets.CellPosition;

import java.util.stream.Collectors;

import static org.quak.sheets.cells.formula.FormulaException.PARSE_EXCEPTION;

public class Formula {
    private static final class FormulaPrinter extends FormulaGrammarBaseVisitor<String>
            implements FormulaGrammarVisitor<String> {
        // TODO: relative positions
        private String cell(TerminalNode ctx) {
            return clean(ctx.getText());
        }
        private String clean(String text) {
            return text.replaceAll(" \t\r\n", "").toUpperCase();
        }
        @Override public String visitStartRule(FormulaGrammarParser.StartRuleContext ctx) {
            return visit(ctx.formula());
        }
        @Override public String visitFormula(FormulaGrammarParser.FormulaContext ctx) {
            return '=' + visit(ctx.numeric_expression());
        }
        @Override public String visitSum(FormulaGrammarParser.SumContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "SUM[", "]"));
        }
        @Override public String visitProd(FormulaGrammarParser.ProdContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "PROD[", "]"));
        }
        @Override public String visitMean(FormulaGrammarParser.MeanContext ctx) {
            return ctx.cells().stream()
                    .map(this::visit)
                    .collect(Collectors.joining(",", "MEAN[", "]"));
        }
        @Override public String visitStddev(FormulaGrammarParser.StddevContext ctx) {
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
            return "SUB[" + visit(ctx.numeric_expression(0)) + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String visitDiv(FormulaGrammarParser.DivContext ctx) {
            return "DIV[" + visit(ctx.numeric_expression(0)) + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String visitSqrt(FormulaGrammarParser.SqrtContext ctx) {
            return "SQRT[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String visitRound(FormulaGrammarParser.RoundContext ctx) {
            return "ROUND[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String visitAbs(FormulaGrammarParser.AbsContext ctx) {
            return "ABS[" + visit(ctx.numeric_expression()) + "]";
        }
        @Override public String visitIfgtz(FormulaGrammarParser.IfgtzContext ctx) {
            return "IFGTZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitIfltz(FormulaGrammarParser.IfltzContext ctx) {
            return "IFLTZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitIfgteqz(FormulaGrammarParser.IfgteqzContext ctx) {
            return "IFGTEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitIflteqz(FormulaGrammarParser.IflteqzContext ctx) {
            return "IFLTEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitIfeqz(FormulaGrammarParser.IfeqzContext ctx) {
            return "IFEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitIfneqz(FormulaGrammarParser.IfneqzContext ctx) {
            return "IFNEQZ[" + visit(ctx.numeric_expression(0))
                    + "]THEN[" + visit(ctx.numeric_expression(1))
                    + "]ELSE[" + visit(ctx.numeric_expression(2)) + "]";
        }
        @Override public String visitLookup(FormulaGrammarParser.LookupContext ctx) {
            return "LOOKUP[" + visit(ctx.numeric_expression(0))
                    + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String visitVlookup(FormulaGrammarParser.VlookupContext ctx) {
            return "VLOOKUP[" + cell(ctx.CELL())
                    + "," + visit(ctx.numeric_expression(0))
                    + "," + visit(ctx.numeric_expression(1)) + "]";
        }
        @Override public String visitNumber(FormulaGrammarParser.NumberContext ctx) {
            return clean(ctx.NUMBER().getText());
        }
        @Override public String visitCell(FormulaGrammarParser.CellContext ctx) {
            return cell(ctx.CELL());
        }
        @Override public String visitCellRange(FormulaGrammarParser.CellRangeContext ctx) {
            return cell(ctx.CELL(0)) + ':' + cell(ctx.CELL(1));
        }
        @Override public String visitExpr(FormulaGrammarParser.ExprContext ctx) {
            return visit(ctx.numeric_expression());
        }
        @Override public String visit(ParseTree tree) {
            return clean(super.visit(tree));
        }
    }

    final FormulaGrammarParser.FormulaContext ctx;
    final CellPosition originalCell;
    public Formula(String formula, CellPosition originalCell) throws FormulaException {
        this.originalCell = originalCell;
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
    public String displayed() throws FormulaException {
        return "NotYetImplemented";
    }
}
