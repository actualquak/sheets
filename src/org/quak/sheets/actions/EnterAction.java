package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;

public class EnterAction extends MyAction {
    private final SheetRenderer sheetRenderer;
    public EnterAction(SheetRenderer sheetRenderer) {
        super(null, null, null, null, null);
        this.sheetRenderer = sheetRenderer;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        if(sheetRenderer.wasEnteringData) { sheetRenderer.wasEnteringData = false; return; }
        if(sheetRenderer.selection != null) { sheetRenderer.selection = null; sheetRenderer.repaint(); }
        else { sheetRenderer.enteringData = true; sheetRenderer.dataEntry = new StringBuilder(); sheetRenderer.repaint(); }
    }
}
