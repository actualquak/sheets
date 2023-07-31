package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAction extends MyAction {
    @SuppressWarnings("unused") public CloseAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Close",
                null,
                "Close the current file",
                KeyStroke.getKeyStroke("control w"),
                KeyEvent.VK_C);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
