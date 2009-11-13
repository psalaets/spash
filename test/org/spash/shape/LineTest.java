package org.spash.shape;

import static org.junit.Assert.assertEquals;
import static org.spash.TestHelper.ALLOWABLE_DELTA;
import static org.spash.shape.ShapeTestHelper.*;

import org.junit.Test;
import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Vector2f;
import org.spash.shape.Line;


public class LineTest {
    @Test
    public void ConstructorSetsP1AsFirstPoint() {
        Line line = new Line(2, 3, 5, 6);

        assertEquals(new Vector2f(2f, 3f), line.getP1());
    }
    
    @Test
    public void ConstructorSetsP2AsSecondPoint() {
        Line line = new Line(2, 3, 5, 6);

        assertEquals(new Vector2f(5f, 6f), line.getP2());
    }

    @Test
    public void PositionIsMidpointOfLine() {
        Line line = new Line(0, 2, 3, 6);

        assertEquals(new Vector2f(1.5f, 4f), line.getPosition());
    }

    @Test
    public void NormalIsLength1() {
        Line line = new Line(1, 2, 5, 6);

        ROVector2f normal = line.getNormal();

        assertEquals(1f, normal.length(), ALLOWABLE_DELTA);
    }

    @Test
    public void NormalIsPerpendicularToLine() {
        Line line = new Line(0, 0, 4, 4);
        Vector2f lineAsVector = new Vector2f(4, 4);

        ROVector2f normal = line.getNormal();

        assertEquals(0f, normal.dot(lineAsVector), ALLOWABLE_DELTA);
    }

    @Test
    public void VerticesAreSameAsPointsOfLine() {
        Line line = new Line(0, 5, 10, 20);

        ROVector2f[] vertices = line.getVertices();

        assertVectorBagsEqual(list(
                new Vector2f(0, 5),
                new Vector2f(10, 20)), list(vertices));
    }

    @Test
    public void MoveBy_MovesPosition() {
        Line line = new Line(2, 2, 5, 10);

        assertEquals(new Vector2f(3.5f, 6f), line.getPosition());
        
        line.moveBy(new Vector2f(2, 3));

        assertEquals(new Vector2f(5.5f, 9f), line.getPosition());
    }

    @Test
    public void MoveBy_MovesPoints() {
        Line line = new Line(2, 2, 5, 10);

        assertEquals(new Vector2f(2f, 2f), line.getP1());
        assertEquals(new Vector2f(5f, 10f), line.getP2());
        
        line.moveBy(new Vector2f(2, 3));

        assertEquals(new Vector2f(4f, 5f), line.getP1());
        assertEquals(new Vector2f(7f, 13f), line.getP2());
    }

    @Test
    public void MoveBy_MovesVertices() {
        Line line = new Line(2, 2, 5, 10);

        ROVector2f[] vertices = line.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(2, 2),
                new Vector2f(5, 10)), list(vertices));

        line.moveBy(new Vector2f(2, 3));

        vertices = line.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(4, 5),
                new Vector2f(7, 13)), list(vertices));
    }

    @Test
    public void SetPosition_ChangesPosition() {
        Line line = new Line(1, 2, 5, 6);

        assertEquals(new Vector2f(3f, 4f), line.getPosition());

        line.setPosition(new Vector2f(-2, 5));

        assertEquals(new Vector2f(-2f, 5f), line.getPosition());
    }

    @Test
    public void SetPosition_ChangesPoints() {
        Line line = new Line(1, 2, 5, 6);

        assertEquals(new Vector2f(1f, 2f), line.getP1());
        assertEquals(new Vector2f(5f, 6f), line.getP2());

        line.setPosition(new Vector2f(-2, 5));

        assertEquals(new Vector2f(-4f, 3f), line.getP1());
        assertEquals(new Vector2f(0f, 7f), line.getP2());
    }

    @Test
    public void SetPosition_ChangesVertices() {
        Line line = new Line(1, 2, 5, 6);

        ROVector2f[] vertices = line.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(1, 2),
                new Vector2f(5, 6)), list(vertices));

        line.setPosition(new Vector2f(-2, 5));

        vertices = line.getVertices();
        assertVectorBagsEqual(list(
                new Vector2f(-4, 3),
                new Vector2f(0, 7)), list(vertices));
    }

    @Test
    public void ProjectOntoUnit_OnVertical() {
        Line line = new Line(1, 2, 3, 4);
        Vector2f vertical = new Vector2f(0, 1);

        Interval result = line.projectOntoUnit(vertical);

        assertEquals(new Interval(2f, 4f), result);
    }

    @Test
    public void ProjectOntoUnit_OnHorizontal() {
        Line line = new Line(1, 2, 3, 4);
        Vector2f horizontal = new Vector2f(1, 0);

        Interval result = line.projectOntoUnit(horizontal);

        assertEquals(new Interval(1f, 3f), result);
    }

    @Test
    public void ProjectOntoUnit_OnNormal() {
        Line line = new Line(0, 0, 2, 2);
        Vector2f normal = new Vector2f(-2, 2);
        normal.normalise();

        Interval result = line.projectOntoUnit(normal);

        assertEquals(new Interval(0f, 0f), result);
    }

    @Test
    public void GetMinX_ReturnsLowestXPosition() {
        Line line = new Line(1, 2, 3, 4);

        assertEquals(1f, line.getMinX());
    }

    @Test
    public void GetMaxX_ReturnsHighestXPosition() {
        Line line = new Line(1, 2, 3, 4);

        assertEquals(3f, line.getMaxX());
    }

    @Test
    public void GetMinY_ReturnsLowestYPosition() {
        Line line = new Line(1, 2, 3, 4);

        assertEquals(2f, line.getMinY());
    }

    @Test
    public void GetMaxY_ReturnsHighestYPosition() {
        Line line = new Line(1, 2, 3, 4);

        assertEquals(4f, line.getMaxY());
    }
}
