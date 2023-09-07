package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class InsertColumnRightAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public
    InsertColumnRightAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Column Right",
                null,
                "Insert a column to the right of the selection",
                KeyStroke.getKeyStroke("alt L"),
                KeyEvent.VK_C);
        this.registry = registry;
        this.renderer = renderer;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionColumns(renderer);
        Collections.reverse(q);
        registry.insertColumn(q.get(0) + 1);
        renderer.repaint();
    }
}