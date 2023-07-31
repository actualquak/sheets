package org.quak.sheets.actions;

import org.quak.sheets.Util;

import javax.swing.*;

public abstract class MyAction extends AbstractAction {
    public MyAction(String name, String iconLocation, String desc, KeyStroke accelKey, Integer mnemonic) {
        super();
        if (name != null) putValue(NAME, name);
        if (iconLocation != null) putValue(iconLocation, Util.loadImage(iconLocation));
        if (desc != null) putValue(SHORT_DESCRIPTION, desc);
        if (accelKey != null) putValue(ACCELERATOR_KEY, accelKey);
        if (mnemonic != null) putValue(MNEMONIC_KEY, mnemonic);
    }
}
