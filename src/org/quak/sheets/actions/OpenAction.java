package org.quak.sheets.actions;

import org.quak.sheets.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OpenAction extends MyAction {
    final SheetRenderer renderer;
    @SuppressWarnings("unused") public
    OpenAction(SheetRenderer renderer) {
        super("Open...",
                null,
                "Open a file",
                KeyStroke.getKeyStroke("control O"),
                KeyEvent.VK_O);
        this.renderer = renderer;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var f = Util.openFileName(renderer);
        if(f != null) SheetApplication.main(new String[]{f.getAbsolutePath()});
    }
}
