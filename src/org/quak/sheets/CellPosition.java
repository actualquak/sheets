package org.quak.sheets;

public record CellPosition(int col, int row) {
    @Override public boolean equals(Object other) {
        return other instanceof CellPosition o && row == o.row() && col == o.col();
    }
    public CellPosition sub(CellPosition other) {
        return new CellPosition(col - other.col, row - other.row);
    }
    public CellPosition sum(CellPosition other) {
        return new CellPosition(col + other.col, row + other.row);
    }
}
