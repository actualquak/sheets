package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FindAction extends MyAction {
    private final SheetRenderer context;
    @SuppressWarnings("unused") public FindAction(SheetRenderer context) {
        super("Find",
                null,
                "Find text in the current file",
                KeyStroke.getKeyStroke("control F"),
                KeyEvent.VK_F);
        this.context = context;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        Util.showDialog(context, false);
    }
}
