package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.cells.LabelCell;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PasteAction extends MyAction {
    private final SheetRenderer renderer;
    private final SheetRegistry registry;
    @SuppressWarnings("unused") public PasteAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Paste",
                null,
                "Paste text from clipboard",
                KeyStroke.getKeyStroke("control V"),
                KeyEvent.VK_P);
        this.renderer = renderer;
        this.registry = registry;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor))
                registry.at(renderer.cursor, new LabelCell((String) t.getTransferData(DataFlavor.stringFlavor)));
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
        renderer.repaint();
    }
}
