package org.quak.sheets;

@FunctionalInterface public interface QUpdatable<T> {
    void qupdate(T q);
}
