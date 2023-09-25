package org.quak.sheets.cells.formula;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.cells.Cell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FormulaCell extends Cell {
    // Cell that contains a formula

    // Original formula
    final String originalFormula;
    // Parsed formula
    Formula formula;
    // Constructor
    public FormulaCell(String formula, SheetRegistry registry) {
        this.originalFormula = formula;
        try {
            this.formula = new Formula(formula, registry, this);
        } catch(FormulaException e) {
            this.formula = null; // Fallback to displaying "SYNTAX ERROR"
        }
    }
    // Load formula from a file
    public static FormulaCell load(DataInputStream ds, SheetRegistry registry)
            throws IOException {
        return new FormulaCell(ds.readUTF(), registry);
    }
    // Get displayed value of formula
    public String displayed() {
        if(formula != null) return formula.displayed();
        else return "PARSER ERROR";
    }
    // Get value of formula
    public String value() {
        if(formula != null) return formula.value();
        else return originalFormula;
    }
    // Write formula to a file
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(value());
    }
}
