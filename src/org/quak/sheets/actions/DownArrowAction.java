package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.CellSelection;
import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DownArrowAction extends MyAction {
    private final SheetRenderer context;
    public DownArrowAction(SheetRenderer context) {
        super(null, null, null, null, null);
        this.context = context;
    }
    @Override public void actionPerformed(ActionEvent e) {
        if((e.getModifiers() & KeyEvent.SHIFT_MASK) > 0 && context.selection == null)
            context.selection = CellSelection.makeSelection(context.cursor);
        if(context.selection != null) context.selection.expandDown();
        context.cursor = new CellPosition(context.cursor.col(), context.cursor.row() + 1);
        context.repaint();
    }
}
