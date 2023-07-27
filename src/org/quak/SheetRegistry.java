package org.quak;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class SheetRegistry {
    private final HashMap<CellPosition, Cell> cells = new HashMap<>();
    SheetRegistry() { }
    public Cell at(CellPosition pos) {
        return cells.computeIfAbsent(pos, p -> new DummyCell());
    }
    public void at(CellPosition pos, Cell cell) { cells.put(pos, cell); }
    public static SheetRegistry load(ByteArrayInputStream bais) { throw new NotYetImplemented(); }
    public void save(ByteArrayOutputStream baos) { throw new NotYetImplemented(); }
}
