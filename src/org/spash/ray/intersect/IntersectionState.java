package org.spash.ray.intersect;

import org.spash.ROVector2f;

public abstract class IntersectionState {
    private ROVector2f point;

    protected IntersectionState() {
        this(null);
    }
    
    protected IntersectionState(ROVector2f point) {
        this.point = point;
    }

    /**
     * Gets the intersection point, if any.
     * 
     * @return intersection point or null if there was no single intersection
     * point
     */
    public ROVector2f getPoint() {
        return point;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    /**
     * Not intersecting 
     */
    public static class None extends IntersectionState {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof None;
        }
    }
    /**
     * Parallel 
     */    
    public static class Parallel extends IntersectionState {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Parallel;
        }
    }
    /**
     * On the same line 
     */
    public static class Colinear extends IntersectionState {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Colinear;
        }
    }
    /**
     * Intersect at one point
     */
    public static class Intersecting extends IntersectionState {
        public Intersecting(ROVector2f point) {
            super(point);
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Intersecting) {
                Intersecting other = (Intersecting)obj;
                return getPoint().equals(other.getPoint());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int result = 13;
            result = (31 * result) + Intersecting.class.hashCode();
            result = (31 * result) + getPoint().hashCode();
            return result;
        }
    }
}
