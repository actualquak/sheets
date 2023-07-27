package org.quak.sheets;

public class LabelCell extends Cell {
    final String displayed;
    LabelCell(String displayed) { this.displayed = displayed; }
    @Override public String displayed() { return displayed; }
}
