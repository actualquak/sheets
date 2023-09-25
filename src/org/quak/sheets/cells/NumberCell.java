package org.quak.sheets.cells;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class NumberCell extends Cell {
    // Cell which stores a number

    // The actual number itself. Stored as a BigDecimal to allow for
    // arbitrary precision and length
    // ('double' is imprecise and 'long' doesn't do non-integral values)
    public BigDecimal num;
    // Constructor
    public NumberCell(String val) throws NumberFormatException {
        num = new BigDecimal(val);
    }
    // Get the displayed value of this number
    @Override public String displayed() {
        return num.toPlainString();
    }
    // Get the actual value of this number
    @Override public String value() {
        return num.toString();
    }
    // Load a NumberCell from a file, and return it
    public static NumberCell load(DataInputStream ds) throws IOException {
        return new NumberCell(ds.readUTF());
    }
    // Write this NumberCell to a file
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(num.toString());
    }
}
