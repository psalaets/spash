package org.spash.ray.intersect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.spash.TestHelper.bodyWith;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Body;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.ray.intersect.ConfigurableRayBodyIntersector;
import org.spash.shape.Circle;

public class RayBodyIntersector_CircleTest {
    ConfigurableRayBodyIntersector intersector;
    Body circleBody;

    @Before
    public void before() {
        intersector = new ConfigurableRayBodyIntersector();
        intersector.registerDefaults();

        circleBody = bodyWith(new Circle(50, 50, 5));
    }

    @After
    public void after() {
        intersector = null;
        circleBody = null;
    }

    @Test
    public void RayGoesThroughCircleFromLeft_ReturnsPointOnLeft() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(70, 50));

        assertEquals(new Vector2f(45, 50), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayGoesThroughCircleFromRight_ReturnsPointOnRight() {
        Ray ray = new Ray(new Vector2f(70, 50), new Vector2f(10, 50));

        assertEquals(new Vector2f(55, 50), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayGoesThroughCircleFromAbove_ReturnsPointOnTop() {
        Ray ray = new Ray(new Vector2f(50, 10), new Vector2f(50, 70));

        assertEquals(new Vector2f(50, 45), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayGoesThroughCircleFromBelow_ReturnsPointOnBottom() {
        Ray ray = new Ray(new Vector2f(50, 70), new Vector2f(50, 10));

        assertEquals(new Vector2f(50, 55), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayIsTangentAlongTop_ReturnsPointOnTop() {
        Ray ray = new Ray(new Vector2f(10, 45), new Vector2f(70, 45));

        assertEquals(new Vector2f(50, 45), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayIsTangentAlongBottom_ReturnsPointOnBottom() {
        Ray ray = new Ray(new Vector2f(10, 55), new Vector2f(70, 55));

        assertEquals(new Vector2f(50, 55), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayIsLinedUpButTooShort_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(20, 50));

        assertNull(intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayLongEnoughButNotLinedUp_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 50), new Vector2f(80, 90));

        assertNull(intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayIsLinedUpToGoAlongTopEdgeUpButTooShort_ReturnsNull() {
        Ray ray = new Ray(new Vector2f(10, 45), new Vector2f(20, 45));

        assertNull(intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayStartsInsideCircle_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(48, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(48, 50), intersector.intersect(ray, circleBody));
    }

    @Test
    public void RayStartsAtCircleEdge_ReturnsPointAtRayOrigin() {
        Ray ray = new Ray(new Vector2f(55, 50), new Vector2f(80, 50));

        assertEquals(new Vector2f(55, 50), intersector.intersect(ray, circleBody));
    }
}
