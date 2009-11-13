package org.spash;

/**
 * A basic shape.
 */
public interface Shape {
    /**
     * Projects this shape onto a unit vector.
     * 
     * @param unit Unit vector to project onto
     * @return Interval on unit formed by this shape's projection
     */
    Interval projectOntoUnit(ROVector2f unit);

    /**
     * Gets the position of this shape.
     * 
     * @return position
     */
    ROVector2f getPosition();

    /**
     * Sets the position of this shape.
     * 
     * @param pos New position
     */
    void setPosition(ROVector2f pos);

    /**
     * Moves this shape.
     * 
     * @param delta How much to move by
     */
    void moveBy(ROVector2f delta);

    /**
     * Gets the left-most x position of this shape.
     */
    float getMinX();

    /**
     * Gets the right-most x position of this shape.
     */
    float getMaxX();

    /**
     * Gets the top-most y position of this shape.
     */
    float getMinY();

    /**
     * Gets the bottom-most y position of this shape.
     */
    float getMaxY();
}
