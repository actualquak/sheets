package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.CellSelection;
import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UpArrowAction extends MyAction {
    // Action for moving the cursor up

    // Renderer
    private final SheetRenderer context;
    // Constructor
    public UpArrowAction(SheetRenderer context) {
        super(null, null, null, null, null);
        this.context = context;
    }
    // Move the cursor up
    // See DownArrowAction::actionPerformed
    @Override public void actionPerformed(ActionEvent e) {
        if (context.enteringData) return;
        if ((e.getModifiers() & KeyEvent.SHIFT_MASK) > 0
                && context.selection == null)
            context.selection = CellSelection.makeSelection(context.cursor);
        if (context.selection != null)
            context.selection = context.selection.expandUp();
        context.cursor = new CellPosition(context.cursor.col(),
                Math.max(context.cursor.row() - 1, 1));
        context.repaint();
    }
}
