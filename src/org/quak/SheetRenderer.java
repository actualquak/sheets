package org.quak;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class SheetRenderer extends JComponent implements KeyListener {
    SheetFrame frame;
    SheetRegistry registry;
    public SheetRenderer(SheetFrame frame) { this(frame, new SheetRegistry()); }
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        this.frame = frame;
        this.registry = registry;
        addKeyListener(this);
    }
    @Override public void keyTyped(KeyEvent keyEvent) { throw new NotYetImplemented(); }
    @Override public void keyPressed(KeyEvent keyEvent) { throw new NotYetImplemented(); }
    @Override public void keyReleased(KeyEvent keyEvent) { throw new NotYetImplemented(); }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
