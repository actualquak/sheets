package org.quak;

import java.util.concurrent.ThreadLocalRandom;

public class DummyCell extends Cell {
    int dummyNum = ThreadLocalRandom.current().nextInt();
    @Override public String displayed() { return String.valueOf(dummyNum); }
}
