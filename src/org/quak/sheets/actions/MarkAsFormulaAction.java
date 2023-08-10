package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsFormulaAction extends MyAction {
    @SuppressWarnings("unused") public MarkAsFormulaAction() {
        super("Formula",
                null,
                "Mark selected cell(s) as formulas",
                KeyStroke.getKeyStroke("control alt Q"),
                KeyEvent.VK_F);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { throw new NotYetImplemented(); }
}
