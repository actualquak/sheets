package org.quak.sheets;

import org.quak.sheets.actions.*;
import org.quak.sheets.cells.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class SheetRenderer extends JPanel implements KeyListener {
    // Perhaps a more apt name for this component would be "SheetPanel"
    // It is effectively a custom implementation of a JTable, with custom logic
    // etc... However, it does have its problems, particularly with speed -
    // many parts of the algorithm are badly optimized

    // The window frame this component is embedded into
    public final SheetFrame frame;
    // The SheetRegistry "backing" this renderer
    private final SheetRegistry registry;
    // The current selection of cells
    public CellSelection selection = null;
    // Flag used in data entry
    public boolean enteringData = false;
    // Cached flag used in data entry
    public boolean wasEnteringData = false;
    // Current data being entered
    public StringBuilder dataEntry = new StringBuilder();
    public double easter = 0;
    // Cursor position
    public CellPosition cursor = new CellPosition(1, 1);
    // Cached number of columns
    int oldColNum = 100;
    // Cached number of rows
    int oldRowNum = 100;
    // Top left cell currently being displayed
    private CellPosition topLeftCell = new CellPosition(0, 0);
    // Graphics2D object
    private Graphics2D g;
    // Constructor
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        // Set frame
        this.frame = frame;
        // Set registry
        this.registry = registry;

        // Register itself as a KeyListener
        addKeyListener(this);

        // Create menu bar
        var menuBar = new JMenuBar();

        // Create file menu
        var fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(getMenuItem(NewAction.class));
        fileMenu.add(getMenuItem(OpenAction.class, this));
        fileMenu.add(getMenuItem(SaveAction.class, this, registry));
        fileMenu.add(getMenuItem(SaveAsAction.class, this, registry));
        fileMenu.add(getMenuItem(CloseAction.class, this));
        fileMenu.add(getMenuItem(CloseAllAction.class));
        menuBar.add(fileMenu);

        // Create edit menu
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(getMenuItem(CutAction.class, this, registry));
        editMenu.add(getMenuItem(CopyAction.class, this, registry));
        editMenu.add(getMenuItem(PasteAction.class, this, registry));
        menuBar.add(editMenu);

        // Create cell menu
        var cellMenu = new JMenu("Cell");
        cellMenu.setMnemonic(KeyEvent.VK_C);
        cellMenu.add(getMenuItem(
                InsertColumnLeftAction.class, this, registry));
        cellMenu.add(getMenuItem(
                InsertColumnRightAction.class, this, registry));
        cellMenu.add(getMenuItem(
                InsertRowAboveAction.class, this, registry));
        cellMenu.add(getMenuItem(
                InsertRowBelowAction.class, this, registry));
        cellMenu.add(new JSeparator());
        cellMenu.add(getMenuItem(
                DeleteColumnAction.class, this, registry));
        cellMenu.add(getMenuItem(
                DeleteRowAction.class, this, registry));
        cellMenu.add(new JSeparator());
        var markMenu = new JMenu("Mark as...");
        markMenu.setMnemonic(KeyEvent.VK_M);
        markMenu.add(getMenuItem(
                MarkAsFormulaAction.class, this, registry));
        markMenu.add(getMenuItem(
                MarkAsTextAction.class, this, registry));
        markMenu.add(getMenuItem(
                MarkAsNumberAction.class, this, registry));
        cellMenu.add(markMenu);
        cellMenu.add(new JSeparator());
        menuBar.add(cellMenu);

        // Register (some) keyboard controls
        // Other keyboard controls are in keyPressed and keyTyped,
        // specifically for data entry
        registerAction("DELETE", new DeleteAction(this, registry));
        registerAction("DOWN", new DownArrowAction(this));
        registerAction("ENTER", new EnterAction(this));
        registerAction("LEFT", new LeftArrowAction(this));
        registerAction("RIGHT", new RightArrowAction(this));
        registerAction("UP", new UpArrowAction(this));
        registerAction("shift DOWN", new DownArrowAction(this));
        registerAction("shift LEFT", new LeftArrowAction(this));
        registerAction("shift RIGHT", new RightArrowAction(this));
        registerAction("shift UP", new UpArrowAction(this));
        registerAction("control alt shift ESCAPE",
                new Easter1(this));
        registerAction("control alt ESCAPE",
                new Easter2(this));

        // Set menu bar
        frame.setJMenuBar(menuBar);
    }
    private JMenuItem
    getMenuItem(Class<? extends MyAction> actionType, Object... args)
            throws IllegalArgumentException {
        // This used reflection to achieve the DRY principle
        // It fetches the constructor of the class actionType
        // with the same argument list length as args.length,
        // calls it with args as its arguments, wraps it in a JMenuItem,
        // and returns the JMenuItem wrapper
        // Create menuItem
        var menuItem = new JMenuItem();
        Object c; // We will store the constructed object here
        // Get list of constructors
        var constructors = actionType.getConstructors();
        Constructor<?> theOneTrueConstructor = null;
        for (var constructor : constructors) {
            // Iterate over constructors, looking for the "correct" one
            // (parameter list length == args.length)
            theOneTrueConstructor = constructor;
            if (theOneTrueConstructor.getGenericParameterTypes().length
                    == args.length) break;
        }
        if (theOneTrueConstructor == null) // No "good" constructor
            throw new IllegalArgumentException();
        // Try invoking constructor
        try {
            theOneTrueConstructor.setAccessible(true);
            c = theOneTrueConstructor.newInstance(args);
        } catch (InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            // There are a LOT of things that can go wrong with reflection,
            // so this serves as a "kitchen sink" exception
            throw new IllegalArgumentException();
        }
        // Set action for menuItem
        menuItem.setAction((Action) c);
        return menuItem;
    }
    private Dimension getTextShape(String text) {
        // Get the size of a string, when rendered onto the screen
        var d2d = g.getFontMetrics().getStringBounds(text, g);
        var d = new Dimension();
        d.setSize(d2d.getWidth(), d2d.getHeight());
        return d;
    }
    @Override public void paintComponent(Graphics graphics) {
        // Renders the component, with all it's internal state
        // Set column top and row top
        var col_top = topLeftCell.col();
        var row_top = topLeftCell.row();
        if (cursor.col() <= col_top) {
            // Left arrow has been pressed, and cursor scrolled offscreen
            topLeftCell = new CellPosition(
                    topLeftCell.col() - 1, topLeftCell.row());
            repaint();
            return;
        }
        if (cursor.row() <= row_top) {
            // Up arrow has been pressed, and cursor scrolled offscreen
            topLeftCell = new CellPosition(
                    topLeftCell.col(), topLeftCell.row() - 1);
            repaint();
            return;
        }
        if (cursor.col() >= col_top + oldColNum + 1
            || cursor.col() >= col_top + oldColNum && oldColNum > 2) {
            // Right arrow has been pressed, and cursor scrolled offscreen
            topLeftCell = new CellPosition(
                    topLeftCell.col() + 1, topLeftCell.row());
            repaint();
            return;
        }
        if (cursor.row() >= row_top + oldRowNum + 1
            || cursor.row() >= row_top + oldRowNum && oldRowNum > 2) {
            // Down arrow has been pressed, and cursor scrolled offscreen
            topLeftCell = new CellPosition(
                    topLeftCell.col(), topLeftCell.row() + 1);
            repaint();
            return;
        }

        // Reset column/row counts
        oldRowNum = 1;
        oldColNum = 1;

        // Set graphics
        g = (Graphics2D) graphics;
        // Call parent method, to clear area to draw into
        super.paintComponent(g);
        String displayed;
        var screenSize = getSize();
        g.rotate(easter, screenSize.getWidth() / 2,
                screenSize.getHeight() / 2);
        // Hopefully nobody's screen is so big that it displays more
        // than 100 columns or rows at once
        // Array of column widths
        var colWidths = new int[100];
        // Columns should be at least 20px wide
        Arrays.fill(colWidths, 20);
        // Array of row heights
        var rowHeights = new int[100];
        // Rows should be at least 10 px high
        Arrays.fill(rowHeights, 10);
        for (var col = col_top; col < col_top + 100; col++) {
            for (var row = row_top; row < row_top + 100; row++) {
                // Cell text being edited is stored separately
                if (enteringData && col == cursor.col() && row == cursor.row())
                    displayed = dataEntry.toString();
                else displayed = registry.at(new CellPosition(col, row))
                        .displayed();
                var textSize = getTextShape(displayed);
                // Adjust row/column heights/widths
                colWidths[col - col_top] = Math.max(colWidths[col - col_top],
                        textSize.width);
                rowHeights[row - row_top] = Math.max(rowHeights[row - row_top],
                        textSize.height);
            }
        }
        // Get height of top bar
        var topBarHeight = Arrays.stream(rowHeights).max().getAsInt() + 10;
        // Temporary variables for drawing
        var x = 0;
        int y;
        for (var col = col_top; true; col++) {
            // Leave space to left of text
            x += 5;
            // Allow space for top bar
            y = topBarHeight;
            for (var row = row_top; true; row++) {
                if(col == cursor.col() && row == cursor.row()) {
                    g.setColor(new Color(0, 0, 0, 30));
                    g.fillRect(x - 5, y, colWidths[col] + 10,
                            rowHeights[row] + 10);
                }
                // Draw cursor borders in red
                if ((col == cursor.col() || col == cursor.col() + 1)
                        && row == cursor.row()) g.setColor(Color.RED);
                // Draw selection borders in green
                else if (selection != null && (selection.isIn(
                        new CellPosition(col, row)) || selection.isIn(
                                new CellPosition(col - 1, row))))
                    g.setColor(Color.GREEN);
                // Otherwise use black
                else g.setColor(Color.BLACK);
                // Draw vertical border
                g.drawLine(x - 5, y, x - 5, y + rowHeights[row - row_top] + 10);
                // Almost the same as above, but not quite
                if (col == cursor.col() && (row == cursor.row() ||
                        row == cursor.row() + 1)) g.setColor(Color.RED);
                else if (selection != null && (selection.isIn(
                        new CellPosition(col, row)) || selection.isIn(
                                new CellPosition(col, row - 1))))
                    g.setColor(Color.GREEN);
                else g.setColor(Color.BLACK);
                // Draw horizontal border
                g.drawLine(x - 5, y, x + colWidths[col - col_top] + 5, y);
                // Leave space above text
                y += 5;
                // Draw text, in black
                // Increment Y by row height
                y += rowHeights[row - row_top];
                g.setColor(Color.BLACK);
                if (enteringData && col == cursor.col() && row == cursor.row())
                    displayed = dataEntry.toString();
                else displayed = registry.at(new CellPosition(col, row))
                        .displayed();
                // Note that text is drawn from the bottom left corner
                g.drawString(displayed, x, y);
                // Leave space below text
                y += 5;
                // If this condition holds, we've gone too
                // far, so we need to stop drawing
                // Really this is just an (extremely)
                // rudimentary performance optimization
                if (y >= screenSize.height) {
                    oldRowNum = Math.max(row - row_top, oldRowNum);
                    break;
                }
            }
            // Increment X by column width
            x += colWidths[col - col_top];
            // Leave space to right of text
            x += 5;
            // Same as above condition, but for width instead
            if (x >= screenSize.width) {
                oldColNum = Math.max(col - col_top, oldColNum);
                break;
            }
        }
        // Draw top bar last
        if (enteringData)
            g.drawString(dataEntry.toString(), 5, topBarHeight - 5);
        else g.drawString(registry.at(cursor).value(),
                5, topBarHeight - 5);
    }
    private void registerAction(String code, Action action) {
        // Register an action into the InputMap and ActionMap
        // Again, this is DRY principle at work
        getInputMap().put(KeyStroke.getKeyStroke(code), code);
        getActionMap().put(code, action);
    }
    @Override public void keyTyped(KeyEvent e) {
        // When a key is typed, this method is called
        // It inserts the key into the data entry
        var c = e.getKeyChar();
        // Keys we don't want to insert into the key map
        if (c == KeyEvent.CHAR_UNDEFINED ||
                (Character.isISOControl(c) && c != '\b') ||
                (e.getModifiersEx() & (KeyEvent.ALT_DOWN_MASK |
                        KeyEvent.CTRL_DOWN_MASK)) != 0) return;
        // If not entering data, start doing so
        if (!enteringData) dataEntry = new StringBuilder();
        enteringData = true;
        // If backspace was pressed, decrement length
        // instead of appending to end of text
        if (c == '\b') {
            if (dataEntry.length() > 0)
                dataEntry.setLength(dataEntry.length() - 1);
            // Otherwise append to end of text
        } else dataEntry.append(c);
        repaint(); // Repaint UI
    }
    @Override public void keyPressed(KeyEvent e) {
        // When a key is pressed, this method is called
        // It inserts handles the key if data is being entered
        if (!enteringData) return; // Don't do anything if no data entry
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
                    KeyEvent.VK_RIGHT, KeyEvent.VK_UP -> {
                // Arrow keys move cursor (*ArrowAction) and commit data entry
                enteringData = false;
                registry.at(cursor, Cell.make(dataEntry.toString(), registry));
                // repaint() will be called by *ArrowAction
            }
            case KeyEvent.VK_ENTER -> {
                // Enter keys just commit data entry
                enteringData = false;
                wasEnteringData = true;
                registry.at(cursor, Cell.make(dataEntry.toString(), registry));
                repaint(); // Not sure if this call is actually necessary
            }
        }
    }
    @Override public void keyReleased(KeyEvent e) {
        // Blank, just here because all interface methods need implementation
    }
}
