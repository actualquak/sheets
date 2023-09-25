package org.quak.sheets.cells.formula;

public class FormulaException extends Exception {
    // Exceptions that are thrown or (more often) returned by different
    // functions in this module

    // Constructor - marked private
    private FormulaException(String reason) { super(reason); }

    // Invalid/incorrect type given in formula
    public static final FormulaException TYPE_EXCEPTION =
            new FormulaException("TYPE ERROR");
    // Invalid syntax for formula
    public static final FormulaException PARSE_EXCEPTION =
            new FormulaException("SYNTAX ERROR");
    // Formula references itself
    // (no asymptomatic self-referencing here, unlike the real excel)
    public static final FormulaException RECURSIVE_FORMULA_EXCEPTION =
            new FormulaException("RECURSIVE FORMULA");
}
