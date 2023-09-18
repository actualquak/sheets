package org.quak.sheets.cells;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.cells.formula.FormulaCell;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class Cell {
    private static final Pattern numberRegex
            = Pattern.compile(
                    "[+-]?(\\d+(\\.\\d+)?|\\.\\d+)([eE][+-]?\\d+)?");
    public static Cell make(String text, SheetRegistry registry) {
        var numberMatcher = numberRegex.matcher(text);
        if(numberMatcher.matches()) try {
            return new NumberCell(text);
        } catch (NumberFormatException ignored) { }
        if(text.startsWith("=")) return new FormulaCell(text, registry);
        else if(text.length() == 0) return new DummyCell();
        return new LabelCell(text);
    }
    public abstract String displayed();
    public abstract String value();
    public abstract void write(DataOutputStream ds) throws IOException;
}
