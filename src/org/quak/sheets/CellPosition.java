package org.quak.sheets;

import java.util.Objects;

public class CellPosition {
    private int col;
    private int row;
    public CellPosition(int col, int row) { this.col = col; this.row = row; }
    public int col() { return col; }
    public void col(int col) { this.col = col; }
    @Override public boolean equals(Object other) {
        return other instanceof CellPosition o && row == o.row() && col == o.col();
    }
    @Override public int hashCode() {
        return Objects.hash(col, row);
    }
    public static CellPosition makeAbsolute(int col, int row) { return new CellPosition(col, row); }
    public static CellPosition makeRelative(CellPosition to, int col, int row) {
        return new CellPosition(col + to.col(), row + to.row());
    }
    public int row() { return row; }
    public void row(int row) { this.row = row; }
}
