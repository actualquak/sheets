package org.quak.sheets;

import org.quak.sheets.cells.Cell;
import org.quak.sheets.cells.DummyCell;
import org.quak.sheets.cells.LabelCell;

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
 *   uint32_t col;
 *   uint32_t row;
 *   uint8_t type;
 *   uint8_t[] cell_data;
 * }
 * struct save_file {
 *   uint32_t magic = 0x0158EE15;
 *   struct table_entry[size] table;
 * }
 */

public class SheetLoaderAndSaver {
    public static SheetRegistry load(File f) {
        var r = new SheetRegistry();
        r.fileName.set(f);
        r.saved.set(true);
        return r; // null if no file found
    }
    private static void writeCell(DataOutputStream ds, CellPosition pos, Cell cell) throws IOException {
        if(cell.getClass().equals(DummyCell.class) || pos.col() == 0 || pos.row() == 0) return; // don't try to serialize these
        ds.writeInt(pos.col());
        ds.writeInt(pos.row());
        if (cell.getClass().equals(LabelCell.class)) ds.writeByte(1);
        else throw new RuntimeException("Found non-serializable cell");
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
