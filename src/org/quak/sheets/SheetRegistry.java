package org.quak.sheets;

import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.LabelCell;

import java.io.File;
import java.util.HashMap;

public class SheetRegistry {
    public HashMap<CellPosition, Cell> cells = new HashMap<>();
    public final QUpdater<File> fileName = new QUpdater<>(null);
    public final QUpdater<Boolean> saved = new QUpdater<>(false);
    public SheetRegistry() {
    }
    public static SheetRegistry load(File f) {
        return SheetLoaderAndSaver.load(f);
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
        saved.set(false);
    }
    public void del(CellPosition pos) {
        cells.remove(pos);
    }
    public void deleteColumn(int col) {
        var map = new HashMap<CellPosition, Cell>();
        cells.forEach((pos, cell) -> {
            if (pos.col() < col) map.put(pos, cell);
            else if (pos.col() > col) map.put(new CellPosition(pos.col() - 1, pos.row()), cell);
        });
        cells = map;
    }
    public void deleteRow(int row) {
        var map = new HashMap<CellPosition, Cell>();
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
    public void save(File f) {
        if(f == null) return;
        saved.set(SheetLoaderAndSaver.save(this, f));
        if(saved.get()) fileName.set(f);
    }
}
