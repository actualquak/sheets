package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class DeleteRowAction extends MyAction {
    private final SheetRegistry registry;
    private final SheetRenderer renderer;
    @SuppressWarnings("unused") public
    DeleteRowAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Delete Row",
                null,
                "Delete the row(s) selected",
                KeyStroke.getKeyStroke("control D"),
                KeyEvent.VK_E);
        this.registry = registry;
        this.renderer = renderer;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionRows(renderer);
        Collections.reverse(q);
        for (int r : q) registry.deleteRow(r);
        renderer.repaint();
    }
}
