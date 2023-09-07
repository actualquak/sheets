package org.quak.sheets.cells.formula;

import org.quak.sheets.CellPosition;
import org.quak.sheets.cells.Cell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FormulaCell extends Cell {
    private static final byte VALID_CELL = 1;
    private static final byte INVALID_CELL = 2;
    final String originalFormula;
    final Formula formula;
    FormulaException ex;
    public FormulaCell(String formula, CellPosition pos) {
        Formula formula1;
        this.originalFormula = formula;
        try {
            formula1 = new Formula(formula, pos);
        } catch(FormulaException e) {
            formula1 = null;
        }
        this.formula = formula1;
    }
    public static FormulaCell load(DataInputStream ds, CellPosition pos)
            throws IOException {
        return new FormulaCell(ds.readUTF(), pos);
    }
    public String displayed() {
        if(formula != null) {
            try {
                return formula.displayed();
            } catch(FormulaException e) {
                return e.getMessage();
            }
        }
        else return "PARSER ERROR";
    }
    public String value() {
        if(formula != null) return formula.value();
        else return originalFormula;
    }
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(value());
    }
}
