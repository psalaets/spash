package org.spash.ray;

import org.spash.ROVector2f;
import org.spash.Vector2f;

/**
 * A line that goes for a while in one direction and then stops.
 */
public class Ray {
    private Vector2f start;
    private Vector2f end;
    private Vector2f direction;

    /**
     * Creates a ray based on start and end points.
     * 
     * @param start Start of ray
     * @param end End of ray
     */
    public Ray(ROVector2f start, ROVector2f end) {
        this.start = new Vector2f(start);
        this.end = new Vector2f(end);
        initDirection(start, end);
    }

    /**
     * Creates a ray based two points and a length.
     * 
     * @param start Start of the ray
     * @param anyPointOnRay Any point on the ray, not necessarily the end point
     * @param length Length of the ray
     */
    public Ray(ROVector2f start, ROVector2f anyPointOnRay, float length) {
        this.start = new Vector2f(start);
        initDirection(start, anyPointOnRay);
        // calculate end point based on direction and length
        end = new Vector2f(direction);
        end.scale(length);
        end.add(start);
    }

    /**
     * Sets up the direction vector based on 2 points. If the two points are the
     * same the ray goes straight up.
     * 
     * @param start Start point of the ray
     * @param point Any point on the ray
     */
    private void initDirection(ROVector2f start, ROVector2f point) {
        if (start.equals(point)) {
            direction = new Vector2f(0, -1);
        } else {
            direction = new Vector2f(point);
            direction.sub(start);
            direction.normalise();
        }
    }

    /**
     * Gets normalized direction vector of this ray.
     * 
     * @return normalized direction vector
     */
    public ROVector2f getDirection() {
        return direction;
    }

    /**
     * Gets start point of this ray.
     * 
     * @return start point
     */
    public ROVector2f getStart() {
        return start;
    }

    /**
     * Gets end point of this ray.
     * 
     * @return end point
     */
    public ROVector2f getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[Ray (" + start.getX() + ", " + start.getY() + ") -> ("
                + end.getX() + ", " + end.getY() + ")]";
    }

    /**
     * Tells if this ray is going left.
     * 
     * @return true if ray is going left, false otherwise
     */
    public boolean isGoingLeft() {
        return direction.getX() < 0.0f;
    }

    /**
     * Tells if this ray is going right.
     * 
     * @return true if ray is going right, false otherwise
     */
    public boolean isGoingRight() {
        return direction.getX() > 0.0f;
    }

    /**
     * Tells if this ray is going up.
     * 
     * @return true if ray is going up, false otherwise
     */
    public boolean isGoingUp() {
        return direction.getY() < 0.0f;
    }

    /**
     * Tells if this ray is going down.
     * 
     * @return true if ray is going down, false otherwise
     */
    public boolean isGoingDown() {
        return direction.getY() > 0.0f;
    }
}
