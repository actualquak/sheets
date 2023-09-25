package org.quak.sheets;

import java.util.ArrayList;

public class QUpdater<T> {
    // Implements the Observable design pattern, together with QUpdatable
    // This in particular holds a reference to an object, and to replace
    // the object one must go through the set(T inner) method
    // This helps prevent programming errors

    // List of objects to notify when a change in value occurs
    private final ArrayList<QUpdatable> qupdatables = new ArrayList<>();
    // Inner reference to value
    private T inner;
    // Constructor
    public QUpdater(T inner) {
        this.inner = inner;
    }
    // Add new observer
    public void addUpdatable(QUpdatable q) {
        qupdatables.add(q);
    }
    // Remove observer
    // Unused warning suppressed here because
    // this is a good method to have even if unused
    @SuppressWarnings("unused") public void removeUpdatable(QUpdatable q) {
        qupdatables.remove(q);
    }
    // Set inner value
    public void set(T inner) {
        this.inner = inner;
        for (var qupdatable : qupdatables) {
            qupdatable.qupdate();
        }
    }
    // Get inner value
    public T get() {
        return inner;
    }
}
