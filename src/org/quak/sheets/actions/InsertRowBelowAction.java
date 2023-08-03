package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InsertRowBelowAction extends MyAction {
    @SuppressWarnings("unused") public InsertRowBelowAction() {
        super("Insert Row Below",
                null,
                "Insert a row below the selection",
                null,
                KeyEvent.VK_B);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
