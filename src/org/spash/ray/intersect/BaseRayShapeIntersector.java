package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.ray.Ray;

public abstract class BaseRayShapeIntersector implements RayShapeIntersector {
    protected LineIntersector intersector;

    protected BaseRayShapeIntersector() {
        intersector = new LineIntersector();
    }

    /**
     * Finds intersection point of a ray and a line.
     * 
     * @param ray The ray
     * @param start Line start point
     * @param end Line end point
     * @return Intersection point or null if there is none. If the ray 
     * intersects the line at more than a single point, the intersection point
     * closest to the ray's origin is returned.
     */
    protected ROVector2f findIntersectionPoint(Ray ray, ROVector2f start, ROVector2f end) {
        IntersectionState state = intersector.determineIntersection(
                ray.getStart().getX(),
                ray.getStart().getY(),
                ray.getEnd().getX(),
                ray.getEnd().getY(),
                start.getX(),
                start.getY(),
                end.getX(),
                end.getY());

        if(state instanceof IntersectionState.Intersecting) {
            return state.getPoint();
        } else if(state instanceof IntersectionState.Colinear) {
            if(lineContainsRayStart(ray, start, end)) {
                return ray.getStart();
            }
            return ray.getStart().closest(endpointsWithinRay(ray, start, end));
        }
        return null;
    }

    //assumes ray and endpoints are colinear
    private ROVector2f[] endpointsWithinRay(Ray ray, ROVector2f start, ROVector2f end) {
        ROVector2f[] possibleIntersects = new ROVector2f[2]; 
        if(pointIsWithinRay(start, ray)) {
            possibleIntersects[0] = start;
        }
        if(pointIsWithinRay(end, ray)) {
            possibleIntersects[1] = end;
        }
        return possibleIntersects;
    }

    //assumes ray and point are colinear
    protected boolean pointIsWithinRay(ROVector2f point, Ray ray) {
        return intersector.isPointWithinColinearSegment(point, ray.getStart(), ray.getEnd());
    }

    //assumes line and point are colinear
    private boolean lineContainsRayStart(Ray ray, ROVector2f start, ROVector2f end) {
        return intersector.isPointWithinColinearSegment(ray.getStart(), start, end);
    }
}
