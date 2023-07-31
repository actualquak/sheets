package org.quak.sheets.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsTextAction extends MyAction {
    @SuppressWarnings("unused") public MarkAsTextAction(@SuppressWarnings("unused") JMenuItem menuItem) {
        super("Text",
                null,
                "Mark selected cell(s) as text",
                null,
                KeyEvent.VK_T);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) { }
}
