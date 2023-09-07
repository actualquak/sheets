package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.cells.NumberCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsNumberAction extends MyAction {
    private final SheetRegistry registry;
    private final SheetRenderer renderer;
    @SuppressWarnings("unused") public
    MarkAsNumberAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Number",
                null,
                "Mark selected cell(s) as numbers",
                KeyStroke.getKeyStroke("control Q"),
                KeyEvent.VK_N);
        this.registry = registry;
        this.renderer = renderer;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(renderer.selection == null)
            try {
                registry.at(renderer.cursor,
                        new NumberCell(registry.at(renderer.cursor).value()));
            } catch (NumberFormatException ignored) { }
        else for (var it = renderer.selection.iterator(); it.hasNext(); ) {
            var i = it.next();
            try { registry.at(i, new NumberCell(registry.at(i).value())); }
            catch(NumberFormatException ignored) { }
        }
        renderer.repaint();
    }
}
