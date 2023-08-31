package org.quak.sheets;

import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.LabelCell;
import org.quak.sheets.cells.NumberCell;

import javax.swing.*;
import java.io.*;

/*
 * Save file format
 * Version 1
 * enum CELL_TYPE {
 *   CELL_TYPE_NUMBER = 0;
 *   CELL_TYPE_LABEL = 1;
 * }
 * struct table_entry {
 *   uint8_t type;
 *   uint32_t col;
 *   uint32_t row;
 *   uint8_t[] cell_data;
 * }
 * struct save_file {
 *   uint32_t magic = 0x0158EE15;
 *   struct table_entry[size] table;
 * }
 */

public class SheetLoaderAndSaver {
    private static final byte CELL_TYPE_NUMBER = 0;
    private static final byte CELL_TYPE_LABEL = 1;
    private static SheetRegistry loadVersion1File(File f, DataInputStream ds) throws IOException {
        var r = new SheetRegistry();
        while(true) {
            byte type;
            try { type = ds.readByte(); } catch(EOFException e) { break; }
            var col = ds.readInt();
            var row = ds.readInt();
            var pos = new CellPosition(col, row);
            switch(type) {
                case CELL_TYPE_LABEL -> r.cells.put(new CellPosition(col, row), LabelCell.load(ds));
                case CELL_TYPE_NUMBER -> r.cells.put(new CellPosition(col, row), NumberCell.load(ds));
                default -> throw new IOException("Found invalid cell type");
            }
        }
        r.fileName.set(f);
        r.saved.set(true);
        return r;
    }
    public static SheetRegistry load(File f) {
        try {
            var ds = new DataInputStream(new FileInputStream(f));
            if(ds.readInt() == 0x0158EE15) return loadVersion1File(f, ds);
            else throw new IOException("Invalid magic number");
        } catch (IOException e) {
            showErrorWindow(e);
            return null;
        }
    }
    private static void writeCell(DataOutputStream ds, CellPosition pos, Cell cell) throws IOException {
        if(cell.getClass().equals(DummyCell.class) || pos.col() == 0 || pos.row() == 0) return; // don't try to serialize these
        if (cell.getClass().equals(LabelCell.class)) ds.writeByte(CELL_TYPE_LABEL);
        else if(cell.getClass().equals(NumberCell.class)) ds.writeByte(CELL_TYPE_NUMBER);
        else throw new IOException("Found non-serializable cell");
        ds.writeInt(pos.col());
        ds.writeInt(pos.row());
        cell.write(ds);
    }
    public static boolean save(SheetRegistry r, File f) {
        try {
            var ds = new DataOutputStream(new FileOutputStream(f));
            ds.writeInt(0x0158EE15);
            for (var e : r.cells.entrySet()) writeCell(ds, e.getKey(), e.getValue()); // TODO: cursor/dataEntry
            return true;
        } catch(IOException e) {
            showErrorWindow(e);
            return false;
        }
    }
    private static void showErrorWindow(Exception e) {
        var contents = new StringWriter();
        e.printStackTrace(new PrintWriter(contents));
        var textArea = new JTextArea(contents.toString());
        var scrollPane = new JScrollPane(textArea);
        var dialog = new JDialog();
        dialog.setContentPane(scrollPane);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
