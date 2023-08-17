package org.quak.sheets;

import javax.swing.*;

public class FindDialog extends JDialog {
    private final JLabel findLabel = new JLabel("Find: ");
    private final JLabel replaceLabel = new JLabel("Replace: ");
    private final JTextField findTextField = new JTextField();
    private final JTextField replaceTextField = new JTextField();
    private final GroupLayout layout = new GroupLayout(this.getContentPane());
    private boolean replace;
    public FindDialog(SheetRenderer context) {
        super(context.frame, "Find/Replace");

        getContentPane().setLayout(layout);

        refreshUi();
    }
    public void replaceAndShow(boolean replace) {
        this.replace = replace;
        setSize(200, 200);
        refreshUi();
        setVisible(true);
    }
    private void refreshUi() {
        getContentPane().removeAll();
        getContentPane().setLayout(layout);
        if (replace) {
            setSize(Math.max(findLabel.getPreferredSize().width + findTextField.getPreferredSize().width,
                            replaceLabel.getPreferredSize().width + replaceTextField.getPreferredSize().width),
                    Math.max(findLabel.getPreferredSize().height + replaceLabel.getPreferredSize().height,
                            findTextField.getPreferredSize().height + replaceTextField.getPreferredSize().height));
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(findLabel)
                            .addComponent(findTextField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(replaceLabel)
                            .addComponent(replaceTextField)));
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(findLabel)
                            .addComponent(replaceLabel))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(findTextField)
                            .addComponent(replaceTextField)));
        } else {
            setSize(findLabel.getPreferredSize().width + findTextField.getPreferredSize().width,
                    Math.max(findLabel.getPreferredSize().height, findTextField.getPreferredSize().height));
            layout.setVerticalGroup(layout.createParallelGroup()
                    .addComponent(findLabel)
                    .addComponent(findTextField));
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(findLabel)
                    .addComponent(findTextField));
        }
    }
}
