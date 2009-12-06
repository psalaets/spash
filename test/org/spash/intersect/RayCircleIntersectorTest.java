package org.spash.intersect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Circle;

public class RayCircleIntersectorTest {
    Circle circle;
    RayCircleIntersector intersector;

    @Before
    public void before() {
        circle = new Circle(50, 50, 5);
        intersector = new RayCircleIntersector();
    }

    @After
    public void after() {
        circle = null;
        intersector = null;
    }

    @Test
    public void RayGoesThroughCircleFromLeft_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(70, 50));

        assertEquals(new Vector2f(45, 50), intersector.intersect(ray, circle));
    }

    @Test
    public void RayGoesThroughCircleFromRight_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(70, 50), new Vector2f(10, 50));

        assertEquals(new Vector2f(55, 50), intersector.intersect(ray, circle));
    }

    @Test
    public void RayGoesThroughCircleFromAbove_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(50, 10), new Vector2f(50, 70));

        assertEquals(new Vector2f(50, 45), intersector.intersect(ray, circle));
    }

    @Test
    public void RayGoesThroughCircleFromBelow_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(50, 70), new Vector2f(50, 10));

        assertEquals(new Vector2f(50, 55), intersector.intersect(ray, circle));
    }

    @Test
    public void RayStopsInCircleFromLeft_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(50, 50));

        assertEquals(new Vector2f(45, 50), intersector.intersect(ray, circle));
    }

    @Test
    public void RayStopsInCircleFromRight_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(70, 50), new Vector2f(50, 50));

        assertEquals(new Vector2f(55, 50), intersector.intersect(ray, circle));
    }

    @Test
    public void RayStopsInCircleFromAbove_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(50, 10), new Vector2f(50, 50));

        assertEquals(new Vector2f(50, 45), intersector.intersect(ray, circle));
    }

    @Test
    public void RayStopsInCircleFromBelow_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(50, 70), new Vector2f(50, 50));

        assertEquals(new Vector2f(50, 55), intersector.intersect(ray, circle));
    }

    @Test
    public void RayIsTangentAlongTop_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 45), new Vector2f(70, 45));

        assertEquals(new Vector2f(50, 45), intersector.intersect(ray, circle));
    }

    @Test
    public void RayIsTangentAlongBottom_ReturnsPointAtIntersectPoint() {
        Ray ray = new Ray(new Vector2f(10, 55), new Vector2f(70, 55));

        assertEquals(new Vector2f(50, 55), intersector.intersect(ray, circle));
    }

    @Test
    public void RayIsLinedUpButTooShort_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(20, 50));

        assertNull(intersector.intersect(ray, circle));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(80, 90));

        assertNull(intersector.intersect(ray, circle));
    }

    @Test
    public void RayIsLinedToGoAlongTopEdgeUpButTooShort_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 45), new Vector2f(20, 45));

        assertNull(intersector.intersect(ray, circle));
    }

    @Test
    public void RayStartsInsideCircle_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(48, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(48, 50), intersector.intersect(ray, circle));
    }

    @Test
    public void RayStartsAtCircleEdge_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(55, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(55, 50), intersector.intersect(ray, circle));
    }
    
    @Test
    public void HandlesCircles() {
        assertTrue(intersector.canHandle(Circle.class));
    }
}
