package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertRowAboveAction extends MyAction {
    // Action to insert a row above the current selection

    // Renderer
    private final SheetRenderer renderer;
    // Registry
    private final SheetRegistry registry;
    // Constructor
    @SuppressWarnings("unused") public
    InsertRowAboveAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Row Above",
                null,
                "Insert a row above the selection",
                KeyStroke.getKeyStroke("alt shift I"),
                KeyEvent.VK_R);
        this.renderer = renderer;
        this.registry = registry;
    }
    // Insert a row above the current selection
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionRows(renderer);
        registry.insertRow(q.get(0));
        renderer.cursor = new CellPosition(renderer.cursor.col(),
                renderer.cursor.row() + 1);
        renderer.repaint();
    }
}
