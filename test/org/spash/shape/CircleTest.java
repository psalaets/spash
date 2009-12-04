package org.spash.shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.spash.shape.ShapeTestHelper.*;

import org.junit.Test;
import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Vector2f;
import org.spash.shape.Circle;


public class CircleTest {
    private static final float SQRT_2 = (float)Math.sqrt(2);

    @Test(expected = IllegalArgumentException.class)
    public void RadiusCannotBeNegative() {
        new Circle(1f, 2f, -2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void RadiusCannotBeZero() {
        new Circle(1f, 2f, 0f);
    }

    @Test
    public void ConstructorSetsPosition() {
        Circle c = new Circle(1f, 2f, 3f);

        assertEquals(new Vector2f(1f, 2f), c.getPosition());
    }

    @Test
    public void ConstructorSetsRadius() {
        Circle c = new Circle(1f, 2f, 3f);

        assertEquals(3f, c.getRadius());
    }

    @Test
    public void Vertices_OnHorizontal() {
        Circle c = new Circle(1, 2, 10);
        Vector2f horizontal = new Vector2f(1, 0);

        ROVector2f[] actual = c.getVertices(horizontal);

        assertVectorBagsEqual(list(
                new Vector2f(-9, 2),
                new Vector2f(11, 2)), list(actual));
    }

    @Test
    public void Vertices_OnVertical() {
        Circle c = new Circle(1, 2, 10);
        Vector2f vertical = new Vector2f(0, 1);

        ROVector2f[] actual = c.getVertices(vertical);

        assertVectorBagsEqual(list(
                new Vector2f(1, -8),
                new Vector2f(1, 12)), list(actual));
    }

    @Test
    public void Vertices_On45Degree() {
        Circle c = new Circle(1, 2, 10);
        Vector2f diagonal = new Vector2f(1, 1);
        diagonal.normalise();

        ROVector2f[] actual = c.getVertices(diagonal);

        float SQRT_2_OVER_2 = SQRT_2 / 2;
        Vector2f expected1 = new Vector2f(10 * -SQRT_2_OVER_2,
                10 * -SQRT_2_OVER_2);
        expected1.add(new Vector2f(1, 2));
        Vector2f expected2 = new Vector2f(10 * SQRT_2_OVER_2,
                10 * SQRT_2_OVER_2);
        expected2.add(new Vector2f(1, 2));

        assertVectorBagsEqual(list(expected1, expected2), list(actual));
    }

    @Test
    public void ProjectOntoUnit_OnHorizontal() {
        Circle c = new Circle(0, 0, 10);
        Vector2f horizonral = new Vector2f(1, 0);

        Interval result = c.projectOntoUnit(horizonral);
        
        assertEquals(new Interval(-10f, 10f), result);
    }

    @Test
    public void ProjectOntoUnit_OnVertical() {
        Circle c = new Circle(0, 0, 10);
        Vector2f vertical = new Vector2f(0, 1);

        Interval result = c.projectOntoUnit(vertical);
        
        assertEquals(new Interval(-10f, 10f), result);
    }

    @Test
    public void ProjectOntoUnit_On45Degree() {
        Circle c = new Circle(0, 0, 10);
        Vector2f diagonal = new Vector2f(1, 1);
        diagonal.normalise();

        Interval result = c.projectOntoUnit(diagonal);
        
        assertEquals(new Interval(-10f, 10f), result);
    }

    @Test
    public void MoveBy_MovesPosition() {
        Circle c = new Circle(1, 2, 5);

        assertEquals(new Vector2f(1f, 2f), c.getPosition());
        
        c.moveBy(new Vector2f(2, 4));

        assertEquals(new Vector2f(3f, 6f), c.getPosition());
    }

    @Test
    public void MoveBy_MovesVertices() {
        Circle c = new Circle(1, 2, 5);

        ROVector2f[] vertices = c.getVertices(new Vector2f(1, 0));
        assertVectorBagsEqual(list(
                new Vector2f(-4, 2),
                new Vector2f(6, 2)), list(vertices));

        c.moveBy(new Vector2f(2, 4));

        vertices = c.getVertices(new Vector2f(1, 0));
        assertVectorBagsEqual(list(
                new Vector2f(-2, 6),
                new Vector2f(8, 6)), list(vertices));
    }

    @Test
    public void SetPosition_ChangesPosition() {
        Circle c = new Circle(1, 2, 5);

        assertEquals(new Vector2f(1f, 2f), c.getPosition());
        
        c.setPosition(new Vector2f(3, 6));

        assertEquals(new Vector2f(3f, 6f), c.getPosition());
    }

    @Test
    public void SetPosition_ChangesVertices() {
        Circle c = new Circle(1, 2, 5);

        ROVector2f[] vertices = c.getVertices(new Vector2f(1, 0));
        assertVectorBagsEqual(list(
                new Vector2f(-4, 2),
                new Vector2f(6, 2)), list(vertices));

        c.setPosition(new Vector2f(3, 6));

        vertices = c.getVertices(new Vector2f(1, 0));
        assertVectorBagsEqual(list(
                new Vector2f(-2, 6),
                new Vector2f(8, 6)), list(vertices));
    }

    @Test
    public void GetMinX_ReturnsPositionXMinusRadius() {
        Circle c = new Circle(2, 3, 10);

        assertEquals(-8f, c.getMinX());
    }

    @Test
    public void GetMaxX_ReturnsPositionXPlusRadius() {
        Circle c = new Circle(2, 3, 10);

        assertEquals(12f, c.getMaxX());
    }

    @Test
    public void GetMinY_ReturnsPositionYMinusRadius() {
        Circle c = new Circle(2, 3, 10);

        assertEquals(-7f, c.getMinY());
    }

    @Test
    public void GetMaxY_ReturnsPositionYPlusRadius() {
        Circle c = new Circle(2, 3, 10);

        assertEquals(13f, c.getMaxY());
    }

    @Test
    public void NormalOnLineToClosest_ClosestIsAbove_ReturnsNormalAlongLineBetweenCenterAndClosestPoint() {
        Circle circle = new Circle(0, 0, 2);
        Vector2f[] points = { new Vector2f(0, -10), new Vector2f(20, 0) };

        Vector2f normal = circle.normalAlongLineToClosest(points);

        assertEquals(new Vector2f(0, -1), normal);
    }

    @Test
    public void NormalOnLineToClosest_ClosestIsRight_ReturnsNormalALongLineBetweenCenterAndClosestPoint() {
        Circle circle = new Circle(0, 0, 2);
        Vector2f[] points = { new Vector2f(0, -20), new Vector2f(10, 0) };

        Vector2f normal = circle.normalAlongLineToClosest(points);

        assertEquals(new Vector2f(1, 0), normal);
    }

    @Test
    public void Contains_PointIsInsideCircle_ReturnsTrue() {
        Circle c = new Circle(5, 5, 10);
        Vector2f point = new Vector2f(4, 4);

        assertTrue(c.contains(point));
    }

    @Test
    public void Contains_PointIsOnCircleEdge_ReturnsTrue() {
        Circle c = new Circle(5, 5, 10);
        Vector2f point = new Vector2f(15, 5);

        assertTrue(c.contains(point));
    }

    @Test
    public void Contains_PointIsOutsideOfCircle_ReturnsFalse() {
        Circle c = new Circle(5, 5, 10);
        Vector2f point = new Vector2f(40, 40);

        assertFalse(c.contains(point));
    }
}
