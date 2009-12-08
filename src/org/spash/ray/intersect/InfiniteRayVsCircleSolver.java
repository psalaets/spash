package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Circle;

/**
 * Solves the problem of intersecting an infinite ray with a circle.
 * 
 * Algorithm from http://metanet.2.forumer.com/index.php?showtopic=17590
 */
public class InfiniteRayVsCircleSolver {
    private Ray ray;
    private Circle circle;
    private Vector2f circleCenter;
    /** circle's center point projected onto ray */
    private Vector2f centerOnRay;
    private float rayToCenterDistance;
    
    public InfiniteRayVsCircleSolver(Ray ray, Circle circle) {
        this.ray = ray;
        this.circle = circle;
        circleCenter = new Vector2f(circle.getPosition());
    }
    
    /**
     * Finds intersections between a circle and a ray, assuming the ray goes
     * forever.  We first translate towards origin so the problem is as if the 
     * ray starts at (0, 0).  This lets us project the translated circle's 
     * center on to the ray's direction vector to find distance between circle
     * center and the ray.  At the end we translate the results back away from
     * the origin.
     * 
     * Better explanation:
     * http://metanet.2.forumer.com/index.php?showtopic=17590
     * 
     * @return infinite ray's intersection points or null if ray does not 
     * intersect circle
     */
    public ROVector2f[] findIntersections() {
        translateTowardsOrigin();
        Vector2f[] intersectionPoints = determineIntersectionPoints();
        return translateAwayFromOrigin(intersectionPoints);
    }

    private void translateTowardsOrigin() {
        circleCenter.sub(ray.getStart());
    }

    private Vector2f[] determineIntersectionPoints() {
        projectCircleCenterOnRay();
        if(rayIsSecant()) {
            return twoIntersectionPoints();
        } else if(rayIsTangent()) {
            return oneIntersectionPoint();
        } 
        return noIntersectionPoints();
    }
    
    private Vector2f[] translateAwayFromOrigin(Vector2f[] points) {
        for(Vector2f point : points) {
            point.add(ray.getStart());
        }
        return points;
    }
    
    /**
     * Ray hits circle twice.
     */
    private boolean rayIsSecant() {
        return rayToCenterDistance < circle.getRadius();
    }
    
    private Vector2f[] twoIntersectionPoints() {
        float b = pythagorean(rayToCenterDistance, circle.getRadius());

        Vector2f pointA = new Vector2f(ray.getDirection());
        pointA.scale(b);
        pointA.add(centerOnRay);

        Vector2f pointB = new Vector2f(ray.getDirection());
        pointB.scale(-b);
        pointB.add(centerOnRay);

        return new Vector2f[] {pointA, pointB};
    }

    /**
     * Ray hits circle once.
     */
    private boolean rayIsTangent() {
        return rayToCenterDistance == circle.getRadius();
    }

    private Vector2f[] oneIntersectionPoint() {
        return new Vector2f[] {centerOnRay};
    }

    private Vector2f[] noIntersectionPoints() {
        return new Vector2f[0];
    }

    private void projectCircleCenterOnRay() {
        centerOnRay = new Vector2f(circleCenter);
        centerOnRay.projectOntoUnit(ray.getDirection(), centerOnRay);
        
        rayToCenterDistance = centerOnRay.distance(circleCenter);
    }
    
    /**
     * a^2 + b^2 = c^2 with right triangle
     * 
     * @param a Length of side
     * @param c Length of hypotenuse
     * @return b Length of 3rd side
     */
    private float pythagorean(float a, float c) {
        return (float)Math.sqrt((c * c) - (a * a));
    }
}
