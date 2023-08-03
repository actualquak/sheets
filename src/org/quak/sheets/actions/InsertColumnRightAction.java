package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertColumnRightAction extends MyAction {
    @SuppressWarnings("unused") public InsertColumnRightAction() {
        super("Insert Column Right",
                null,
                "Insert a column to the right of the selection",
                null,
                KeyEvent.VK_R);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
