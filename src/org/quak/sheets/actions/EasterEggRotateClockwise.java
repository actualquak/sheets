package org.quak.sheets.actions;

import org.quak.sheets.SheetRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EasterEggRotateClockwise extends MyAction {
    private final SheetRenderer context;
    public EasterEggRotateClockwise(SheetRenderer context) {
        super(null, null, null,
                KeyStroke.getKeyStroke("control alt ESCAPE"), null);
        this.context = context;
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        context.easter += Math.PI / 16;
        if (Math.abs(context.easter) < 0.1) context.easter = 0;
        context.repaint();
    }
}
