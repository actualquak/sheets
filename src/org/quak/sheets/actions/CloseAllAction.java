package org.quak.sheets.actions;

import org.quak.sheets.SheetFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAllAction extends MyAction {
    @SuppressWarnings("unused") public CloseAllAction() {
        super("Close",
                null,
                "Close the current file",
                KeyStroke.getKeyStroke("control shift W"),
                KeyEvent.VK_C);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (var frame : SheetFrame.frames) frame.dispose();
    }
}
