package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAction extends MyAction {
    public CloseAction() {
        super("Close", null, "Close File", KeyStroke.getKeyStroke("control w"), KeyEvent.VK_W);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
