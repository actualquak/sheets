package org.quak.sheets.cells;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.cells.formula.FormulaCell;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class Cell {
    // Cell base class. Implements methods that every cell needs
    // Allows for the cells 'value' to be different from it's displayed value
    // (i.e. formula cells)

    // Regular expression to match text contained within number cells
    private static final Pattern numberRegex
            = Pattern.compile(
                    "[+-]?(\\d+(\\.\\d+)?|\\.\\d+)([eE][+-]?\\d+)?");
    // Makes a cell. Automatically chooses the type of cell to make based on
    // the format of text
    public static Cell make(String text, SheetRegistry registry) {
        var numberMatcher = numberRegex.matcher(text);
        // If the text is a valid number, return a NumberCell
        if(numberMatcher.matches()) try {
            return new NumberCell(text);
        } catch (NumberFormatException ignored) { }
        // If it starts with "=", it's a formula
        if(text.startsWith("=")) return new FormulaCell(text, registry);
        // If the text is literally just nothing, it's a dummy cell
        else if(text.length() == 0) return new DummyCell();
        // Otherwise just return a label cell containing the text
        return new LabelCell(text);
    }
    // Get the displayed value of this Cell
    public abstract String displayed();
    // Get the value of this cell
    public abstract String value();
    // Write this cell to a file
    // (only needs to write the actual contents, and never the header)
    public abstract void write(DataOutputStream ds) throws IOException;
}
