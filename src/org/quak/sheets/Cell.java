package org.quak.sheets;

import java.util.List;

public abstract class Cell {
    public abstract List<Cell> depends();
    public abstract String displayed();
    public <T> T as(Class<T> type) throws IllegalArgumentException {
        if(type.isInstance(this)) return type.cast(this);
        throw new IllegalArgumentException();
    }
}
