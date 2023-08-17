package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UndoAction extends MyAction {
    @SuppressWarnings("unused") public UndoAction() {
        super("Undo",
                null,
                "Undo last edit",
                KeyStroke.getKeyStroke("control Z"),
                KeyEvent.VK_U);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    }
}
