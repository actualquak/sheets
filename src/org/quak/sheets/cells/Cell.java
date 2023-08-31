package org.quak.sheets.cells;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
    public void save(ObjectOutput out) throws IOException {
        if (getClass().equals(DummyCell.class)) out.writeInt(0);
        else if (getClass().equals(LabelCell.class)) out.writeInt(1);
        writeBody(out);
    }
    abstract void writeBody(ObjectOutput out) throws IOException;
}
