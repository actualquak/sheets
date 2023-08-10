package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CopyAction extends MyAction {
    @SuppressWarnings("unused") public CopyAction() {
        super("Copy",
                null,
                "Copy text to clipboard",
                KeyStroke.getKeyStroke("control C"),
                KeyEvent.VK_C);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { throw new NotYetImplemented(); }
}
