package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveAction extends MyAction {
    final SheetRenderer renderer;
    final SheetRegistry registry;
    @SuppressWarnings("unused") public
    SaveAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Save",
            null,
            "Save the current file",
            KeyStroke.getKeyStroke("control S"),
            KeyEvent.VK_T);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(registry.fileName.get() == null) {
            var file = Util.selectSaveFileName(renderer);
            registry.save(file);
        } else registry.save(registry.fileName.get());
    }
}
