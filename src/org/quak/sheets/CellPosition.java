package org.quak.sheets;

import java.util.Objects;

public record CellPosition(int col, int row) {
    // Record which stores the column and row
    // of a single position in a spreadsheet
    @Override public boolean equals(Object other) {
        // Equals method. Required for using this as a key in a HashMap
        return other instanceof CellPosition o
                && row == o.row() && col == o.col();
    }
    public CellPosition sub(CellPosition other) {
        // Subtract other from this, and return the result
        return new CellPosition(col - other.col, row - other.row);
    }
    public CellPosition sum(CellPosition other) {
        // Add other to this, and return the result
        return new CellPosition(col + other.col, row + other.row);
    }
    public static CellPosition valueOf(String s) {
        // Parse a CellPosition from a String
        // Never call this with an invalidly formatted s
        var s2 = s.toUpperCase(); // Makes following logic easier
        var column = 0;
        var row = 0;
        var i = 0;
        for(; i < s2.length(); i++) {
            // The reason we converted to uppercase
            if('A' <= s2.charAt(i) && s2.charAt(i) <= 'Z') {
                // Just a simple base-26 number parser
                column *= 26; // Move digit over
                column += s2.charAt(i) - 'A' + 1; // Add new digit to end
            } else break;
        }
        for(; i < s2.length(); i++) {
            // Resume iteration
            if('0' <= s2.charAt(i) && s2.charAt(i) <= '9') {
                // Another simple parser, but for base-10 this time
                row *= 10;
                row += s2.charAt(i) - '0';
            }
        }
        return new CellPosition(column, row);
    }
    @Override public int hashCode() {
        // HashCode method. Serves same purpose as equals()
        return Objects.hash(col, row);
    }
}
