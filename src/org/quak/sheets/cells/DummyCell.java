package org.quak.sheets.cells;

import java.io.ObjectInput;
import java.io.ObjectOutput;
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
    @Override void writeBody(ObjectOutput out) {
    }
}
