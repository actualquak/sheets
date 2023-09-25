package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.CellSelection;
import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DownArrowAction extends MyAction {
    // An action to handle moving the cursor down

    // Renderer
    private final SheetRenderer context;
    // Constructor
    public DownArrowAction(SheetRenderer context) {
        super(null, null, null, null, null);
        this.context = context;
    }
    // Perform down arrow action
    @Override public void actionPerformed(ActionEvent e) {
        // If entering data, do nothing
        if (context.enteringData) return;
        // If shift key pressed, expand/create selection
        if ((e.getModifiers() & KeyEvent.SHIFT_MASK) > 0
                && context.selection == null)
            context.selection = CellSelection.makeSelection(context.cursor);
        if (context.selection != null)
            context.selection = context.selection.expandDown();
        // Move cursor
        context.cursor = new CellPosition(context.cursor.col(),
                context.cursor.row() + 1);
        context.repaint();
    }
}
