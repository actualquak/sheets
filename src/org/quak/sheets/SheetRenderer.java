package org.quak.sheets;

import org.quak.sheets.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SheetRenderer extends JPanel {
    public final SheetFrame frame;
    private final SheetRegistry registry;
    private Graphics2D g;
    public CellSelection selection = null;
    public CellPosition cursor = new CellPosition(1, 1);
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        this.frame = frame;
        this.registry = registry;

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(getMenuItem(NewAction.class));
        fileMenu.add(getMenuItem(OpenAction.class));
        fileMenu.add(getMenuItem(SaveAction.class));
        fileMenu.add(getMenuItem(SaveAsAction.class));
        fileMenu.add(getMenuItem(CloseAction.class, this));
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(getMenuItem(UndoAction.class));
        editMenu.add(getMenuItem(RedoAction.class));
        editMenu.add(new JSeparator());
        editMenu.add(getMenuItem(CutAction.class));
        editMenu.add(getMenuItem(CopyAction.class));
        editMenu.add(getMenuItem(PasteAction.class));
        editMenu.add(new JSeparator());
        editMenu.add(getMenuItem(FindAction.class, this));
        editMenu.add(getMenuItem(ReplaceAction.class, this));
        menuBar.add(editMenu);

        JMenu cellMenu = new JMenu("Cell");
        cellMenu.setMnemonic(KeyEvent.VK_C);
        cellMenu.add(getMenuItem(InsertColumnLeftAction.class, this, registry));
        cellMenu.add(getMenuItem(InsertColumnRightAction.class, this, registry));
        cellMenu.add(getMenuItem(InsertRowAboveAction.class, this, registry));
        cellMenu.add(getMenuItem(InsertRowBelowAction.class, this, registry));
        cellMenu.add(new JSeparator());
        cellMenu.add(getMenuItem(DeleteColumnAction.class, this, registry));
        cellMenu.add(getMenuItem(DeleteRowAction.class, this, registry));
        cellMenu.add(new JSeparator());
        JMenu markMenu = new JMenu("Mark as...");
        markMenu.setMnemonic(KeyEvent.VK_M);
        markMenu.add(getMenuItem(MarkAsFormulaAction.class));
        markMenu.add(getMenuItem(MarkAsTextAction.class));
        markMenu.add(getMenuItem(MarkAsNumberAction.class));
        cellMenu.add(markMenu);
        cellMenu.add(new JSeparator());
        menuBar.add(cellMenu);

        registerAction("DOWN", new DownArrowAction(this));
        registerAction("ENTER", new EnterAction(this));
        registerAction("LEFT", new LeftArrowAction(this));
        registerAction("RIGHT", new RightArrowAction(this));
        registerAction("UP", new UpArrowAction(this));
        registerAction("shift DOWN", new DownArrowAction(this));
        registerAction("shift LEFT", new LeftArrowAction(this));
        registerAction("shift RIGHT", new RightArrowAction(this));
        registerAction("shift UP", new UpArrowAction(this));

        frame.setJMenuBar(menuBar);
    }
    private JMenuItem getMenuItem(Class<? extends MyAction> actionType, Object... args) throws IllegalArgumentException {
        // Today's episode of the idiocy show - what lengths will our loveable idiot Stanley go to avoid typing
        // which doesn't actually save him any typing? QUITE FAR IT SEEMS.
        JMenuItem menuItem = new JMenuItem(); Object c;
        Constructor<?>[] constructors = actionType.getConstructors();
        Constructor<?> theOneTrueConstructor = null;
        for (Constructor<?> constructor : constructors) {
            theOneTrueConstructor = constructor;
            if (theOneTrueConstructor.getGenericParameterTypes().length == args.length) break;
        }
        if(theOneTrueConstructor == null) throw new IllegalArgumentException();
        try {
            theOneTrueConstructor.setAccessible(true);
            c = theOneTrueConstructor.newInstance(args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
        menuItem.setAction((Action) c);
        return menuItem;
    }
    private Dimension getTextShape(String text) {
        Rectangle2D d2d = g.getFontMetrics().getStringBounds(text, g);
        Dimension d = new Dimension();
        d.setSize(d2d.getWidth(), d2d.getHeight());
        return d;
    }
    @Override public void paintComponent(Graphics graphics) {
        g = (Graphics2D) graphics;
        Dimension screenSize = getSize();
        int[] colWidths = new int[100];
        int[] rowHeights = new int[100];
        for(int col = 0; col < 100; col++) { // TODO better stuff here
            for(int row = 0; row < 100; row++) { // TODO better stuff here too
                String displayed = registry.at(new CellPosition(col, row)).displayed();
                Dimension textSize = getTextShape(displayed);
                colWidths[col] = Math.max(colWidths[col], textSize.width);
                rowHeights[row] = Math.max(rowHeights[row], textSize.height);
            }
        }
        int x = 0; int y;
        for (int col = 0; true; col++) {
            //g.drawLine(x, 0, x, screenSize.height);
            x += 5;
            y = 0;
            for (int row = 0; true; row++) {
                if((col == cursor.col() || col == cursor.col() + 1) && row == cursor.row()) g.setColor(Color.RED);
                else if(selection != null && (selection.isIn(new CellPosition(col, row))
                        || selection.isIn(new CellPosition(col - 1, row))))
                    g.setColor(Color.GREEN);
                else g.setColor(Color.BLACK);
                g.drawLine(x - 5, y, x - 5, y + rowHeights[row] + 10);
                if(col == cursor.col() && (row == cursor.row() || row == cursor.row() + 1)) g.setColor(Color.RED);
                else if(selection != null && (selection.isIn(new CellPosition(col, row))
                        || selection.isIn(new CellPosition(col, row - 1))))
                    g.setColor(Color.GREEN);
                else g.setColor(Color.BLACK);
                g.drawLine(x - 5, y, x + colWidths[col] + 5, y);
                y += 5;
                y += rowHeights[row];
                String displayed = registry.at(new CellPosition(col, row)).displayed();
                g.setColor(Color.BLACK);
                g.drawString(displayed, x, y);
                y += 5;
                if (y >= screenSize.height) break;
            }
            x += colWidths[col];
            x += 5;
            if (x >= screenSize.width) break;
        }
    }
    private void registerAction(String code, Action action) {
        getInputMap().put(KeyStroke.getKeyStroke(code), code);
        getActionMap().put(code, action);
    }
}