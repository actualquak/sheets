package org.quak.sheets;

import org.quak.sheets.cells.Cell;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Util {
    // Class for miscellaneous utilities that really don't belong anywhere else
    // but do not need their own class

    // Converts a column integer to a column name
    // (i.e. 1 -> A, 26 -> Z, 28 -> AB)
    public static String base26ButNotReally(int i) {
        var b = new StringBuilder();
        while (i > 0) {
            var modulo = (i - 1) % 26;
            b.append((char) ('A' + modulo));
            i = (i - modulo) / 26;
        }
        return b.reverse().toString();
    }
    // Load an image icon. This is unused (deleted the images)
    // Still included because it does work and could be a starting point
    // towards other things which are also useful (image cells?)
    public static ImageIcon loadImage(String imageName) {
        var imageLocation = "/images/" + imageName;
        var imageURL = Util.class.getResource(imageLocation);
        if (imageURL == null) {
            System.err.println("Failed to find image: " + imageName);
            return null;
        }
        return new ImageIcon(imageURL);
    }
    // Get a sorted list of the columns in the current selection
    public static ArrayList<Integer>
    getSortedSelectionColumns(SheetRenderer renderer) {
        if (renderer.selection != null) {
            var columnDeletionMap = new LinkedHashSet<Integer>();
            var it = renderer.selection.iterator();
            while (it.hasNext()) {
                var pos = it.next();
                columnDeletionMap.add(pos.col());
            }
            var q = new ArrayList<>(columnDeletionMap);
            Collections.sort(q);
            return q;
        } else {
            var l = new ArrayList<Integer>(1);
            l.add(renderer.cursor.col());
            return l;
        }
    }
    // Get a sorted list of the rows in the current selection
    public static ArrayList<Integer>
    getSortedSelectionRows(SheetRenderer renderer) {
        if (renderer.selection != null) {
            var rowDeletionMap = new LinkedHashSet<Integer>();
            var it = renderer.selection.iterator();
            while (it.hasNext()) {
                var pos = it.next();
                rowDeletionMap.add(pos.row());
            }
            var q = new ArrayList<>(rowDeletionMap);
            Collections.sort(q);
            return q;
        } else {
            var l = new ArrayList<Integer>(1);
            l.add(renderer.cursor.row());
            return l;
        }
    }
    // Copy the current selection from the spreadsheet
    public static Transferable
    copySelectionFromSheet(SheetRenderer renderer, SheetRegistry registry) {
        var dataList = new CellTransferable.CellDataList();
        if(renderer.selection != null) {
            var top = new CellPosition(
                    getSortedSelectionColumns(renderer).get(0),
                    getSortedSelectionRows(renderer).get(0));

            for(var it = renderer.selection.iterator(); it.hasNext(); ) {
                var next = it.next();
                if(next.equals(renderer.cursor) && renderer.enteringData)
                    dataList.add(next.sub(top),
                            Cell.make(renderer.dataEntry.toString(), registry));
                else dataList.add(next.sub(top), registry.at(next));
            }
        }
        else if(renderer.enteringData)
            dataList.add(renderer.cursor.sub(renderer.cursor),
                    Cell.make(renderer.dataEntry.toString(), registry));
        else dataList.add(renderer.cursor.sub(renderer.cursor),
                    registry.at(renderer.cursor));

        return new CellTransferable(dataList);
    }
    // Delete the current selection from the spreadsheet
    public static void
    deleteSelectionFromSheet(SheetRenderer renderer, SheetRegistry registry) {
        if (renderer.enteringData) renderer.dataEntry = new StringBuilder();
        else registry.del(renderer.cursor);
    }
    // Save file dialog
    public static File
    selectSaveFileName(SheetRenderer renderer) {
        var fc = new JFileChooser();
        var filter = new FileNameExtensionFilter("Spreadsheet files",
                "sheet");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        var returnVal = fc.showSaveDialog(renderer);
        return returnVal == JFileChooser.APPROVE_OPTION
                ? fc.getSelectedFile() :
                null;
    }
    // Open file
    public static File
    openFileName(SheetRenderer renderer) {
        var fc = new JFileChooser();
        var filter = new FileNameExtensionFilter("Spreadsheet files",
                "sheet");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        var returnVal = fc.showOpenDialog(renderer);
        return returnVal == JFileChooser.APPROVE_OPTION
                ? fc.getSelectedFile()
                : null;
    }
}
