package org.spash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.spash.Body;
import org.spash.DefaultBody;
import org.spash.Shape;
import org.spash.Vector2f;
import org.spash.shape.Circle;


public class DefaultBodyTest {
    @Test
    public void ConstructorSetsShape() {
        Shape shape = new Circle(0, 0, 5);
        Body body = new DefaultBody(shape);

        assertEquals(shape, body.getShape());
    }

    @Test
    public void ConstructorSetPositionToShapesPosition() {
        Shape shape = new Circle(1, 2, 5);
        Body body = new DefaultBody(shape);

        assertEquals(shape.getPosition(), body.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShapeCannotBeNull() {
        new DefaultBody(null);
    }

    @Test
    public void SetPosition_ChangesPosition() {
        Body body = new DefaultBody(new Circle(0, 0, 5));

        body.setPosition(new Vector2f(1, 2));

        assertEquals(new Vector2f(1, 2), body.getPosition());
    }

    @Test
    public void AdjustPosition_MovesPositionByGivenDelta() {
        Body body = new DefaultBody(new Circle(3, 4, 5));

        body.adjustPosition(new Vector2f(1, 2));

        assertEquals(new Vector2f(4, 6), body.getPosition());
    }

    @Test
    public void GetMinX_ReturnsLeftMostX() {
        Body body = new DefaultBody(new Circle(3, 0, 5));

        assertEquals(-2f, body.getMinX());
    }

    @Test
    public void GetMaxX_ReturnsRightMostX() {
        Body body = new DefaultBody(new Circle(3, 0, 5));

        assertEquals(8f, body.getMaxX());
    }

    @Test
    public void GetMinY_ReturnsHighestY() {
        Body body = new DefaultBody(new Circle(3, 0, 5));

        assertEquals(-5f, body.getMinY());
    }

    @Test
    public void GetMaxY_ReturnsLowestY() {
        Body body = new DefaultBody(new Circle(3, 0, 5));

        assertEquals(5f, body.getMaxY());
    }
}
