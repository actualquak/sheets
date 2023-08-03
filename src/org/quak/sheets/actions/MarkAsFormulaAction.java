package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsFormulaAction extends MyAction {
    @SuppressWarnings("unused") public MarkAsFormulaAction() {
        super("Formula",
                null,
                "Mark selected cell(s) as formulas",
                null,
                KeyEvent.VK_F);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
