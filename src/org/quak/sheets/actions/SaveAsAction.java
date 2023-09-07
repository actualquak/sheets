package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends MyAction {
    final SheetRenderer renderer;
    final SheetRegistry registry;
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
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var file = Util.selectSaveFileName(renderer, registry);
        registry.save(file);
    }
}
