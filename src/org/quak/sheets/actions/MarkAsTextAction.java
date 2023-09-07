package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.cells.LabelCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsTextAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public
    MarkAsTextAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Text",
                null,
                "Mark selected cell(s) as text",
                KeyStroke.getKeyStroke("alt Q"),
                KeyEvent.VK_T);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(renderer.selection == null)
            registry.at(renderer.cursor,
                    new LabelCell(registry.at(renderer.cursor).value()));
        else for (var it = renderer.selection.iterator(); it.hasNext(); ) {
            var i = it.next();
            registry.at(i, new LabelCell(registry.at(i).value()));
        }
        renderer.repaint();
    }
}
