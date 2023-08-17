package org.quak.sheets;

import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.LabelCell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class SheetRegistry {
    private HashMap<CellPosition, Cell> cells = new HashMap<>();
    public SheetRegistry() {
    }
    public static SheetRegistry load(ByteArrayInputStream bais) {
        throw new NotYetImplemented();
    }
    public Cell at(CellPosition pos) {
        if (pos.col() == 0 && pos.row() == 0) return new LabelCell("@");
        if (pos.col() == 0) return new LabelCell(String.valueOf(pos.row()));
        if (pos.row() == 0) return new LabelCell(Util.base26ButNotReally(pos.col()));
        return cells.computeIfAbsent(pos, p -> new DummyCell());
    }
    public void at(CellPosition pos, Cell cell) {
        if (pos.col() == 0 || pos.row() == 0) return;
        cells.put(pos, cell);
    }
    public void deleteColumn(int col) {
        HashMap<CellPosition, Cell> map = new HashMap<>();
        cells.forEach((pos, cell) -> {
            if (pos.col() < col) map.put(pos, cell);
            else if (pos.col() > col) map.put(new CellPosition(pos.col() - 1, pos.row()), cell);
        });
        cells = map;
    }
    public void deleteRow(int row) {
        HashMap<CellPosition, Cell> map = new HashMap<>();
        cells.forEach((pos, cell) -> {
            if (pos.row() < row) map.put(pos, cell);
            else if (pos.row() > row) map.put(new CellPosition(pos.col(), pos.row() - 1), cell);
        });
        cells = map;
    }
    public void insertColumnRight(int col) {
        throw new NotYetImplemented();
    }
    public void insertRowBelow(int row) {
        throw new NotYetImplemented();
    }
    public void save(ByteArrayOutputStream baos) {
        throw new NotYetImplemented();
    }
}
