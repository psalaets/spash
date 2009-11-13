package org.spash;

/**
 * Listener for body overlaps.
 */
public interface OverlapListener {
    /**
     * Called when an overlap occurs.
     * 
     * @param event The overlap
     */
    void onOverlap(OverlapEvent event);
}
