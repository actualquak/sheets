package org.quak.sheets;

import java.util.ArrayList;
import java.util.List;

public class LabelCell extends Cell {
    final String displayed;
    public LabelCell(String displayed) { this.displayed = displayed; }
    @Override public List<Cell> depends() { return new ArrayList<>(); }
    @Override public String displayed() { return displayed; }
}
