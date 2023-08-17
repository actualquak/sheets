package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsNumberAction extends MyAction {
    @SuppressWarnings("unused") public MarkAsNumberAction() {
        super("Number",
                null,
                "Mark selected cell(s) as numbers",
                KeyStroke.getKeyStroke("control Q"),
                KeyEvent.VK_N);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        throw new NotYetImplemented();
    }
}
