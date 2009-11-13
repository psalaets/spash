package org.spash;

/**
 * A physical body.
 */
public interface Body {
    /**
     * Gets this body's shape.
     * 
     * @return shape, never null
     */
    Shape getShape();

    /**
     * Gets this body's position.
     * 
     * @return position
     */
    ROVector2f getPosition();

    /**
     * Moves this body.
     * 
     * @param delta How much to move by
     */
    void adjustPosition(ROVector2f delta);

    /**
     * Moves this body.
     * 
     * @param position Location to move to
     */
    void setPosition(ROVector2f position);

    /**
     * Called when this body overlaps another.
     */
    void overlapping(Body other);

    /**
     * Gets the left-most x coordinate of this body.
     */
    float getMinX();

    /**
     * Gets the right-most x coordinate of this body.
     */
    float getMaxX();

    /**
     * Gets the highest y coordinate of this body.
     */
    float getMinY();

    /**
     * Gets the lowest y coordinate of this body.
     */
    float getMaxY();
}