package org.quak.sheets;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// arrgghhh
@SuppressWarnings("ALL") public class SheetFrame extends JFrame implements QUpdatable {
    public static final List<SheetFrame> frames = new ArrayList<>();
    SheetRegistry reg = new SheetRegistry();
    public SheetFrame() {
        reg.fileName.addUpdatable(this);
        reg.saved.addUpdatable(this);
        frames.add(this);
        setSize(300, 300);
        setContentPane(new SheetRenderer(this, reg));
        setTitle("Sheets - " + reg.fileName.get() + (reg.saved.get() ? "" : "*"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    @Override public void qupdate(Object q) {
        setTitle("Sheets - " + reg.fileName.get() + (reg.saved.get() ? "" : "*"));
    }
}
