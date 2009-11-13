package org.spash.broad.sortsweep;

import org.spash.Body;

public class YAxis implements Axis {
    public boolean innerEdgesOverlap(Body lower, Body upper) {
        return lower.getMaxY() > upper.getMinY();
    }

    /**
     * Compares bodies based on the y coordinate of their highest point.
     */
    public int compare(Body body1, Body body2) {
        if(body1.getMinY() < body2.getMinY()) {
            return -1;
        } else if(body1.getMinY() > body2.getMinY()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "YAxis";
    }
}
