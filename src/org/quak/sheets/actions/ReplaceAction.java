package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ReplaceAction extends MyAction {
    private final SheetRenderer context;
    @SuppressWarnings("unused") public ReplaceAction(SheetRenderer context) {
        super("Replace",
                null,
                "Replace text in the current file",
                KeyStroke.getKeyStroke("control R"),
                KeyEvent.VK_R);
        this.context = context;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        Util.showDialog(context, true);
    }
}
