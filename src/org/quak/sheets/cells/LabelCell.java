package org.quak.sheets.cells;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class LabelCell extends Cell {
    final String displayed;
    public LabelCell(String displayed) {
        this.displayed = displayed;
    }
    @Override public String displayed() {
        return displayed;
    }
    @Override public String value() {
        return displayed;
    }
    @Override void writeBody(ObjectOutput out) throws IOException {
        out.writeUTF(displayed);
    }
    static LabelCell loadBody(ObjectInput in) throws IOException {
        return new LabelCell(in.readUTF());
    }
}
