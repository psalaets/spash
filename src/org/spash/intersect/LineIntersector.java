package org.spash.intersect;

import org.spash.ROVector2f;
import org.spash.Vector2f;
import org.spash.intersect.IntersectionState.Colinear;
import org.spash.intersect.IntersectionState.Intersecting;
import org.spash.intersect.IntersectionState.None;
import org.spash.intersect.IntersectionState.Parallel;

/**
 * Does some math related to lines.
 */
public class LineIntersector {
    /**
     * Figure out the intersection of two line segments.<br/>
     * http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/
     * 
     * @param x1 Line 1 start x
     * @param y1 Line 1 start y
     * @param x2 Line 1 end x
     * @param y2 Line 1 end y
     * @param x3 Line 2 start x
     * @param y3 Line 2 start y
     * @param x4 Line 2 end x
     * @param y4 Line 2 end y
     * @return Intersection the segments have, never null
     */
    protected IntersectionState determineIntersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float denom = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        float numeA = ((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3));
        float numeB = ((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3));

        if(denom == 0f) {
            if(numeA == 0f && numeB == 0f) {
                return new Colinear();
            }
            return new Parallel();
        }

        float uA = numeA / denom;
        float uB = numeB / denom;
        if(uA >= 0f && uA <= 1f && uB >= 0f && uB <= 1f) {
            Vector2f point = new Vector2f(x1 + (uA * (x2 - x1)), y1 + (uA * (y2 - y1)));
            return new Intersecting(point);
        }
        return new None();
    }

    /**
     * Given a point that is on the same line as a line segment, tells if that
     * point is between the start and end points of the line segment.
     * 
     * @param point
     * @param segmentStart
     * @param segmentEnd
     * @return true if point is within line segment, false otherwise
     */
    public boolean isPointWithinColinearSegment(ROVector2f point, ROVector2f segmentStart, ROVector2f segmentEnd) {
        if(segmentStart.getX() != segmentEnd.getX()) {
            if(segmentStart.getX() <= point.getX() && point.getX() <= segmentEnd.getX()) return true;
            if(segmentStart.getX() >= point.getX() && point.getX() >= segmentEnd.getX()) return true;
        } else {
            if(segmentStart.getY() <= point.getY() && point.getY() <= segmentEnd.getY()) return true;
            if(segmentStart.getY() >= point.getY() && point.getY() >= segmentEnd.getY()) return true;
        }
        return false;
    }
}
