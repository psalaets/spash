package org.spash.intersect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Line;

public class RayLineIntersectorTest {
    /** Line at (5, 5) - (8, 8) */
    Line line;
    RayLineIntersector intersector;

    @Before
    public void before() {
        line = new Line(5, 5, 8, 8);
        intersector = new RayLineIntersector();
    }

    @After
    public void after() {
        line = null;
        intersector = null;
    }

    @Test
    public void RayGoesThroughLineFromLeft_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(0, 7), new Vector2f(10, 7));

        assertEquals(new Vector2f(7, 7), intersector.intersect(ray, line));
    }

    @Test
    public void RayGoesThroughLineFromRight_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 7), new Vector2f(0, 7));

        assertEquals(new Vector2f(7, 7), intersector.intersect(ray, line));
    }

    @Test
    public void RayStopsAtLineFromLeft_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(0, 6), new Vector2f(6, 6));

        assertEquals(new Vector2f(6, 6), intersector.intersect(ray, line));
    }

    @Test
    public void RayStopsAtLineFromRight_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(10, 6), new Vector2f(6, 6));

        assertEquals(new Vector2f(6, 6), intersector.intersect(ray, line));
    }

    @Test
    public void RayIsLinedUpButNotLongEnough_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(0, 6), new Vector2f(5, 6));

        assertNull(intersector.intersect(ray, line));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(0, 6), new Vector2f(10, 10));

        assertNull(intersector.intersect(ray, line));
    }

    @Test
    public void RayStartsOnLineStart_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(5, 5), new Vector2f(10, 5));

        assertEquals(new Vector2f(5, 5), intersector.intersect(ray, line));
    }

    @Test
    public void RayStartsOnLineEnd_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(8, 8), new Vector2f(10, 5));

        assertEquals(new Vector2f(8, 8), intersector.intersect(ray, line));
    }

    @Test
    public void RayStartsOnLine_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(6, 6), new Vector2f(10, 6));

        assertEquals(new Vector2f(6, 6), intersector.intersect(ray, line));
    }

    @Test
    public void RayIsColinearButDoesNotReach_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(4, 4));

        assertNull(intersector.intersect(ray, line));
    }

    @Test
    public void RayIsColinearAndOverlapsLine_ReturnsRayContactAtLinePointThatRayContains() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(6, 6));

        assertEquals(new Vector2f(5, 5), intersector.intersect(ray, line));
    }

    @Test
    public void RayIsColinearAndContainsLine_ReturnsRayContactAtLinePointClosestToRayOrigin() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(10, 10));

        assertEquals(new Vector2f(5, 5), intersector.intersect(ray, line));
    }

    @Test
    public void RayIsColinearAndLineContainsRayOrigin_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(6, 6), new Vector2f(10, 10));

        assertEquals(new Vector2f(6, 6), intersector.intersect(ray, line));
    }

    @Test
    public void RayIsColinearAndLineContainsEntireRay_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(6, 6), new Vector2f(7, 7));

        assertEquals(new Vector2f(6, 6), intersector.intersect(ray, line));
    }
}
