package org.quak.sheets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class SheetRegistry {
    private final HashMap<CellPosition, Cell> cells = new HashMap<>();
    SheetRegistry() { }
    public Cell at(CellPosition pos) {
        if(pos.col() == 0 && pos.row() == 0) return cells.computeIfAbsent(pos, p -> new LabelCell(""));
        if(pos.col() == 0) return cells.computeIfAbsent(pos, p -> new LabelCell(String.valueOf(p.row())));
        if(pos.row() == 0) return cells.computeIfAbsent(pos, p -> new LabelCell(Util.base26ButNotReally(p.col())));
        return cells.computeIfAbsent(pos, p -> new DummyCell());
    }
    public void at(CellPosition pos, Cell cell) {
        if(pos.col() == 0 || pos.row() == 0) return;
        cells.put(pos, cell);
    }
    public static SheetRegistry load(ByteArrayInputStream bais) { throw new NotYetImplemented(); }
    public void save(ByteArrayOutputStream baos) { throw new NotYetImplemented(); }
}
