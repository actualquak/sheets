package org.quak.sheets.cells;

import java.util.concurrent.ThreadLocalRandom;

public class DummyCell extends Cell {
    final int dummyNum = ThreadLocalRandom.current().nextInt(0, 100);
    @Override public String displayed() { return String.valueOf(dummyNum); }
    @Override public String value() { return String.valueOf(dummyNum); }
}
