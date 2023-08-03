package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;

public class OpenAction extends MyAction {
    @SuppressWarnings("unused") public OpenAction() {
        super("Open...",
                null,
                "Open a file",
                KeyStroke.getKeyStroke("control o"),
                KeyEvent.VK_O);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
