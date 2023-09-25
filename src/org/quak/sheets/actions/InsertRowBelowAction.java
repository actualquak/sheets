package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class InsertRowBelowAction extends MyAction {
    // Action for inserting a row below the current selection

    // Renderer
    private final SheetRenderer renderer;
    // Registry
    private final SheetRegistry registry;
    // Constructor
    @SuppressWarnings("unused") public
    InsertRowBelowAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Row Below",
                null,
                "Insert a row below the selection",
                KeyStroke.getKeyStroke("alt I"),
                KeyEvent.VK_R);
        this.renderer = renderer;
        this.registry = registry;
    }
    // Insert a row below the current selection
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionRows(renderer);
        Collections.reverse(q);
        registry.insertRow(q.get(0) + 1);
        renderer.repaint();
    }
}
