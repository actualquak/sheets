package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends MyAction {
    @SuppressWarnings("unused") public NewAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("New",
                null,
                "Create a blank file",
                null,
                KeyEvent.VK_N);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}