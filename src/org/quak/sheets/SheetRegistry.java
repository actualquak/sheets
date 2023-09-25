package org.quak.sheets;

import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.LabelCell;

import java.io.File;
import java.util.HashMap;

public class SheetRegistry {
    // "Registry", i.e. spreadsheet of cells
    // The backend class that this whole application is really structured around

    // This class uses a HashMap for cells because it has better performance
    // characteristics than a linked list (long seek) or 2d array (large memory
    // usage)
    public HashMap<CellPosition, Cell> cells = new HashMap<>();
    // The filename
    public final QUpdater<File> fileName = new QUpdater<>(null);
    // Has the file been saved?
    public final QUpdater<Boolean> saved = new QUpdater<>(false);
    // Constructor
    public SheetRegistry() {
    }
    // Load from file
    public static SheetRegistry load(File f) {
        return SheetLoaderAndSaver.load(f);
    }
    // Get cell at position
    public Cell at(CellPosition pos) {
        if (pos.col() == 0 && pos.row() == 0) return new LabelCell("@");
        if (pos.col() == 0) return new LabelCell(String.valueOf(pos.row()));
        if (pos.row() == 0)
            return new LabelCell(Util.base26ButNotReally(pos.col()));
        return cells.computeIfAbsent(pos, p -> new DummyCell());
    }
    // Set cell at position
    public void at(CellPosition pos, Cell cell) {
        if (pos.col() == 0 || pos.row() == 0) return;
        cells.put(pos, cell);
        saved.set(false);
    }
    // Delete cell
    public void del(CellPosition pos) {
        cells.remove(pos);
    }
    // Delete column
    public void deleteColumn(int col) {
        var map = new HashMap<CellPosition, Cell>();
        cells.forEach((pos, cell) -> {
            if (pos.col() < col) map.put(pos, cell);
            else if (pos.col() > col)
                map.put(new CellPosition(pos.col() - 1, pos.row()), cell);
        });
        cells = map;
        saved.set(false);
    }
    // Delete row
    public void deleteRow(int row) {
        var map = new HashMap<CellPosition, Cell>();
        cells.forEach((pos, cell) -> {
            if (pos.row() < row) map.put(pos, cell);
            else if (pos.row() > row)
                map.put(new CellPosition(pos.col(), pos.row() - 1), cell);
        });
        cells = map;
        saved.set(false);
    }
    // Insert column
    public void insertColumn(int col) {
        var map = new HashMap<CellPosition, Cell>();
        cells.forEach((pos, cell) -> {
            if(pos.col() < col) map.put(pos, cell);
            else map.put(new CellPosition(pos.col() + 1, pos.row()), cell);
        });
        cells = map;
        saved.set(false);
    }
    // Insert row
    public void insertRow(int row) {
        var map = new HashMap<CellPosition, Cell>();
        cells.forEach((pos, cell) -> {
            if(pos.row() < row) map.put(pos, cell);
            else map.put(new CellPosition(pos.col(), pos.row() + 1), cell);
        });
        cells = map;
        saved.set(false);
    }
    // Save file
    public void save(File f) {
        if(f == null) return;
        saved.set(SheetLoaderAndSaver.save(this, f));
        if(saved.get()) fileName.set(f);
    }
}
