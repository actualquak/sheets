package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import java.awt.event.ActionEvent;

public class DeleteAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    public DeleteAction(SheetRenderer renderer, SheetRegistry registry) {
        super(null, null, null, null, null);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        if (!renderer.enteringData) Util.deleteSelectionFromSheet(renderer, registry);
        renderer.repaint();
    }
}
