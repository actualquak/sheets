package org.quak.sheets;

import java.util.Objects;

public class CellPosition {
    private final int col;
    private final int row;
    public CellPosition(int col, int row) {
        this.col = col;
        this.row = row;
    }
    public int col() {
        return col;
    }
    @Override public boolean equals(Object other) {
        return other instanceof CellPosition o && row == o.row() && col == o.col();
    }
    @Override public int hashCode() {
        return Objects.hash(col, row);
    }
    public int row() {
        return row;
    }
    public CellPosition sub(CellPosition other) {
        return new CellPosition(col - other.col, row - other.row);
    }
    public CellPosition sum(CellPosition other) {
        return new CellPosition(col + other.col, row + other.row);
    }
}
