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
import org.spash.shape.Line;

public class RayBodyIntersector_LineTest {
    ConfigurableRayBodyIntersector intersector;
    Body lineBody;

    @Before
    public void before() throws Exception {
        intersector = new ConfigurableRayBodyIntersector();
        intersector.registerDefaults();

        lineBody = bodyWith(new Line(50, 30, 50, 50));
    }

    @After
    public void after() throws Exception {
        intersector = null;
        lineBody = null;
    }

    @Test
    public void RayHitsLineFromLeft_ReturnsIntersectionPoint() {
        Ray ray = new Ray(new Vector2f(10, 40), new Vector2f(70, 40));

        assertEquals(new Vector2f(50, 40), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayHitsLineFromRight_ReturnsIntersectionPoint() {
        Ray ray = new Ray(new Vector2f(70, 40), new Vector2f(10, 40));

        assertEquals(new Vector2f(50, 40), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsLinedUpButNotLongEnough_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 40), new Vector2f(40, 40));

        assertNull(intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 40), new Vector2f(80, 90));

        assertNull(intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayStartsOnLineStart_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 30), new Vector2f(70, 40));

        assertEquals(new Vector2f(50, 30), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayStartsOnLineEnd_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 50), new Vector2f(70, 30));

        assertEquals(new Vector2f(50, 50), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayStartsOnLine_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 40), new Vector2f(70, 40));

        assertEquals(new Vector2f(50, 40), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsColinearButDoesNotReach_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(50, 0), new Vector2f(50, 20));

        assertNull(intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsColinearAndOverlapsLine_ReturnsPointAtLinesEndPointThatRayContains() {
        Ray ray = new Ray(new Vector2f(50, 0), new Vector2f(50, 40));

        assertEquals(new Vector2f(50, 30), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsColinearAndContainsLine_ReturnsPointAtLinesEndPointClosestToRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 0), new Vector2f(50, 60));

        assertEquals(new Vector2f(50, 30), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsColinearAndLineContainsRayOrigin_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 40), new Vector2f(50, 60));

        assertEquals(new Vector2f(50, 40), intersector.intersect(ray, lineBody));
    }

    @Test
    public void RayIsColinearAndLineContainsRay_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(50, 40), new Vector2f(50, 45));

        assertEquals(new Vector2f(50, 40), intersector.intersect(ray, lineBody));
    }
}
