package org.quak.sheets;

import java.util.ArrayList;

public class QUpdater<T> {
    private final ArrayList<QUpdatable<T>> qupdatables = new ArrayList<>();
    private T inner;
    public QUpdater(T inner) {
        this.inner = inner;
    }
    public void addUpdatable(QUpdatable<T> q) {
        qupdatables.add(q);
    }
    public void removeUpdatable(QUpdatable<T> q) {
        qupdatables.remove(q);
    }
    public void set(T inner) {
        this.inner = inner;
        for (var qupdatable : qupdatables) {
            qupdatable.qupdate(inner);
        }
    }
    public T get() {
        return inner;
    }
}
