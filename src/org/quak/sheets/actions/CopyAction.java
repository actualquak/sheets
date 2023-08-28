package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CopyAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public CopyAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Copy",
                null,
                "Copy text to clipboard",
                KeyStroke.getKeyStroke("control C"),
                KeyEvent.VK_C);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(Util.copySelectionFromSheet(renderer, registry), null);
        renderer.selection = null;
        renderer.repaint();
    }
}
