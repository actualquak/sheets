package org.quak.sheets;

import java.io.File;

public class SheetApplication {
    public static void main(String[] args) {
        SheetFrame frame;
        if(args.length > 0) frame = new SheetFrame(SheetRegistry.load(new File(args[0])));
        else frame = new SheetFrame();
        if(frame.registry != null) frame.setVisible(true);
    }
}
