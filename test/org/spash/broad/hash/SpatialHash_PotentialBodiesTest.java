package org.spash.broad.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.bodyWith;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Body;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.ray.RayBroadPhase;
import org.spash.shape.Circle;

public class SpatialHash_PotentialBodiesTest {
    RayBroadPhase hash;

    @Before
    public void before() {
        hash = new SpatialHash(new DefaultSpanFactory(), 20);
    }

    @After
    public void after() {
        hash = null;
    }

    @Test
    public void RayCoversOneEmptyCell_ReturnsNoBodies() {
        Ray ray = new Ray(new Vector2f(1, 1), new Vector2f(2, 2));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertTrue(potentials.isEmpty());
    }

    @Test
    public void RayCoversOneCellWithOneBody_ReturnsTheBody() {
        Body body = bodyWith(new Circle(12, 12, 5));
        hash.add(body);
        Ray ray = new Ray(new Vector2f(1, 1), new Vector2f(2, 2));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(1, potentials.size());
        assertTrue(potentials.contains(body));
    }

    @Test
    public void RayCoversOneCellWithTwoBodies_ReturnsBothBodies() {
        Body body = bodyWith(new Circle(12, 12, 5));
        Body body2 = bodyWith(new Circle(14, 14, 5));
        hash.add(body);
        hash.add(body2);
        Ray ray = new Ray(new Vector2f(1, 1), new Vector2f(4, 1));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(2, potentials.size());
        assertTrue(potentials.contains(body));
        assertTrue(potentials.contains(body2));
    }

    @Test
    public void HorzontalRayPassesThroughTwoCellsWithOneBodyEach_ReturnsBothBodies() {
        Body leftBody = bodyWith(new Circle(12, 12, 5));
        Body rightBody = bodyWith(new Circle(30, 12, 5));
        hash.add(leftBody);
        hash.add(rightBody);
        Ray ray = new Ray(new Vector2f(1, 1), new Vector2f(25, 1));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(2, potentials.size());
        assertTrue(potentials.contains(leftBody));
        assertTrue(potentials.contains(rightBody));
    }

    @Test
    public void HorzontalRayPassesAlongBorderOfTwoCellsWithOneBodyEach_ReturnsLowerCellsBody() {
        Body upperBody = bodyWith(new Circle(12, 12, 5));
        Body lowerBody = bodyWith(new Circle(12, 30, 5));
        hash.add(upperBody);
        hash.add(lowerBody);
        Ray ray = new Ray(new Vector2f(2, 20), new Vector2f(5, 20));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(1, potentials.size());
        assertTrue(potentials.contains(lowerBody));
    }

    @Test
    public void VerticalRayPassesThroughTwoCellsWithOneBodyEach_ReturnsBothBodies() {
        Body upperBody = bodyWith(new Circle(12, 12, 5));
        Body lowerBody = bodyWith(new Circle(12, 30, 5));
        hash.add(upperBody);
        hash.add(lowerBody);
        Ray ray = new Ray(new Vector2f(1, 1), new Vector2f(1, 25));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(2, potentials.size());
        assertTrue(potentials.contains(upperBody));
        assertTrue(potentials.contains(lowerBody));
    }

    @Test
    public void VerticalRayPassesAlongBorderOfTwoCellsWithOneBodyEach_ReturnsRightCellsBody() {
        Body leftBody = bodyWith(new Circle(12, 12, 5));
        Body rightBody = bodyWith(new Circle(30, 12, 5));
        hash.add(leftBody);
        hash.add(rightBody);
        Ray ray = new Ray(new Vector2f(20, 2), new Vector2f(20, 5));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(1, potentials.size());
        assertTrue(potentials.contains(rightBody));
    }

    @Test
    public void DiagonalRayPassesThroughThreeCellsWithOneBodyEach_ReturnsThreeBodies() {
        Body upperLeftBody = bodyWith(new Circle(12, 12, 5));
        Body lowerLeftBody = bodyWith(new Circle(12, 30, 5));
        Body lowerRightBody = bodyWith(new Circle(30, 30, 5));
        hash.add(upperLeftBody);
        hash.add(lowerLeftBody);
        hash.add(lowerRightBody);
        Ray ray = new Ray(new Vector2f(1, 10), new Vector2f(21, 21));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(3, potentials.size());
        assertTrue(potentials.contains(upperLeftBody));
        assertTrue(potentials.contains(lowerLeftBody));
        assertTrue(potentials.contains(lowerRightBody));
    }

    @Test
    public void RayCoversEmptyCellsButBodiesExistInHash_ReturnsNoBodies() {
        Body body = bodyWith(new Circle(35, 12, 5));
        hash.add(body);
        Ray ray = new Ray(new Vector2f(65, 1), new Vector2f(225, 1));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertTrue(potentials.isEmpty());
    }
    
    @Test
    public void RayCoversTwoCellsThatContainSameBody_ReturnsTheBodyOnce() {
        Body body = bodyWith(new Circle(20, 20, 10));
        hash.add(body);
        Ray ray = new Ray(new Vector2f(18, 18), new Vector2f(22, 18));

        Collection<Body> potentials = hash.potentialBodies(ray);

        assertEquals(1, potentials.size());
        assertTrue(potentials.contains(body));
    }
}
