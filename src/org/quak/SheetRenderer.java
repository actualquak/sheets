package org.quak;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

class TestAction extends AbstractAction {
    TestAction() {
        super("Test Action", null);
    }
    @Override public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "HelloWorld");
    }
}

public class SheetRenderer extends JComponent {
    SheetFrame frame;
    SheetRegistry registry;
    boolean topaint = false;
    public SheetRenderer(SheetFrame frame) { this(frame, new SheetRegistry()); }
    public SheetRenderer(SheetFrame frame, SheetRegistry registry) {
        this.frame = frame;
        this.registry = registry;
        this.getInputMap().put(KeyStroke.getKeyStroke("F2"),
                "TestAction");
        this.getActionMap().put("TestAction",
                new TestAction());
    }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Hello world", 30, 30);
    }
}
