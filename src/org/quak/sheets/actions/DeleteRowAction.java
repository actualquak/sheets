package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteRowAction extends MyAction {
    @SuppressWarnings("unused") public DeleteRowAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Delete Row",
                null,
                "Delete the row(s) selected",
                null,
                KeyEvent.VK_E);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
