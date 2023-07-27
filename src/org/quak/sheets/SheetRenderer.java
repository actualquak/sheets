package org.quak.sheets;

import org.quak.sheets.actions.*;

import javax.swing.*;
import java.awt.*;

public class SheetRenderer extends JPanel {
    SheetFrame frame;
    SheetRegistry registry;
    JMenuBar bar = new JMenuBar();
    public SheetRenderer(SheetFrame frame) { this(frame, new SheetRegistry()); }
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        this.frame = frame;
        this.registry = registry;
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(new NewAction()));
        fileMenu.add(new JMenuItem(new OpenAction()));
        fileMenu.add(new JMenuItem(new SaveAction()));
        fileMenu.add(new JMenuItem(new SaveAsAction()));
        fileMenu.add(new JMenuItem(new CloseAction()));
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
    @Override public void paintComponent(Graphics g) { }
}
