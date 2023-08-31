package org.quak.sheets.cells;

import org.quak.sheets.NotYetImplemented;

import java.io.DataOutputStream;
import java.io.ObjectInput;
import java.util.concurrent.ThreadLocalRandom;

public class DummyCell extends Cell {
    final int dummyNum = ThreadLocalRandom.current().nextInt(0, 100);
    static DummyCell loadBody(ObjectInput in) {
        return new DummyCell();
    }
    @Override public String displayed() {
        return String.valueOf(dummyNum);
    }
    @Override public String value() {
        return String.valueOf(dummyNum);
    }
    @Override public void write(DataOutputStream ds) {
        throw new NotYetImplemented();
    }
}
