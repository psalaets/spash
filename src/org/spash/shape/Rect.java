package org.spash.shape;

import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Vector2f;

/**
 * A rectangle.
 */
public strictfp class Rect extends AbstractShape implements Shape {
    private float width;
    private float height;

    /**
     * Creates a rect.
     * 
     * @param x X of rect center
     * @param y Y of rect center
     * @param width Width of the rect, must be > 0
     * @param height Height of the rect, must be > 0
     */
    public Rect(float x, float y, float width, float height) {
        super(new Vector2f(x, y));
        if(width <= 0f) throw new IllegalArgumentException("width must be > 0");
        if(height <= 0f) throw new IllegalArgumentException("height must be > 0");
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the vertices of this rect.
     * 
     * @return vertices, starting at top-left and going clockwise
     */
    public ROVector2f[] getVertices() {
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        Vector2f[] vertices = new Vector2f[4];
        vertices[0] = new Vector2f(-halfWidth, -halfHeight);
        vertices[1] = new Vector2f(halfWidth, -halfHeight);
        vertices[2] = new Vector2f(halfWidth, halfHeight);
        vertices[3] = new Vector2f(-halfWidth, halfHeight);

        vertices[0].add(getPosition());
        vertices[1].add(getPosition());
        vertices[2].add(getPosition());
        vertices[3].add(getPosition());
        return vertices;
    }

    /**
     * Projects this rect onto a unit vector.
     * 
     * @param unit Unit vector to project onto
     * @return Interval on unit formed by the rect's projection
     */
    public Interval projectOntoUnit(ROVector2f unit) {
        return projectVerticesOntoUnit(getVertices(), unit);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getMaxX() {
        return getPosition().getX() + (width / 2);
    }

    public float getMaxY() {
        return getPosition().getY() + (height / 2);
    }

    public float getMinX() {
        return getPosition().getX() - (width / 2);
    }

    public float getMinY() {
        return getPosition().getY() - (height / 2);
    }

    /**
     * Tells if this rect contains a point.
     *
     * @param point
     * @return true if this contains point, false otherwise
     */
    public boolean contains(ROVector2f point) {
        if(point.getX() > getMaxX()) return false;
        if(point.getX() < getMinX()) return false;
        if(point.getY() > getMaxY()) return false;
        if(point.getY() < getMinY()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "[Rect " + width + " x " + height + " at (" + getPosition().getX() + ", " + getPosition().getY() + ")]";
    }
}
