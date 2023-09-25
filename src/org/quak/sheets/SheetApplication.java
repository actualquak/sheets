package org.quak.sheets;

import java.io.File;

public class SheetApplication {
    // Entry point. Program flow starts here. Is called also to re-open files
    public static void main(String[] args) {
        // Parse arguments, show frame
        SheetFrame frame;
        if(args.length > 0) frame =
                new SheetFrame(SheetRegistry.load(new File(args[0])));
        else frame = new SheetFrame();
        if(frame.registry != null) frame.setVisible(true);
    }
}
