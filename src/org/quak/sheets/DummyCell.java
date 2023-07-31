package org.quak.sheets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DummyCell extends Cell {
    int dummyNum = ThreadLocalRandom.current().nextInt(0, 100);
    @Override public List<Cell> depends() { return new ArrayList<>(); }
    @Override public String displayed() { return String.valueOf(dummyNum); }
}
