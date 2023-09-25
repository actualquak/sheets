package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertColumnLeftAction extends MyAction {
    // Action for inserting a column to the left

    // Renderer
    private final SheetRenderer renderer;
    // Registry
    private final SheetRegistry registry;
    // Constructor
    @SuppressWarnings("unused") public
    InsertColumnLeftAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Column Left",
                null,
                "Insert a column to the left of the selection",
                KeyStroke.getKeyStroke("alt shift L"),
                KeyEvent.VK_C);
        this.renderer = renderer;
        this.registry = registry;
    }
    // Insert a column to the left
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionColumns(renderer);
        registry.insertColumn(q.get(0));
        renderer.cursor = new CellPosition(renderer.cursor.col() + 1,
                renderer.cursor.row());
        renderer.repaint();
    }
}
