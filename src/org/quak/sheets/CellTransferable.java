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
    // Transferable for transmitting a list of cells "across the wire"
    // Part of copy/paste, see the java documentation for Transferable or
    // https://docstore.mik.ua/orelly/jfc/ch11_01.htm, which does a better job
    // of explaining the whole architecture of the java.awt.datatransfer package
    // Also https://docs.oracle.com/javase/tutorial/uiswing/dnd/cutpaste.html

    // Custom DataFlavor for transferring cells
    public static final DataFlavor cellsFlavor =
            new DataFlavor(CellTransferable.class,
                    "CellTransferableData");
    // List of cells, including data
    public static class CellDataList {
        private final HashMap<CellPosition, Cell> map = new HashMap<>();
        public void add(CellPosition pos, Cell cell) {
            map.put(pos, cell);
        }
        public Iterator<Map.Entry<CellPosition, Cell>> getIterator() {
            return map.entrySet().iterator();
        }
    }
    // DataFlavors supported by CellTransferable
    private final static DataFlavor[] flavors
            = {cellsFlavor, DataFlavor.stringFlavor};
    // Data owned by this CellTransferable
    private final CellDataList cellData;
    // Constructor
    CellTransferable(CellDataList cellData) {
        this.cellData = cellData;
    }
    // Get supported data flavors
    @Override public DataFlavor[] getTransferDataFlavors() {
        // don't return reference to cellsFlavor, which is private
        return flavors.clone();
    }
    // Check if a dataFlavor is supported
    @Override public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return Arrays.stream(flavors)
                .anyMatch(flavor -> flavor.equals(dataFlavor));
    }
    // Get data, formatted to the specific DataFlavor
    @Override public Object getTransferData(DataFlavor dataFlavor)
            throws UnsupportedFlavorException {
        if(dataFlavor.equals(cellsFlavor)) return cellData;
        else if(dataFlavor.equals(DataFlavor.stringFlavor))
            return cellData.getIterator().next().getValue().value();
        else throw new UnsupportedFlavorException(dataFlavor);
    }
}
