package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RedoAction extends MyAction {
    @SuppressWarnings("unused") public RedoAction() {
        super("Redo",
                null,
                "Redo last undo",
                KeyStroke.getKeyStroke("control shift z"),
                KeyEvent.VK_R);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
