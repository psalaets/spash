package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.ray.Ray;
import org.spash.shape.Circle;

/**
 * Does intersect checks between rays and circles.
 */
public strictfp class RayCircleIntersector extends BaseRayShapeIntersector {
    public ROVector2f intersect(Ray ray, Shape shape) {
        Circle circle = (Circle)shape;
        if(circle.contains(ray.getStart())) {
            return ray.getStart();
        } else {
            ROVector2f potentialPoint = intersectionWithInfiniteRay(ray, circle);
            if(potentialPoint != null && pointIsWithinRay(potentialPoint, ray)) {
                return potentialPoint;
            }
        }
        return null;
    }

    private ROVector2f intersectionWithInfiniteRay(Ray ray, Circle circle) {
        InfiniteRayVsCircleSolver rayVsCircle = new InfiniteRayVsCircleSolver(ray, circle);
        return ray.getStart().closest(rayVsCircle.findIntersections());
    }
    
    public boolean canHandle(Class<? extends Shape> shapeType) {
        return shapeType == Circle.class;
    }
}
