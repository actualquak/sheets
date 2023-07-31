package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsNumberAction extends MyAction {
    @SuppressWarnings("unused") public MarkAsNumberAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Number",
                null,
                "Mark selected cell(s) as numbers",
                null,
                KeyEvent.VK_N);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
