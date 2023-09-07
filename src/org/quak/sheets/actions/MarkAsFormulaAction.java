package org.quak.sheets.actions;

import org.quak.sheets.SheetRegistry;
import org.quak.sheets.SheetRenderer;
import org.quak.sheets.cells.formula.FormulaCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MarkAsFormulaAction extends MyAction {
    private final SheetRegistry registry;
    private final SheetRenderer renderer;
    @SuppressWarnings("unused") public MarkAsFormulaAction(SheetRenderer renderer, SheetRegistry registry) {
        super("Formula",
                null,
                "Mark selected cell(s) as formulas",
                KeyStroke.getKeyStroke("control alt Q"),
                KeyEvent.VK_F);
        this.registry = registry;
        this.renderer = renderer;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(renderer.selection == null)
            registry.at(renderer.cursor, new FormulaCell(registry.at(renderer.cursor).value(), renderer.cursor));
        else for (var it = renderer.selection.iterator(); it.hasNext(); ) {
            var i = it.next();
            registry.at(i, new FormulaCell(registry.at(i).value(), i));
        }
        renderer.repaint();
    }
}
