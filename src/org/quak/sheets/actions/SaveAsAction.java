package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends MyAction {
    @SuppressWarnings("unused") public SaveAsAction() {
        super("Save as...",
                null,
                "Save the current file as",
                KeyStroke.getKeyStroke("control alt s"),
                KeyEvent.VK_S);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
