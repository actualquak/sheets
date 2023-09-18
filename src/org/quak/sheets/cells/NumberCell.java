package org.quak.sheets.cells;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class NumberCell extends Cell {
    public BigDecimal num;
    public NumberCell(String val) throws NumberFormatException {
        num = new BigDecimal(val);
    }
    @Override public String displayed() {
        return num.toPlainString();
    }
    @Override public String value() {
        return num.toString();
    }
    public static NumberCell load(DataInputStream ds) throws IOException {
        return new NumberCell(ds.readUTF());
    }
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(num.toString());
    }
}
