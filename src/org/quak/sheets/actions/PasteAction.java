package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PasteAction extends MyAction {
    @SuppressWarnings("unused") public PasteAction() {
        super("Paste",
                null,
                "Paste text from clipboard",
                KeyStroke.getKeyStroke("control V"),
                KeyEvent.VK_P);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        throw new NotYetImplemented();
    }
}
