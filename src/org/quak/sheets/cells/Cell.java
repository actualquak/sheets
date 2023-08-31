package org.quak.sheets.cells;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class Cell {
    private static final Pattern numberRegex = Pattern.compile("[+-]?(\\d+(\\.\\d+)?|\\.\\d+)([eE][+-]?\\d+)?");
    public static Cell make(String text) {
        var numberMatcher = numberRegex.matcher(text);
        if(numberMatcher.matches()) try { return new NumberCell(text); } catch (NumberFormatException ignored) { }
        return new LabelCell(text);
    }
    public abstract String displayed();
    public abstract String value();
    public abstract void write(DataOutputStream ds) throws IOException;
}
