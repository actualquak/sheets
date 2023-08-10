package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;

import javax.swing.*;
import java.awt.event.*;

public class OpenAction extends MyAction {
    @SuppressWarnings("unused") public OpenAction() {
        super("Open...",
                null,
                "Open a file",
                KeyStroke.getKeyStroke("control O"),
                KeyEvent.VK_O);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { throw new NotYetImplemented(); }
}
