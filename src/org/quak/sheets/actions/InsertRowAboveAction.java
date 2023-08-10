package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InsertRowAboveAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public InsertRowAboveAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Insert Row Above",
                null,
                "Insert a row above the selection",
                null,
                KeyEvent.VK_A);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Integer> q = Util.getSortedSelectionRows(renderer);
        registry.insertRowBelow(q.get(0) - 1);
    }
}
