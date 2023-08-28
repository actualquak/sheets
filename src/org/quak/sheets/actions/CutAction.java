package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CutAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public CutAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Cut",
                null,
                "Cut text to clipboard",
                KeyStroke.getKeyStroke("control X"),
                KeyEvent.VK_T);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        var t = Util.copySelectionFromSheet(renderer, registry);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, null);
        Util.deleteSelectionFromSheet(renderer, registry);
        renderer.selection = null;
        renderer.repaint();
    }
}
