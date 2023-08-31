package org.quak.sheets.cells;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;

public class LabelCell extends Cell {
    final String displayed;
    public LabelCell(String displayed) {
        this.displayed = displayed;
    }
    static LabelCell loadBody(ObjectInput in) throws IOException {
        return new LabelCell(in.readUTF());
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
