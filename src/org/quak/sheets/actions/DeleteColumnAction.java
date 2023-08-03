package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteColumnAction extends MyAction {
    @SuppressWarnings("unused") public DeleteColumnAction() {
        super("Delete Column",
                null,
                "Delete the column(s) selected",
                null,
                KeyEvent.VK_D);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) { }
}
