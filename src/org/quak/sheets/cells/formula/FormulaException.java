package org.quak.sheets.cells.formula;

public class FormulaException extends Exception {
    private FormulaException(String reason) { super(reason); }
    public static final FormulaException TYPE_EXCEPTION =
            new FormulaException("TYPE ERROR");
    public static final FormulaException PARSE_EXCEPTION =
            new FormulaException("SYNTAX ERROR");
    public static final FormulaException NOT_IMPLEMENTED_EXCEPTION =
            new FormulaException("NOT IMPLEMENTED");
    public static final FormulaException RECURSIVE_FORMULA_EXCEPTION =
            new FormulaException("RECURSIVE FORMULA");
}
