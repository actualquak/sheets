package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends MyAction {
    // Action to save a file, choosing the filename

    // Renderer
    final SheetRenderer renderer;
    // Registry
    final SheetRegistry registry;
    // Constructor
    @SuppressWarnings("unused") public
    SaveAsAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Save as...",
                null,
                "Save the current file as",
                KeyStroke.getKeyStroke("control shift S"),
                KeyEvent.VK_S);
        this.renderer = renderer;
        this.registry = registry;
    }
    // Save file, choosing filename
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var file = Util.selectSaveFileName(renderer);
        registry.save(file);
    }
}
