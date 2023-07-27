package org.quak.sheets;

public abstract class Cell {
    abstract public String displayed();
    public <T> T as(Class<T> type) throws IllegalArgumentException {
        if(type.isInstance(this)) return type.cast(this);
        throw new IllegalArgumentException();
    }
}
