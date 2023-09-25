package org.quak.sheets.cells;

import java.io.DataOutputStream;

public class DummyCell extends Cell {
    // Dummy Cell, which has no data, does not display anything,
    // and is never written to a file

    // Get displayed value (nothing)
    @Override public String displayed() {
        return "";
    }
    // Get value (nothing)
    @Override public String value() {
        return "";
    }
    // Write to a file (never happens)
    @Override public void write(DataOutputStream ds) {
        throw new UnsupportedOperationException();
    }
}
