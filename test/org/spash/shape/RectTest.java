package org.spash.shape;

import static org.junit.Assert.assertEquals;
import static org.spash.shape.ShapeTestHelper.assertVectorBagsEqual;
import static org.spash.shape.ShapeTestHelper.list;

import org.junit.Test;
import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Vector2f;
import org.spash.shape.Rect;


public class RectTest {
    private static final float SQRT_2 = (float)Math.sqrt(2);
    private static float SQRT_2_OVER_2 = SQRT_2 / 2;  

    @Test(expected = IllegalArgumentException.class)
    public void WidthCannotBeNegative() {
        new Rect(0, 0, -2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void HeightCannotBeNegative() {
        new Rect(0, 0, 2, -3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WidthCannotBeZero() {
        new Rect(0, 0, 0, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void HeightCannotBeZero() {
        new Rect(0, 0, 2, 0);
    }

    @Test
    public void ConstructorSetsWidth() {
        Rect r = new Rect(0, 0, 2, 3);
        
        assertEquals(2f, r.getWidth());
    }

    @Test
    public void ConstructorSetsHeight() {
        Rect r = new Rect(0, 0, 2, 3);

        assertEquals(3f, r.getHeight());
    }
    
    @Test
    public void ConstructorSetsPosition() {
        Rect r = new Rect(0, 1, 2, 4);

        assertEquals(new Vector2f(0f, 1f), r.getPosition());
    }

    @Test
    public void Vertices_ShouldBeCornersOfRect() {
        Rect rect = new Rect(1, 1, 2, 2);

        ROVector2f[] vertices = rect.getVertices();

        assertVectorBagsEqual(list(
                new Vector2f(0, 0),
                new Vector2f(0, 2),
                new Vector2f(2, 2),
                new Vector2f(2, 0)), list(vertices));
    }

    @Test
    public void ProjectOntoUnit_OnHorizontal() {
        Rect rect = new Rect(0, 0, 5, 10);
        Vector2f horizontal = new Vector2f(1, 0);

        Interval actual = rect.projectOntoUnit(horizontal);

        assertEquals(new Interval(-2.5f, 2.5f), actual);
    }

    @Test
    public void ProjectOntoUnit_OnVertical() {
        Rect rect = new Rect(0, 0, 5, 10);
        Vector2f vertical = new Vector2f(0, 1);

        Interval actual = rect.projectOntoUnit(vertical);

        assertEquals(new Interval(-5f, 5f), actual);
    }

    @Test
    public void ProjectOntoUnit_On45Degree() {
        Rect rect = new Rect(0, 0, 5, 5);
        Vector2f diagonal = new Vector2f(1, 1);
        diagonal.normalise();

        Interval actual = rect.projectOntoUnit(diagonal);

        assertEquals(new Interval(5 * -SQRT_2_OVER_2, 5 * SQRT_2_OVER_2), actual);
    }

    @Test
    public void MoveBy_MovesPosition() {
        Rect rect = new Rect(2, 3, 5, 10);

        assertEquals(new Vector2f(2f, 3f), rect.getPosition());
        
        rect.moveBy(new Vector2f(-2, 4));

        assertEquals(new Vector2f(0f, 7f), rect.getPosition());
    }

    @Test
    public void MoveBy_MovesVertices() {
        Rect rect = new Rect(1, 1, 2, 2);

        ROVector2f[] vertices = rect.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(0, 0),
                new Vector2f(0, 2),
                new Vector2f(2, 2),
                new Vector2f(2, 0)), list(vertices));

        rect.moveBy(new Vector2f(2, 3));

        vertices = rect.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(2, 3),
                new Vector2f(2, 5),
                new Vector2f(4, 5),
                new Vector2f(4, 3)), list(vertices));
    }

    @Test
    public void SetPosition_ChangesPosition() {
        Rect rect = new Rect(1, 2, 5, 5);

        assertEquals(new Vector2f(1f, 2f), rect.getPosition());
        
        rect.setPosition(new Vector2f(3, 4));

        assertEquals(new Vector2f(3f, 4f), rect.getPosition());
    }

    @Test
    public void SetPosition_ChangesVertices() {
        Rect rect = new Rect(1, 1, 2, 2);

        ROVector2f[] vertices = rect.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(0, 0),
                new Vector2f(0, 2),
                new Vector2f(2, 2),
                new Vector2f(2, 0)), list(vertices));

        rect.setPosition(new Vector2f(3, 4));

        vertices = rect.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(2, 3),
                new Vector2f(2, 5),
                new Vector2f(4, 3),
                new Vector2f(4, 5)), list(vertices));
    }

    @Test
    public void GetMinX_ReturnsPositionXMinusHalfWidth() {
        Rect rect = new Rect(1, 2, 4, 5);

        assertEquals(-1f, rect.getMinX());
    }

    @Test
    public void GetMaxX_ReturnsPositionXPlusHalfWidth() {
        Rect rect = new Rect(1, 2, 4, 5);

        assertEquals(3f, rect.getMaxX());
    }

    @Test
    public void GetMinY_ReturnsPositionYMinusHalfHeight() {
        Rect rect = new Rect(1, 2, 4, 5);

        assertEquals(-0.5f, rect.getMinY());
    }

    @Test
    public void GetMaxY_ReturnsPositionYPlusHalfHeight() {
        Rect rect = new Rect(1, 2, 4, 5);

        assertEquals(4.5f, rect.getMaxY());
    }
}
