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
    private static FindDialog dialog = null;
    public static String base26ButNotReally(int i) {
        var b = new StringBuilder();
        while (i > 0) {
            var modulo = (i - 1) % 26;
            b.append((char) ('A' + modulo));
            i = (i - modulo) / 26;
        }
        return b.reverse().toString();
    }
    public static void showDialog(SheetRenderer context, boolean replace) {
        if (dialog == null) dialog = new FindDialog(context);
        dialog.replaceAndShow(replace);
    }
    public static ImageIcon loadImage(String imageName) {
        var imageLocation = "/images/" + imageName;
        var imageURL = Util.class.getResource(imageLocation);
        if (imageURL == null) {
            System.err.println("Failed to find image: " + imageName);
            return null;
        }
        return new ImageIcon(imageURL);
    }
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
                            Cell.make(renderer.dataEntry.toString(),
                                    next.sub(top)));
                else dataList.add(next.sub(top), registry.at(next));
            }
        }
        else if(renderer.enteringData)
            dataList.add(renderer.cursor.sub(renderer.cursor),
                    Cell.make(renderer.dataEntry.toString(),
                            renderer.cursor.sub(renderer.cursor)));
        else dataList.add(renderer.cursor.sub(renderer.cursor),
                    registry.at(renderer.cursor));

        return new CellTransferable(dataList);
    }
    public static void
    deleteSelectionFromSheet(SheetRenderer renderer, SheetRegistry registry) {
        if (renderer.enteringData) renderer.dataEntry = new StringBuilder();
        else registry.del(renderer.cursor);
    }
    public static File
    selectSaveFileName(SheetRenderer renderer, SheetRegistry registry) {
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
    public static File
    openFileName(SheetRenderer renderer, SheetRegistry registry) {
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
