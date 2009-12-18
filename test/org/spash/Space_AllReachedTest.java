package org.spash;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.ray.Ray;
import org.spash.ray.RayBodyIntersector;
import org.spash.ray.RayBroadPhase;
import org.spash.ray.RayContact;

public class Space_AllReachedTest {
    private Space space;

    @Before
    public void before() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        space = new Space(broadPhase, overlapper);
    }

    @After
    public void after() {
        space = null;
    }

    @Test(expected = IllegalStateException.class)
    public void WhenNotEquippedForRays_ThrowsIllegalStateException() {
        Ray ray = new Ray(new Vector2f(), new Vector2f());

        space.allReached(ray);
    }

    @Test
    public void ReturnsOneContactPerIntersection() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Set<Body> potentials = new HashSet<Body>(Arrays.asList(body1, body2));

        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 1));
        when(rayBroadPhase.potentialBodies(ray)).thenReturn(potentials);

        RayBodyIntersector intersector = mock(RayBodyIntersector.class);
        when(intersector.intersect(ray, body1)).thenReturn(new Vector2f(0, 0));
        when(intersector.intersect(ray, body2)).thenReturn(new Vector2f(1, 1));

        space.equipForRays(rayBroadPhase, intersector);

        List<RayContact> contacts = space.allReached(ray);

        assertEquals(Arrays.asList(
            new RayContact(ray, body1, new Vector2f(0, 0)),
            new RayContact(ray, body2, new Vector2f(1, 1))
        ), contacts);
    }

    @Test
    public void ReturnsContactsSortedByAscendingDistanceFromRayStart() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Body body3 = mock(Body.class);
        Set<Body> potentials = new HashSet<Body>(Arrays.asList(body1, body2, body3));

        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 1));
        when(rayBroadPhase.potentialBodies(ray)).thenReturn(potentials);

        RayBodyIntersector intersector = mock(RayBodyIntersector.class);
        when(intersector.intersect(ray, body1)).thenReturn(new Vector2f(1, 1));
        when(intersector.intersect(ray, body2)).thenReturn(new Vector2f(0, 0));
        when(intersector.intersect(ray, body3)).thenReturn(new Vector2f(2, 2));

        space.equipForRays(rayBroadPhase, intersector);

        List<RayContact> contacts = space.allReached(ray);

        assertEquals(Arrays.asList(
            new RayContact(ray, body2, new Vector2f(0, 0)),
            new RayContact(ray, body1, new Vector2f(1, 1)),
            new RayContact(ray, body3, new Vector2f(2, 2))
        ), contacts);
    }
}
