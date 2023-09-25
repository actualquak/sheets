package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import java.awt.event.ActionEvent;

public class DeleteAction extends MyAction {
    // An action for removing a cell from the spreadsheet

    // Renderer
    private final SheetRenderer renderer;
    // Registry
    private final SheetRegistry registry;
    // Constructor
    public DeleteAction(SheetRenderer renderer, SheetRegistry registry) {
        super(null, null, null, null, null);
        this.renderer = renderer;
        this.registry = registry;
    }
    // Delete selection from spreadsheet
    @Override public void actionPerformed(ActionEvent actionEvent) {
        if (!renderer.enteringData)
            Util.deleteSelectionFromSheet(renderer, registry);
        renderer.repaint();
    }
}
