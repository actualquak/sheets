package org.quak.sheets;

import java.util.ArrayList;
import java.util.Iterator;

class AreaSelection extends CellSelection {
    // A subclass for CellSelection that specifies a rectangle of cells

    // First corner of the cell
    private final CellPosition first;
    // Second corner of the cell - expand* commands move this corner
    private CellPosition second;
    // Constructor
    public AreaSelection(CellPosition first, CellPosition second) {
        this.first = first;
        this.second = second;
    }
    // Move the second corner of this selection down
    @Override public CellSelection expandDown() {
        second = new CellPosition(second.col(), second.row() + 1);
        return this;
    }
    // Move the second corner of this selection left
    @Override public CellSelection expandLeft() {
        second = new CellPosition(Math.max(second.col() - 1, 1), second.row());
        return this;
    }
    // Move the second corner of this selection right
    @Override public CellSelection expandRight() {
        second = new CellPosition(second.col() + 1, second.row());
        return this;
    }
    // Move the second corner of this selection up
    @Override public CellSelection expandUp() {
        second = new CellPosition(second.col(), Math.max(second.row() - 1, 1));
        return this;
    }
    // Flip a cell in this selection
    @SuppressWarnings("unused") @Override public CellSelection
    flipCell(CellPosition cell) {
        var cells = new ArrayList<CellPosition>();
        iterator().forEachRemaining(cells::add);
        return new IndividualSelection(cells).flipCell(cell);
    }
    // Test if a cell is in this selection
    @Override public boolean isIn(CellPosition cell) {
        return (first.col() <= cell.col() && cell.col() <= second.col()
                || first.col() >= cell.col() && cell.col() >= second.col())
                && (first.row() <= cell.row() && cell.row() <= second.row()
                || first.row() >= cell.row() && cell.row() >= second.row());
    }
    // Get iterator over all cells in this selection
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
    // A subclass for CellSelection that specifies a list of cells

    // Stored list of cells
    private final ArrayList<CellPosition> positions;
    // Constructor
    public IndividualSelection(ArrayList<CellPosition> cells) {
        positions = cells;
    }
    // expand* methods do nothing, because these operations don't apply to a
    // list of cells, only to a rectangle. To put it another way, if you
    // selected A1, A3 and B7, what would you expect pressing the down arrow to
    // do? Nothing, so these methods do exactly that
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
    // Flip a cell in this selection
    @Override public CellSelection flipCell(CellPosition cell) {
        if (positions.contains(cell)) positions.remove(cell);
        else positions.add(cell);
        return this;
    }
    // Test if a cell is in this selection
    @SuppressWarnings("unused") @Override public boolean
    isIn(CellPosition cell) {
        return positions.contains(cell);
    }
    // Get an iterator over all the cells in this selection
    @SuppressWarnings("unused") @Override public Iterator<CellPosition>
    iterator() {
        return positions.iterator();
    }
}

public abstract class CellSelection {
    // Selection of cells. Much of this code is unused, however still included
    // because it is necessary for supporting the flipCell operation
    // In turn, the flipCell operation is included because it is a requirement
    // for good mouse support, as is the other unused code (two arg constructor)
    // Also much of this code is untested (MASSIVE disclaimer)

    public static CellSelection makeSelection(CellPosition cursor) {
        // Make a CellSelection from a single point
        return new AreaSelection(cursor, cursor);
    }
    @SuppressWarnings("unused") public static
    CellSelection makeSelection(CellPosition first, CellPosition second) {
        // Make a CellSelection from a pair of points
        // Left in for mouse support
        return new AreaSelection(first, second);
    }
    // Expand this CellSelection's area down
    public abstract CellSelection expandDown();
    // Expand this CellSelection's area left
    public abstract CellSelection expandLeft();
    // Expand this CellSelection's area right
    public abstract CellSelection expandRight();
    // Expand this CellSelection's area up
    public abstract CellSelection expandUp();
    // Flip whether the specified cell is included in this selection or not
    // Left in for mouse support
    @SuppressWarnings("unused") public abstract
    CellSelection flipCell(CellPosition cell);
    // Test if a cell is in this selection
    public abstract boolean isIn(CellPosition cell);
    // Get an iterator over all cells in this selection
    public abstract Iterator<CellPosition> iterator();
}
