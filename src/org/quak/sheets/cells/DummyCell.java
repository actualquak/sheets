package org.quak.sheets.cells;

import java.io.DataOutputStream;

public class DummyCell extends Cell {
    @Override public String displayed() {
        return "";
    }
    @Override public String value() {
        return "";
    }
    @Override public void write(DataOutputStream ds) {
        throw new UnsupportedOperationException();
    }
}
