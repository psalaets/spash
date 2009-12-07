package org.spash.intersect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.spash.TestHelper.bodyWith;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Body;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Rect;

public class RayBodyIntersector_RectTest {
    ConfigurableRayBodyIntersector intersector;
    Body rectBody;

    @Before
    public void before() throws Exception {
        intersector = new ConfigurableRayBodyIntersector();
        intersector.registerDefaults();

        rectBody = bodyWith(new Rect(50, 50, 10, 10));
    }

    @After
    public void after() throws Exception {
        intersector = null;
        rectBody = null;
    }

    @Test
    public void RayGoesThroughRectFromLeft_ReturnsPointOnLeft() {
        Ray ray = new Ray(new Vector2f(10, 48), new Vector2f(70, 48));

        assertEquals(new Vector2f(45, 48), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesThroughRectFromRight_ReturnsPointOnRight() {
        Ray ray = new Ray(new Vector2f(70, 48), new Vector2f(10, 48));

        assertEquals(new Vector2f(55, 48), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesThroughRectFromAbove_ReturnsPointOnTop() {
        Ray ray = new Ray(new Vector2f(48, 10), new Vector2f(48, 70));

        assertEquals(new Vector2f(48, 45), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesThroughRectFromBelow_ReturnsPointOnBottom() {
        Ray ray = new Ray(new Vector2f(48, 70), new Vector2f(48, 10));

        assertEquals(new Vector2f(48, 55), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesAlongBottomEdgeFromLeft_ReturnsPointAtRectsLowerLeftCorner() {
        Ray ray = new Ray(new Vector2f(10, 55), new Vector2f(75, 55));

        assertEquals(new Vector2f(45, 55), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesAlongTopEdgeFromRight_ReturnsPointAtRectsUpperRightCorner() {
        Ray ray = new Ray(new Vector2f(75, 45), new Vector2f(10, 45));

        assertEquals(new Vector2f(55, 45), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesAlongLeftEdgeFromTop_ReturnsPointAtRectsUpperLeftCorner() {
        Ray ray = new Ray(new Vector2f(45, 10), new Vector2f(45, 70));

        assertEquals(new Vector2f(45, 45), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayGoesAlongRightEdgeFromBelow_ReturnsPointAtRectsLowerRightCorner() {
        Ray ray = new Ray(new Vector2f(55, 70), new Vector2f(55, 10));

        assertEquals(new Vector2f(55, 55), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayIsLinedUpButNotLongEnough_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(30, 50));

        assertNull(intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(50, 80));

        assertNull(intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayStartsInsideRect_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(48, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(48, 50), intersector.intersect(ray, rectBody));
    }

    @Test
    public void RayStartsAtRectEdge_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(45, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(45, 50), intersector.intersect(ray, rectBody));
    }
}
