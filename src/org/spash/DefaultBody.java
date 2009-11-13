package org.spash;

/**
 * Default Body impl.
 */
public class DefaultBody implements Body {
    private Shape shape;

    /**
     * Creates a body.
     * 
     * @param shape Shape of the body, cannot be null
     */
    public DefaultBody(Shape shape) {
        if(shape == null) throw new IllegalArgumentException("shape cannot be null");
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public ROVector2f getPosition() {
        return shape.getPosition();
    }

    public void adjustPosition(ROVector2f delta) {
        shape.moveBy(delta);
    }

    public void setPosition(ROVector2f position) {
        shape.setPosition(position);
    }

    public void overlapping(Body other) {
        //no op
    }

    public float getMinX() {
        return shape.getMinX();
    }

    public float getMaxX() {
        return shape.getMaxX();
    }

    public float getMinY() {
        return shape.getMinY();
    }

    public float getMaxY() {
        return shape.getMaxY();
    }
}
