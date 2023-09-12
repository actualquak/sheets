package org.quak.sheets;

public record CellPosition(int col, int row) {
    @Override public boolean equals(Object other) {
        return other instanceof CellPosition o
                && row == o.row() && col == o.col();
    }
    public CellPosition sub(CellPosition other) {
        return new CellPosition(col - other.col, row - other.row);
    }
    public CellPosition sum(CellPosition other) {
        return new CellPosition(col + other.col, row + other.row);
    }
    public static CellPosition valueOf(String s) {
        var s2 = s.toUpperCase();
        var column = 0;
        var row = 0;
        var i = 0;
        for(; i < s2.length(); i++) {
            if('A' <= s2.charAt(i) && s2.charAt(i) <= 'Z') {
                column *= 26;
                column += s2.charAt(i) - 'A' + 1;
            } else break;
        }
        for(; i < s2.length(); i++) {
            if('0' <= s2.charAt(i) && s2.charAt(i) <= '9') {
                row *= 10;
                row += s2.charAt(i) - '0';
            }
        }
        return new CellPosition(column, row);
    }
}
