package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAction extends MyAction {
    public SaveAction() {
        super("Save", null, "Save File", KeyStroke.getKeyStroke("control S"), KeyEvent.VK_T);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
