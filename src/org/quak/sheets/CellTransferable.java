package org.quak.sheets;

import org.quak.sheets.cells.Cell;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CellTransferable implements Transferable {
    public static final DataFlavor cellsFlavor =
            new DataFlavor(CellTransferable.class,
                    "CellTransferableData");
    public static class CellDataList {
        private final HashMap<CellPosition, Cell> map = new HashMap<>();
        public void add(CellPosition pos, Cell cell) {
            map.put(pos, cell);
        }
        public Iterator<Map.Entry<CellPosition, Cell>> getIterator() {
            return map.entrySet().iterator();
        }
    }
    private final static DataFlavor[] flavors
            = {cellsFlavor, DataFlavor.stringFlavor};

    private final CellDataList cellData;
    CellTransferable(CellDataList cellData) {
        this.cellData = cellData;
    }
    @Override public DataFlavor[] getTransferDataFlavors() {
        return flavors.clone();
    }
    @Override public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return Arrays.stream(flavors)
                .anyMatch(flavor -> flavor.equals(dataFlavor));
    }
    @Override public Object getTransferData(DataFlavor dataFlavor)
            throws UnsupportedFlavorException {
        if(dataFlavor.equals(cellsFlavor)) return cellData;
        else if(dataFlavor.equals(DataFlavor.stringFlavor))
            return cellData.getIterator().next().getValue().value();
        else throw new UnsupportedFlavorException(dataFlavor);
    }
}
