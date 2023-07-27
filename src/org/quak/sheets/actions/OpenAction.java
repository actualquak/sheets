package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;

public class OpenAction extends MyAction {
    public OpenAction() {
        super("Open...", null, "Open File", KeyStroke.getKeyStroke("control o"), KeyEvent.VK_O);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
