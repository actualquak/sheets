package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertColumnLeftAction extends MyAction {
    @SuppressWarnings("unused") public InsertColumnLeftAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Left",
                null,
                "Insert a column to the left of the selection",
                null,
                KeyEvent.VK_L);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
