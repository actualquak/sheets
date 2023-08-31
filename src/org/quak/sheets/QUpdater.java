package org.quak.sheets;

import java.util.ArrayList;

public class QUpdater<T> {
    final ArrayList<QUpdatable<T>> qupdatables = new ArrayList<>();
    T inner;
    QUpdater(T inner) {
        this.inner = inner;
    }
    void addUpdatable(QUpdatable<T> q) {
        qupdatables.add(q);
    }
    void removeUpdatable(QUpdatable<T> q) {
        qupdatables.remove(q);
    }
    void set(T inner) {
        this.inner = inner;
        for (var qupdatable : qupdatables) {
            qupdatable.qupdate(inner);
        }
    }
    T get() {
        return inner;
    }
}
