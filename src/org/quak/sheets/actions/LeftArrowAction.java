package org.quak.sheets.actions;

import org.quak.sheets.CellPosition;
import org.quak.sheets.CellSelection;
import org.quak.sheets.SheetRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LeftArrowAction extends MyAction {
    // Action for when the left arrow is pressed

    // Renderer
    private final SheetRenderer context;
    // Constructor
    public LeftArrowAction(SheetRenderer context) {
        super(null, null, null, null, null);
        this.context = context;
    }
    // When left arrow pressed, move left
    // See DownArrowAction::actionPerformed
    @Override public void actionPerformed(ActionEvent e) {
        if (context.enteringData) return;
        if ((e.getModifiers() & KeyEvent.SHIFT_MASK) > 0
                && context.selection == null)
            context.selection = CellSelection.makeSelection(context.cursor);
        if (context.selection != null)
            context.selection = context.selection.expandLeft();
        context.cursor = new CellPosition(Math.max(context.cursor.col() - 1, 1),
                context.cursor.row());
        context.repaint();
    }
}
