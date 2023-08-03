package org.quak.sheets;

public class SheetApplication {
    public static void main(String[] args) {
        CellSelection selection = CellSelection.makeSelection(new CellPosition(2, 3), new CellPosition(3, 5));

        SheetFrame frame = new SheetFrame();
        frame.setVisible(true);
    }
}
