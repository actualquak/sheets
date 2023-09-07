package org.quak.sheets;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// arrgghhh
@SuppressWarnings({"unchecked", "rawtypes"}) public class SheetFrame
        extends JFrame implements QUpdatable {
    public static final List<SheetFrame> frames = new ArrayList<>();
    public final SheetRegistry registry;
    public SheetFrame() {
        this(new SheetRegistry());
    }
    public SheetFrame(SheetRegistry registry) {
        this.registry = registry;
        if(registry != null) {
            registry.fileName.addUpdatable(this);
            registry.saved.addUpdatable(this);
            frames.add(this);
            setSize(300, 300);
            setContentPane(new SheetRenderer(this, registry));
            if (registry.fileName.get() == null) setTitle("Sheets - Untitled*");
            else setTitle("Sheets - " + registry.fileName.get().getName() +
                    (registry.saved.get() ? "" : "*"));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
    @Override public void qupdate(Object q) {
        if(registry.fileName.get() == null) setTitle("Sheets - Untitled*");
        else setTitle("Sheets - " + registry.fileName.get().getName() +
                (registry.saved.get() ? "" : "*"));
    }
}
