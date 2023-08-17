package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CutAction extends MyAction {
    @SuppressWarnings("unused") public CutAction() {
        super("Cut",
                null,
                "Cut text to clipboard",
                KeyStroke.getKeyStroke("control X"),
                KeyEvent.VK_T);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        throw new NotYetImplemented();
    }
}
