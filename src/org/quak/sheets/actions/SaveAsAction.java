package org.quak.sheets.actions;

import org.quak.sheets.NotYetImplemented;
import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends MyAction {
    final SheetRenderer renderer;
    final SheetRegistry registry;
    @SuppressWarnings("unused") public SaveAsAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Save as...",
                null,
                "Save the current file as",
                KeyStroke.getKeyStroke("control alt S"),
                KeyEvent.VK_S);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        throw new NotYetImplemented();
    }
}
