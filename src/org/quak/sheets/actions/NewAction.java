package org.quak.sheets.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends MyAction {
    public NewAction() {
        super("New", null, "Create New File", null, KeyEvent.VK_N);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
