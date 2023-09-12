package org.quak.sheets;

import java.util.ArrayList;

public class QUpdater<T> {
    private final ArrayList<QUpdatable> qupdatables = new ArrayList<>();
    private T inner;
    public QUpdater(T inner) {
        this.inner = inner;
    }
    public void addUpdatable(QUpdatable q) {
        qupdatables.add(q);
    }
    @SuppressWarnings("unused") public void removeUpdatable(QUpdatable q) {
        qupdatables.remove(q);
    }
    public void set(T inner) {
        this.inner = inner;
        for (var qupdatable : qupdatables) {
            qupdatable.qupdate();
        }
    }
    public T get() {
        return inner;
    }
}
