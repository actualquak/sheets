package org.quak.sheets.cells;

public abstract class Cell {
    public <T extends Cell> T as(Class<T> type) throws IllegalArgumentException {
        if(type.isInstance(this)) return type.cast(this);
        throw new IllegalArgumentException();
    }
    public abstract String displayed();
    public abstract String value();
}
