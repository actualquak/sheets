package org.quak.sheets;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SheetFrame extends JFrame {
    public static final List<SheetFrame> frames = new ArrayList<>();
    public SheetFrame() {
        frames.add(this);
        setSize(300, 300);
        setContentPane(new SheetRenderer(this, new SheetRegistry()));
        setTitle("Sheets");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
