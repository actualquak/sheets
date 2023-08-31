package org.quak.sheets.cells;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;

public abstract class Cell {
    public static Cell load(ObjectInput in) throws IOException {
        return switch (in.readInt()) {
            case 0 -> DummyCell.loadBody(in);
            case 1 -> LabelCell.loadBody(in);
            default -> throw new IOException("Invalid formatting");
        };
    }
    public static Cell make(String text) {
        return new LabelCell(text);
    }
    public abstract String displayed();
    public abstract String value();
    public abstract void write(DataOutputStream ds) throws IOException;
}
