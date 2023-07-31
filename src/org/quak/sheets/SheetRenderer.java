package org.quak.sheets;

import org.quak.sheets.actions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SheetRenderer extends JPanel {
    private final SheetFrame frame;
    private SheetRegistry registry;
    private Graphics2D g;
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
        fileMenu.add(getMenuItem(CloseAction.class));
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(getMenuItem(UndoAction.class));
        editMenu.add(getMenuItem(RedoAction.class));
        editMenu.add(getMenuItem(RepeatAction.class));
        menuBar.add(editMenu);

        JMenu insertMenu = new JMenu("Insert");
        insertMenu.setMnemonic(KeyEvent.VK_I);
        JMenu insertColumnMenu = new JMenu("Column...");
        insertColumnMenu.setMnemonic(KeyEvent.VK_C);
        insertColumnMenu.add(getMenuItem(InsertColumnLeftAction.class));
        insertColumnMenu.add(getMenuItem(InsertColumnRightAction.class));
        insertMenu.add(insertColumnMenu);
        JMenu insertRowMenu = new JMenu("Row...");
        insertRowMenu.setMnemonic(KeyEvent.VK_R);
        insertRowMenu.add(getMenuItem(InsertRowAboveAction.class));
        insertRowMenu.add(getMenuItem(InsertRowBelowAction.class));
        insertMenu.add(insertRowMenu);
        insertMenu.add(new JSeparator());
        insertMenu.add(getMenuItem(DeleteColumnAction.class));
        insertMenu.add(getMenuItem(DeleteRowAction.class));
        menuBar.add(insertMenu);

        JMenu dataMenu = new JMenu("Data");
        dataMenu.setMnemonic(KeyEvent.VK_D);
        JMenu markMenu = new JMenu("Mark as...");
        markMenu.setMnemonic(KeyEvent.VK_M);
        markMenu.add(getMenuItem(MarkAsFormulaAction.class));
        markMenu.add(getMenuItem(MarkAsTextAction.class));
        markMenu.add(getMenuItem(MarkAsNumberAction.class));
        dataMenu.add(markMenu);
        menuBar.add(dataMenu);

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
            if (theOneTrueConstructor.getGenericParameterTypes().length == args.length + 1) break;
        }
        if(theOneTrueConstructor == null) throw new IllegalArgumentException();
        try {
            theOneTrueConstructor.setAccessible(true);
            Object[] newArgs = new Object[args.length + 1];
            System.arraycopy(args, 0, newArgs, 1, args.length);
            newArgs[0] = menuItem;
            c = theOneTrueConstructor.newInstance(newArgs);
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
        for(int col = 0; col < 100; col++) {
            for(int row = 0; row < 100; row++) {
                String displayed = registry.at(new CellPosition(col, row)).displayed();
                Dimension textSize = getTextShape(displayed);
                colWidths[col] = Math.max(colWidths[col], textSize.width);
                rowHeights[row] = Math.max(rowHeights[row], textSize.height);
            }
        }
        int x = 0; int y;
        for (int col = 0; true; col++) {
            g.drawLine(x, 0, x, screenSize.height);
            x += 5;
            y = 0;
            for (int row = 0; true; row++) {
                g.drawLine(x - 5, y, x + colWidths[col] + 5, y);
                y += 5;
                y += rowHeights[row];
                String displayed = registry.at(new CellPosition(col, row)).displayed();
                g.drawString(displayed, x, y);
                y += 5;
                if (y >= screenSize.height) break;
            }
            x += colWidths[col];
            x += 5;
            if (x >= screenSize.width) break;
        }
    }
}