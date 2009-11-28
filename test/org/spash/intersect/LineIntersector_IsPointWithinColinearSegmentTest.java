package org.spash.intersect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Vector2f;

public class LineIntersector_IsPointWithinColinearSegmentTest {
    LineIntersector intersector;
    
    @Before
    public void before() {
        intersector = new LineIntersector();
    }
    
    @After
    public void after() {
        intersector = null;
    }
    
    @Test
    public void PointIsColinearButOutsideOfSegment_ReturnsFalse() {
        assertFalse(intersector.isPointWithinColinearSegment(
                new Vector2f(1, 1),
                new Vector2f(2, 2),
                new Vector2f(4, 4)));
    }

    @Test
    public void PointIsInsideSegment_ReturnsTrue() {
        assertTrue(intersector.isPointWithinColinearSegment(
                new Vector2f(3, 3),
                new Vector2f(2, 2),
                new Vector2f(4, 4)));
    }

    @Test
    public void PointIsSegmentStart_ReturnsTrue() {
        assertTrue(intersector.isPointWithinColinearSegment(
                new Vector2f(2, 2),
                new Vector2f(2, 2),
                new Vector2f(4, 4)));
    }

    @Test
    public void PointIsSegmentEnd_ReturnsTrue() {
        assertTrue(intersector.isPointWithinColinearSegment(
                new Vector2f(4, 4),
                new Vector2f(2, 2),
                new Vector2f(4, 4)));
    }
}
