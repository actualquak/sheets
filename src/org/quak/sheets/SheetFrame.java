package org.quak.sheets;

import javax.swing.*;

public class SheetFrame extends JFrame {
    public SheetFrame() {
        setSize(300, 300);
        setContentPane(new SheetRenderer(this, new SheetRegistry()));
        setTitle("Sheets");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
