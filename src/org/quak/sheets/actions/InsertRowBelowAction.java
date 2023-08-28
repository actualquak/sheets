package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class InsertRowBelowAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public InsertRowBelowAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Row Below",
                null,
                "Insert a row below the selection",
                KeyStroke.getKeyStroke("alt I"),
                KeyEvent.VK_B);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var q = Util.getSortedSelectionRows(renderer);
        Collections.reverse(q);
        registry.insertRowBelow(q.get(0));
    }
}
