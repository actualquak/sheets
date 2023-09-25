package org.quak.sheets.actions;

import org.quak.sheets.SheetApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends MyAction {
    // Action to create and open a new file

    // Constructor
    @SuppressWarnings("unused") public NewAction() {
        super("New",
                null,
                "Create a blank file",
                KeyStroke.getKeyStroke("control N"),
                KeyEvent.VK_N);
    }
    // Create and open new file
    @Override public void actionPerformed(ActionEvent actionEvent) {
        SheetApplication.main(new String[]{});
    }
}
