package org.spash.shape;

import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Vector2f;

/**
 * A line segment.
 */
public strictfp class Line extends AbstractShape implements Shape {
    private Vector2f p1;
    private Vector2f p2;

    /**
     * Creates a line.
     * 
     * @param x1 X of 1st endpoint
     * @param y1 Y of 1st endpoint
     * @param x2 X of 2nd endpoint
     * @param y2 Y of 2nd endpoint
     */
    public Line(float x1, float y1, float x2, float y2) {
        super(midPoint(x1, y1, x2, y2));
        p1 = new Vector2f(x1, y1);
        p2 = new Vector2f(x2, y2);
    }

    private static Vector2f midPoint(float x1, float y1, float x2, float y2) {
        return new Vector2f((x1 + x2) / 2f, (y1 + y2) / 2f);
    }

    /**
     * Gets a unit vector that is perpendicular to this line.
     * 
     * @return the normal
     */
    public ROVector2f getNormal() {
        Vector2f normal = new Vector2f(-(p2.getY() - p1.getY()), p2.getX() - p1.getX());
        normal.normalise();
        return normal;
    }

    /**
     * Gets first endpoint.
     */
    public ROVector2f getP1() {
        return p1;
    }
    
    /**
     * Gets second endpoint.
     */
    public ROVector2f getP2() {
        return p2;
    }

    /**
     * Projects this line onto a unit vector.
     * 
     * @param unit Unit vector to project onto
     * @return Interval on unit formed by this line's projection
     */
    public Interval projectOntoUnit(ROVector2f unit) {
        return projectVerticesOntoUnit(getVertices(), unit);
    }

    /**
     * Gets the points of this line.
     */
    public ROVector2f[] getVertices() {
        Vector2f[] v = new Vector2f[2];
        v[0] = new Vector2f(p1);
        v[1] = new Vector2f(p2);
        return v;
    }

    @Override
    public void setPosition(ROVector2f position) {
        Vector2f distanceToNewPosition = new Vector2f(position);
        distanceToNewPosition.sub(getPosition());
        moveBy(distanceToNewPosition);
    }

    @Override
    public void moveBy(ROVector2f delta) {
        //move mid point
        super.moveBy(delta);
        //keep endpoints synced up
        p1.add(delta);
        p2.add(delta);
    }

    public float getMaxX() {
        return Math.max(p1.getX(), p2.getX());
    }

    public float getMaxY() {
        return Math.max(p1.getY(), p2.getY());
    }

    public float getMinX() {
        return Math.min(p1.getX(), p2.getX());
    }

    public float getMinY() {
        return Math.min(p1.getY(), p2.getY());
    }

    public String toString() {
        return "[Line (" + p1.getX() + ", " + p1.getY() + ")-(" + p2.getX() + ", " + p2.getY() + ")]";
    }
}
