package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;

public class EnterAction extends MyAction {
    // Class for handling pressing the enter key

    // Registry
    private final SheetRenderer sheetRenderer;
    // Constructor
    public EnterAction(SheetRenderer sheetRenderer) {
        super(null, null, null, null, null);
        this.sheetRenderer = sheetRenderer;
    }
    // Enter key pressed
    @Override public void actionPerformed(ActionEvent actionEvent) {
        // If entering data, commit data entry
        if (sheetRenderer.wasEnteringData) {
            sheetRenderer.wasEnteringData = false;
            return;
        }
        // Otherwise clear selection
        if (sheetRenderer.selection != null) {
            sheetRenderer.selection = null;
            sheetRenderer.repaint();
        } else {
            // Otherwise start entering data
            sheetRenderer.enteringData = true;
            sheetRenderer.dataEntry = new StringBuilder();
            sheetRenderer.repaint();
        }
    }
}
