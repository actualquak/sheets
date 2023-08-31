package org.quak.sheets.cells;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LabelCell extends Cell {
    final String displayed;
    public LabelCell(String displayed) {
        this.displayed = displayed;
    }
    public static LabelCell load(DataInputStream ds) throws IOException {
        return new LabelCell(ds.readUTF());
    }
    @Override public String displayed() {
        return displayed;
    }
    @Override public String value() {
        return displayed;
    }
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(displayed);
    }
}
