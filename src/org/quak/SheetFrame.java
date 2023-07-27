package org.quak;

import javax.swing.*;

public class SheetFrame extends JFrame {
    public SheetFrame() {
        setSize(300, 300);
        setContentPane(new SheetRenderer(this));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
