package org.quak.sheets.actions;

import org.quak.sheets.SheetApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends MyAction {
    @SuppressWarnings("unused") public NewAction() {
        super("New",
                null,
                "Create a blank file",
                KeyStroke.getKeyStroke("control N"),
                KeyEvent.VK_N);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { SheetApplication.main(new String[]{}); }
}
