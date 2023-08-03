package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnterAction extends MyAction {
    private final SheetRenderer sheetRenderer;
    public EnterAction(SheetRenderer sheetRenderer) {
        super(null, null, null, null, null);
        this.sheetRenderer = sheetRenderer;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        sheetRenderer.selection = null;
        sheetRenderer.repaint();
    }
}
