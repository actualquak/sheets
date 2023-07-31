package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAction extends MyAction {
    @SuppressWarnings("unused") public SaveAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Save",
                null,
                "Save the current file",
                KeyStroke.getKeyStroke("control S"),
                KeyEvent.VK_T);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}