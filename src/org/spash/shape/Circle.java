package org.spash.shape;

import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Vector2f;

/**
 * A circle.
 */
public strictfp class Circle extends AbstractShape implements Shape {
    private float radius;

    /**
     * Creates a circle.
     * 
     * @param x X of circle center
     * @param y Y of circle center
     * @param radius Radius of the circle, must be > 0
     */
    public Circle(float x, float y, float radius) {
        super(new Vector2f(x, y));
        if(radius <= 0) throw new IllegalArgumentException("radius must be > 0");
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    /**
     * Projects this circle onto a unit vector.
     * 
     * @param unit Unit vector to project onto
     * @return Interval on unit formed by this circle's projection
     */
    public Interval projectOntoUnit(ROVector2f unit) {
        return projectVerticesOntoUnit(getVertices(unit), unit);
    }

    /**
     * Gets the intersection points (vertices) of this circle if a unit vector
     * is extended through the circle's center.
     * 
     * @param unit Unit vector
     * @return array of circle vertices
     */
    public ROVector2f[] getVertices(ROVector2f unit) {
        Vector2f[] vertices = new Vector2f[2];
        vertices[0] = new Vector2f(unit);
        vertices[0].scale(radius);
        vertices[0].add(getPosition());
        vertices[1] = new Vector2f(unit);
        vertices[1].scale(-radius);
        vertices[1].add(getPosition());
        return vertices;
    }

    public float getMaxX() {
        return getPosition().getX() + radius;
    }

    public float getMaxY() {
        return getPosition().getY() + radius;
    }

    public float getMinX() {
        return getPosition().getX() - radius;
    }

    public float getMinY() {
        return getPosition().getY() - radius;
    }

    public Vector2f normalAlongLineToClosest(ROVector2f[] points) {
        ROVector2f closest = getPosition().closest(points);
        Vector2f lineToClosest = lineFromCenterTo(closest);
        lineToClosest.normalise();
        return lineToClosest;
    }

    private Vector2f lineFromCenterTo(ROVector2f point) {
        Vector2f lineFromCenter = new Vector2f(point);
        lineFromCenter.sub(getPosition());
        return lineFromCenter;
    }

    /**
     * Tells if this circle contains a point.
     *
     * @param point
     * @return true if circle contains point, false otherwise
     */
    public boolean contains(ROVector2f point) {
        return getPosition().distance(point) <= radius;
    }

    @Override
    public String toString() {
        return "[Circle r:" + radius + " at (" + getPosition().getX() + ", " + getPosition().getY() + ")]";
    }
}
