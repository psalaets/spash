package org.spash.intersect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Rect;

public class RayRectIntersectorTest {
    Rect rect;
    RayRectIntersector intersector;

    @Before
    public void before() {
        rect = new Rect(50, 50, 10, 10);
        intersector = new RayRectIntersector();
    }

    @After
    public void after() {
        rect = null;
        intersector = null;
    }

    @Test
    public void RayGoesThroughRectFromLeft_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 48), new Vector2f(70, 48));

        assertEquals(new Vector2f(45, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesThroughRectFromRight_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(70, 48), new Vector2f(10, 48));

        assertEquals(new Vector2f(55, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesThroughRectFromAbove_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(48, 10), new Vector2f(48, 70));

        assertEquals(new Vector2f(48, 45), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesThroughRectFromBelow_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(48, 70), new Vector2f(48, 10));

        assertEquals(new Vector2f(48, 55), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsInsideRectFromLeft_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 48), new Vector2f(50, 48));

        assertEquals(new Vector2f(45, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsInsideRectFromRight_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(70, 48), new Vector2f(50, 48));

        assertEquals(new Vector2f(55, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsInsideRectFromAbove_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(48, 10), new Vector2f(48, 50));

        assertEquals(new Vector2f(48, 45), intersector.intersect(ray, rect));
    }

    @Test
    public void RaysStopsInsideRectFromBelow_ReturnsRayContactAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(48, 70), new Vector2f(48, 50));

        assertEquals(new Vector2f(48, 55), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsAtFirstEdgeFromLeft_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(10, 48), new Vector2f(45, 48));

        assertEquals(new Vector2f(45, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsAtFirstEdgeFromRight_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(70, 48), new Vector2f(55, 48));

        assertEquals(new Vector2f(55, 48), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsAtFirstEdgeFromAbove_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(48, 10), new Vector2f(48, 45));

        assertEquals(new Vector2f(48, 45), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStopsAtFirstEdgeFromBelow_ReturnsRayContactAtRayEnd() {
        Ray ray = new Ray(new Vector2f(48, 70), new Vector2f(48, 55));

        assertEquals(new Vector2f(48, 55), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesAlongBottomEdge_ReturnsRayContactAtRectCorner() {
        Ray ray = new Ray(new Vector2f(10, 55), new Vector2f(75, 55));

        assertEquals(new Vector2f(45, 55), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesAlongTopEdge_ReturnsRayContactAtRectCorner() {
        Ray ray = new Ray(new Vector2f(10, 45), new Vector2f(75, 45));

        assertEquals(new Vector2f(45, 45), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesAlongLeftEdge_ReturnsRayContactAtRectCorner() {
        Ray ray = new Ray(new Vector2f(45, 10), new Vector2f(45, 70));

        assertEquals(new Vector2f(45, 45), intersector.intersect(ray, rect));
    }

    @Test
    public void RayGoesAlongRightEdge_ReturnsRayContactAtRectCorner() {
        Ray ray = new Ray(new Vector2f(55, 70), new Vector2f(55, 10));

        assertEquals(new Vector2f(55, 55), intersector.intersect(ray, rect));
    }

    @Test
    public void RayIsLinedUpButNotLongEnough_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(30, 50));

        assertNull(intersector.intersect(ray, rect));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(50, 80));

        assertNull(intersector.intersect(ray, rect));
    }

    @Test
    public void RayStartsInsideRect_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(48, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(48, 50), intersector.intersect(ray, rect));
    }

    @Test
    public void RayStartsAtRectEdge_ReturnsRayContactAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(45, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(45, 50), intersector.intersect(ray, rect));
    }
}
