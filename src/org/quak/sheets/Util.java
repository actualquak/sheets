package org.quak.sheets;

import javax.swing.*;

public class Util {
    private static FindDialog dialog = null;
    public static String base26ButNotReally(int i) {
        StringBuilder b = new StringBuilder();
        while(i > 0) {
            int modulo = (i - 1) % 26;
            b.append((char)('A' + modulo));
            i = (i - modulo) / 26;
        }
        return b.reverse().toString();
    }
    public static void showDialog(SheetRenderer context, boolean replace) {
        if(dialog == null) dialog = new FindDialog(context);
        dialog.replaceAndShow(replace);
    }
    public static ImageIcon loadImage(String imageName) {
        String imageLocation = "/images/" + imageName;
        java.net.URL imageURL = Util.class.getResource(imageLocation);
        if (imageURL == null) {
            System.err.println("Failed to find image: " + imageName);
            return null;
        }
        return new ImageIcon(imageURL);
    }
}
