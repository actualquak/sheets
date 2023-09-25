package org.quak.sheets;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class SheetFrame extends JFrame implements QUpdatable {
    // SheetFrame, subclass of JFrame
    // Holds and displays a window, containing a single spreadsheet

    // List of all SheetFrames every created. Used for the "Close all" button
    public static final List<SheetFrame> frames = new ArrayList<>();
    // The registry of cells corresponding to this frame
    public final SheetRegistry registry;
    public SheetFrame() {
        // Call other constructor
        this(new SheetRegistry());
    }
    public SheetFrame(SheetRegistry registry) {
        // Create new window
        this.registry = registry;
        if(registry != null) {
            registry.fileName.addUpdatable(this);
            registry.saved.addUpdatable(this);
            frames.add(this);
            // Default size. Quite small, but eh... resizing is supported :)
            setSize(300, 300);
            setContentPane(new SheetRenderer(this, registry));
            qupdate();
            // Don't want to terminate application the moment any window is
            // closed, rather better to wait until all windows are closed
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
    @Override public void qupdate() {
        // Update window title to reflect filename/saved/unsaved status
        if(registry.fileName.get() == null) setTitle("Sheets - Untitled*");
        else setTitle("Sheets - " + registry.fileName.get().getName() +
                (registry.saved.get() ? "" : "*"));
    }
}
