package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class DeleteColumnAction extends MyAction {
    private final SheetRegistry registry;
    private final SheetRenderer renderer;
    @SuppressWarnings("unused") public DeleteColumnAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Delete Column",
                null,
                "Delete the column(s) selected",
                null,
                KeyEvent.VK_D);
        this.registry = registry;
        this.renderer = renderer;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Integer> q = Util.getSortedSelectionColumns(renderer);
        Collections.reverse(q);
        for(int c: q) registry.deleteColumn(c);
    }
}
