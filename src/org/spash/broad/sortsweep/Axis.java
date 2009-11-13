package org.spash.broad.sortsweep;

import java.util.Comparator;

import org.spash.Body;


public interface Axis extends Comparator<Body> {
    /**
     * Tells if the inner edges of two shapes overlap along this axis.
     * 
     * @param lower Lower body along this axis
     * @param upper Higher body along this axis
     * @return true if the inner edges of the bodies overlap eachother, false
     * otherwise
     */
    boolean innerEdgesOverlap(Body lower, Body upper);
}
