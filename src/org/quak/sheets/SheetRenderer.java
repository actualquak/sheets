package org.quak.sheets;

import org.quak.sheets.actions.*;
import org.quak.sheets.cells.LabelCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class SheetRenderer extends JPanel implements KeyListener, MouseListener {
    public final SheetFrame frame;
    private final SheetRegistry registry;
    public CellSelection selection = null;
    public boolean enteringData = false;
    public boolean wasEnteringData = false;
    public StringBuilder dataEntry = new StringBuilder();
    public double easter = 0;
    private CellPosition topLeftCell = new CellPosition(0, 0);
    int oldColNum = 100;
    int oldRowNum = 100;
    public CellPosition cursor = new CellPosition(1, 1);
    private Graphics2D g;
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        this.frame = frame;
        this.registry = registry;

        addKeyListener(this);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(getMenuItem(NewAction.class));
        fileMenu.add(getMenuItem(OpenAction.class));
        fileMenu.add(getMenuItem(SaveAction.class));
        fileMenu.add(getMenuItem(SaveAsAction.class));
        fileMenu.add(getMenuItem(CloseAction.class, this));
        fileMenu.add(getMenuItem(CloseAllAction.class));
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
        registerAction("control alt shift ESCAPE", new EasterEggRotateAntiClockwise(this));
        registerAction("control alt ESCAPE", new EasterEggRotateClockwise(this));

        frame.setJMenuBar(menuBar);
    }
    private JMenuItem getMenuItem(Class<? extends MyAction> actionType, Object... args) throws IllegalArgumentException {
        // Today's episode of the idiocy show - what lengths will our loveable idiot Stanley go to avoid typing
        // which doesn't actually save him any typing? QUITE FAR IT SEEMS.
        JMenuItem menuItem = new JMenuItem();
        Object c;
        Constructor<?>[] constructors = actionType.getConstructors();
        Constructor<?> theOneTrueConstructor = null;
        for (Constructor<?> constructor : constructors) {
            theOneTrueConstructor = constructor;
            if (theOneTrueConstructor.getGenericParameterTypes().length == args.length) break;
        }
        if (theOneTrueConstructor == null) throw new IllegalArgumentException();
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
        int col_top = topLeftCell.col();
        int row_top = topLeftCell.row();
        if(cursor.col() <= topLeftCell.col()) {
            topLeftCell = new CellPosition(topLeftCell.col() - 1, topLeftCell.row());
            repaint();
            return;
        }
        if(cursor.row() <= topLeftCell.row()) {
            topLeftCell = new CellPosition(topLeftCell.col(), topLeftCell.row() - 1);
            repaint();
            return;
        }
        if(cursor.col() >= topLeftCell.col() + oldColNum) {
            topLeftCell = new CellPosition(topLeftCell.col() + 1, topLeftCell.row());
            repaint();
            return;
        }
        if(cursor.row() >= topLeftCell.row() + oldRowNum) {
            topLeftCell = new CellPosition(topLeftCell.col(), topLeftCell.row() + 1);
            repaint();
            return;
        }
        oldRowNum = 0; oldColNum = 0;

        g = (Graphics2D) graphics;
        super.paintComponent(g);
        String displayed;
        Dimension screenSize = getSize();
        g.rotate(easter, screenSize.getWidth() / 2, screenSize.getHeight() / 2);
        int[] colWidths = new int[100];
        int[] rowHeights = new int[100];
        for (int col = col_top; col < col_top + 100; col++) { // TODO maybe 100 columns is the wrong number. Also scrolling
            for (int row = row_top; row < row_top + 100; row++) { // TODO maybe 100 rows is the wrong number. Also scrolling
                if (enteringData && col == cursor.col() && row == cursor.row()) displayed = dataEntry.toString();
                else displayed = registry.at(new CellPosition(col, row)).displayed();
                Dimension textSize = getTextShape(displayed);
                colWidths[col - col_top] = Math.max(colWidths[col - col_top], textSize.width);
                rowHeights[row - row_top] = Math.max(rowHeights[row - row_top], textSize.height);
            }
        }
        int topBarHeight = Arrays.stream(rowHeights).max().getAsInt() + 10;
        int x = 0;
        int y;
        for (int col = col_top; true; col++) {
            x += 5;
            y = topBarHeight;
            for (int row = row_top; true; row++) {
                if ((col == cursor.col() || col == cursor.col() + 1) && row == cursor.row()) g.setColor(Color.RED);
                else if (selection != null && (selection.isIn(new CellPosition(col, row)) || selection.isIn(new CellPosition(col - 1, row))))
                    g.setColor(Color.GREEN);
                else g.setColor(Color.BLACK);
                g.drawLine(x - 5, y, x - 5, y + rowHeights[row - row_top] + 10);
                if (col == cursor.col() && (row == cursor.row() || row == cursor.row() + 1)) g.setColor(Color.RED);
                else if (selection != null && (selection.isIn(new CellPosition(col, row)) || selection.isIn(new CellPosition(col, row - 1))))
                    g.setColor(Color.GREEN);
                else g.setColor(Color.BLACK);
                g.drawLine(x - 5, y, x + colWidths[col - col_top] + 5, y);
                y += 5;
                y += rowHeights[row - row_top];
                g.setColor(Color.BLACK);
                if (enteringData && col == cursor.col() && row == cursor.row()) displayed = dataEntry.toString();
                else displayed = registry.at(new CellPosition(col, row)).displayed();
                g.drawString(displayed, x, y);
                y += 5;
                if (y >= screenSize.height) { oldRowNum = Math.max(row - row_top, oldRowNum); break; }
            }
            x += colWidths[col - col_top];
            x += 5;
            if (x >= screenSize.width) { oldColNum = Math.max(col - col_top, oldColNum); break; }
        }
        if (enteringData) g.drawString(dataEntry.toString(), 5, topBarHeight - 5);
        else g.drawString(registry.at(cursor).value(), 5, topBarHeight - 5);
    }
    private void registerAction(String code, Action action) {
        getInputMap().put(KeyStroke.getKeyStroke(code), code);
        getActionMap().put(code, action);
    }
    @Override public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == KeyEvent.CHAR_UNDEFINED || (Character.isISOControl(c) && c != '\b') || (e.getModifiersEx() & (KeyEvent.ALT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK)) != 0)
            return;
        if (!enteringData) dataEntry = new StringBuilder();
        enteringData = true;
        if (c == '\b') {
            if (dataEntry.length() > 0) dataEntry.setLength(dataEntry.length() - 1);
        } else dataEntry.append(c);
        repaint();
    }
    @Override public void keyPressed(KeyEvent e) {
        if (!enteringData) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP -> {
                enteringData = false;
                registry.at(cursor, new LabelCell(dataEntry.toString()));
            }
            case KeyEvent.VK_ENTER -> {
                enteringData = false;
                wasEnteringData = true;
                registry.at(cursor, new LabelCell(dataEntry.toString()));
                repaint();
            }
        }
    }
    @Override public void keyReleased(KeyEvent e) {
    }
    @Override public void mouseClicked(MouseEvent mouseEvent) {
    }
    @Override public void mousePressed(MouseEvent mouseEvent) {
    }
    @Override public void mouseReleased(MouseEvent mouseEvent) {
    }
    @Override public void mouseEntered(MouseEvent mouseEvent) {
    }
    @Override public void mouseExited(MouseEvent mouseEvent) {
    }
}