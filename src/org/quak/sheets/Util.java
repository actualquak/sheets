package org.quak.sheets;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Util {
    private static FindDialog dialog = null;
    public static String base26ButNotReally(int i) {
        StringBuilder b = new StringBuilder();
        while (i > 0) {
            int modulo = (i - 1) % 26;
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
        String imageLocation = "/images/" + imageName;
        java.net.URL imageURL = Util.class.getResource(imageLocation);
        if (imageURL == null) {
            System.err.println("Failed to find image: " + imageName);
            return null;
        }
        return new ImageIcon(imageURL);
    }
    public static ArrayList<Integer> getSortedSelectionColumns(SheetRenderer renderer) {
        if (renderer.selection != null) {
            LinkedHashSet<Integer> columnDeletionMap = new LinkedHashSet<>();
            Iterator<CellPosition> it = renderer.selection.iterator();
            while (it.hasNext()) {
                CellPosition pos = it.next();
                columnDeletionMap.add(pos.col());
            }
            ArrayList<Integer> q = new ArrayList<>(columnDeletionMap);
            Collections.sort(q);
            return q;
        } else {
            ArrayList<Integer> l = new ArrayList<>(1);
            l.add(renderer.cursor.col());
            return l;
        }
    }
    public static ArrayList<Integer> getSortedSelectionRows(SheetRenderer renderer) {
        if (renderer.selection != null) {
            LinkedHashSet<Integer> rowDeletionMap = new LinkedHashSet<>();
            Iterator<CellPosition> it = renderer.selection.iterator();
            while (it.hasNext()) {
                CellPosition pos = it.next();
                rowDeletionMap.add(pos.row());
            }
            ArrayList<Integer> q = new ArrayList<>(rowDeletionMap);
            Collections.sort(q);
            return q;
        } else {
            ArrayList<Integer> l = new ArrayList<>(1);
            l.add(renderer.cursor.row());
            return l;
        }
    }
    public static Transferable copySelectionFromSheet(SheetRenderer renderer, SheetRegistry registry) {
        if (renderer.enteringData) return new StringSelection(renderer.dataEntry.toString());
        else return new StringSelection(registry.at(renderer.cursor).value());
    }
    public static void deleteSelectionFromSheet(SheetRenderer renderer, SheetRegistry registry) {
        if (renderer.enteringData) renderer.dataEntry = new StringBuilder();
        else registry.del(renderer.cursor);
    }
}
