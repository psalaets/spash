package org.spash.intersect;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Vector2f;
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

    /**
     * Finds first intersection between a circle and a ray, assuming the ray
     * goes forever.  Translate towards origin so the problem is as if the ray
     * start is (0, 0).  This lets us project the translated circle's center on
     * to the ray's direction vector to find distance between circle center and
     * the ray.
     * 
     * Better explanation:
     * http://metanet.2.forumer.com/index.php?showtopic=17590
     * 
     * @return infinite ray's intersection point or null if infinite ray does
     * not intersect circle
     */
    private ROVector2f intersectionWithInfiniteRay(Ray ray, Circle circle) {
        Vector2f translatedCenter = translatedCircleCenter(ray, circle);

        Vector2f centerOnRay = new Vector2f(translatedCenter);
        centerOnRay.projectOntoUnit(ray.getDirection(), centerOnRay);

        float rayToCenterDistance = centerOnRay.distance(translatedCenter);
        if(rayIsTangentToCircle(circle, rayToCenterDistance)) {
            return singleIntersectPoint(ray, centerOnRay);
        } else if(rayIsSecantToCircle(circle, rayToCenterDistance)) {
            return closestOfTwoIntersectPoints(ray, circle, centerOnRay, rayToCenterDistance);
        }
        return null;
    }

    private ROVector2f closestOfTwoIntersectPoints(Ray ray, Circle circle, Vector2f centerOnRay, float rayToCenterDistance) {
        float b = pythagorean(rayToCenterDistance, circle.getRadius());

        Vector2f pointA = new Vector2f(ray.getDirection());
        pointA.scale(b);
        pointA.add(centerOnRay);
        pointA.add(ray.getStart()); // translate back away from origin

        Vector2f pointB = new Vector2f(ray.getDirection());
        pointB.scale(-b);
        pointB.add(centerOnRay);
        pointB.add(ray.getStart()); // translate back away from origin

        return ray.getStart().closest(pointA, pointB);
    }

    private ROVector2f singleIntersectPoint(Ray ray, Vector2f centerOnRay) {
        Vector2f point = new Vector2f(centerOnRay);
        point.add(ray.getStart()); // translate back away from origin
        return point;
    }

    private Vector2f translatedCircleCenter(Ray ray, Circle circle) {
        Vector2f circleCenter = new Vector2f(circle.getPosition());
        circleCenter.sub(ray.getStart()); //translate towards origin
        return circleCenter;
    }

    /**
     * Ray hits circle twice. 
     */
    private boolean rayIsSecantToCircle(Circle circle, float distance) {
        return distance < circle.getRadius();
    }
    /**
     * Ray hits circle once. 
     */
    private boolean rayIsTangentToCircle(Circle circle, float distance) {
        return distance == circle.getRadius();
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

    public boolean canHandle(Class<? extends Shape> shapeType) {
        return shapeType == Circle.class;
    }
}
