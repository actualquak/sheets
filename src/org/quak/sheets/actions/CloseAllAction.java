package org.quak.sheets.actions;

import org.quak.sheets.SheetFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAllAction extends MyAction {
    @SuppressWarnings("unused") public CloseAllAction() {
        super("Close All",
                null,
                "Close all files",
                KeyStroke.getKeyStroke("control shift W"),
                KeyEvent.VK_A);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (var frame : SheetFrame.frames) frame.dispose();
    }
}
