package org.quak.sheets;

// FunctionalInterface allows lambda,
// although this interface was never used that way
@FunctionalInterface public interface QUpdatable {
    // Implements the 'Observer' design pattern, together with QUpdater

    // Called when an update occurs
    void qupdate();
}
