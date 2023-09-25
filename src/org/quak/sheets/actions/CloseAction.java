package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAction extends MyAction {
    // Action for closing a file

    // Renderer
    private final SheetRenderer renderer;
    // Constructor
    @SuppressWarnings("unused") public CloseAction(SheetRenderer renderer) {
        super("Close",
                null,
                "Close the current file",
                KeyStroke.getKeyStroke("control W"),
                KeyEvent.VK_C);
        this.renderer = renderer;
    }
    // Closes the current window
    @Override public void actionPerformed(ActionEvent actionEvent) {
        renderer.frame.dispose();
    }
}
