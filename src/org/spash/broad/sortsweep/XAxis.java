package org.spash.broad.sortsweep;

import org.spash.Body;

public class XAxis implements Axis {
    public boolean innerEdgesOverlap(Body leftMost, Body rightMost) {
        return leftMost.getMaxX() > rightMost.getMinX();
    }

    /**
     * Compares bodies based on the x coordinate of their left most point.
     */
    public int compare(Body body1, Body body2) {
        if(body1.getMinX() < body2.getMinX()) {
            return -1;
        } else if(body1.getMinX() > body2.getMinX()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "XAxis";
    }
}
