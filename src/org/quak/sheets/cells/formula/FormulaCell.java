package org.quak.sheets.cells.formula;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.cells.Cell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FormulaCell extends Cell {
    private static final byte VALID_CELL = 1;
    private static final byte INVALID_CELL = 2;
    final String originalFormula;
    Formula formula;
    FormulaException ex;
    public FormulaCell(String formula, SheetRegistry registry) {
        this.originalFormula = formula;
        try {
            this.formula = new Formula(formula, registry, this);
        } catch(FormulaException e) {
            this.formula = null;
        }
    }
    public static FormulaCell load(DataInputStream ds, SheetRegistry registry)
            throws IOException {
        return new FormulaCell(ds.readUTF(), registry);
    }
    public String displayed() {
        if(formula != null) return formula.displayed();
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
