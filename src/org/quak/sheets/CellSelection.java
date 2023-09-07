package org.quak.sheets;

import java.util.ArrayList;
import java.util.Iterator;

class AreaSelection extends CellSelection {
    private final CellPosition first;
    private CellPosition second;
    public AreaSelection(CellPosition first, CellPosition second) {
        this.first = first;
        this.second = second;
    }
    @Override public CellSelection expandDown() {
        second = new CellPosition(second.col(), second.row() + 1);
        return this;
    }
    @Override public CellSelection expandLeft() {
        second = new CellPosition(Math.max(second.col() - 1, 1), second.row());
        return this;
    }
    @Override public CellSelection expandRight() {
        second = new CellPosition(second.col() + 1, second.row());
        return this;
    }
    @Override public CellSelection expandUp() {
        second = new CellPosition(second.col(), Math.max(second.row() - 1, 1));
        return this;
    }
    @Override public CellSelection flipCell(CellPosition cell) {
        var cells = new ArrayList<CellPosition>();
        iterator().forEachRemaining(cells::add);
        return new IndividualSelection(cells).flipCell(cell);
    }
    @Override public boolean isIn(CellPosition cell) {
        return (first.col() <= cell.col() && cell.col() <= second.col()
                || first.col() >= cell.col() && cell.col() >= second.col())
                && (first.row() <= cell.row() && cell.row() <= second.row()
                || first.row() >= cell.row() && cell.row() >= second.row());
    }
    @Override public Iterator<CellPosition> iterator() {
        var positions = new ArrayList<CellPosition>();
        var rowIncrease = -(int) Math.signum(first.row() - second.row());
        if(rowIncrease == 0) rowIncrease = 1;
        var colIncrease = -(int) Math.signum(first.col() - second.col());
        if(colIncrease == 0) colIncrease = 1;
        for (var x = first.col();
             x != second.col() + colIncrease; x += colIncrease) {
            for (var y = first.row();
                 y != second.row() + rowIncrease; y += rowIncrease) {
                positions.add(new CellPosition(x, y));
            }
        }
        return positions.iterator();
    }
}

class IndividualSelection extends CellSelection {
    private final ArrayList<CellPosition> positions;
    public IndividualSelection(ArrayList<CellPosition> cells) {
        positions = cells;
    }
    @Override public CellSelection expandDown() {
        return this;
    }
    @Override public CellSelection expandLeft() {
        return this;
    }
    @Override public CellSelection expandRight() {
        return this;
    }
    @Override public CellSelection expandUp() {
        return this;
    }
    @Override public CellSelection flipCell(CellPosition cell) {
        if (positions.contains(cell)) positions.remove(cell);
        else positions.add(cell);
        return this;
    }
    @Override public boolean isIn(CellPosition cell) {
        return positions.contains(cell);
    }
    @Override public Iterator<CellPosition> iterator() {
        return positions.iterator();
    }
}

public abstract class CellSelection {
    public static CellSelection makeSelection(CellPosition cursor) {
        return new AreaSelection(cursor, cursor);
    }
    @SuppressWarnings("unused") public static
    CellSelection makeSelection(CellPosition first, CellPosition second) {
        // left in for mouse support
        return new AreaSelection(first, second);
    }
    public abstract CellSelection expandDown();
    public abstract CellSelection expandLeft();
    public abstract CellSelection expandRight();
    public abstract CellSelection expandUp();
    @SuppressWarnings("unused") public abstract
    CellSelection flipCell(CellPosition cell); // left in for mouse support
    public abstract boolean isIn(CellPosition cell);
    public abstract Iterator<CellPosition> iterator();
}
