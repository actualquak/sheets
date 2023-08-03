package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RepeatAction extends MyAction {
    @SuppressWarnings("unused") public RepeatAction() {
        super("Repeat",
                null,
                "Repeat last operation",
                KeyStroke.getKeyStroke("control shift y"),
                KeyEvent.VK_E);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
