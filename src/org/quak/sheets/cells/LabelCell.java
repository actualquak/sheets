package org.quak.sheets.cells;

public class LabelCell extends Cell {
    final String displayed;
    public LabelCell(String displayed) { this.displayed = displayed; }
    @Override public String displayed() { return displayed; }
    @Override public String value() { return displayed; }
}
