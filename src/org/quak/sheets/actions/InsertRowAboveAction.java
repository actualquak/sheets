package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertRowAboveAction extends MyAction {
    @SuppressWarnings("unused") public InsertRowAboveAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Above",
                null,
                "Insert a row above the selection",
                null,
                KeyEvent.VK_A);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
