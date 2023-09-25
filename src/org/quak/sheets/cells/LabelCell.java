package org.quak.sheets.cells;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LabelCell extends Cell {
    // Cell for displaying a Label; a text cell

    // Displayed text
    final String displayed;
    // Constructor
    public LabelCell(String displayed) {
        this.displayed = displayed;
    }
    // Load a LabelCell from a file
    public static LabelCell load(DataInputStream ds) throws IOException {
        return new LabelCell(ds.readUTF());
    }
    // Get displayed value
    @Override public String displayed() {
        return displayed;
    }
    // Get value
    @Override public String value() {
        return displayed;
    }
    // Write this cell to a file
    @Override public void write(DataOutputStream ds) throws IOException {
        ds.writeUTF(displayed);
    }
}
