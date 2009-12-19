package org.spash.broad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.spash.broad.BroadTestHelper.set;

import java.util.Set;

import org.junit.Test;
import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.ray.RayBroadPhase;


public class BruteForceTest {
    @Test
    public void WhenEmpty_ReturnsNoPairs() {
        BroadPhase broadPhase = new BruteForce();

        Set<Pair> pairs = broadPhase.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenOneBody_ReturnsNoPairs() {
        BroadPhase broadPhase = new BruteForce();
        broadPhase.add(mock(Body.class));

        Set<Pair> pairs = broadPhase.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenTwoBodies_ReturnsOnePair() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        BroadPhase broadPhase = new BruteForce();
        broadPhase.add(body1);
        broadPhase.add(body2);

        Set<Pair> pairs = broadPhase.findPairs();

        assertEquals(set(new Pair(body1, body2)), pairs);
    }

    @Test
    public void GivenThreeBodies_ReturnsThreePairs() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Body body3 = mock(Body.class);
        BroadPhase broadPhase = new BruteForce();
        broadPhase.add(body1);
        broadPhase.add(body2);
        broadPhase.add(body3);

        Set<Pair> pairs = broadPhase.findPairs();

        assertEquals(set(
                new Pair(body1, body2), 
                new Pair(body1, body3),
                new Pair(body2, body3)), pairs);
    }

    @Test
    public void ReturnsNoPairsAfterClear() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        BroadPhase broadPhase = new BruteForce();
        broadPhase.add(body1);
        broadPhase.add(body2);

        assertEquals(1, broadPhase.findPairs().size());

        broadPhase.clear();

        assertEquals(0, broadPhase.findPairs().size());
    }

    @Test
    public void HasNoPotentialBodiesWhenEmpty() {
        RayBroadPhase rayBroadPhase = new BruteForce();
        
        Ray ray = new Ray(new Vector2f(), new Vector2f());
        assertTrue(rayBroadPhase.potentialBodies(ray).isEmpty());
    }
    
    @Test
    public void AllBodiesArePotentialBodiesForRays() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        RayBroadPhase rayBroadPhase = new BruteForce();
        rayBroadPhase.add(body1);
        rayBroadPhase.add(body2);

        Ray ray = new Ray(new Vector2f(), new Vector2f());
        assertEquals(set(body1, body2), rayBroadPhase.potentialBodies(ray));
    }
    
    @Test
    public void HasNoPotentialBodiesAfterClearing() {
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        RayBroadPhase rayBroadPhase = new BruteForce();
        rayBroadPhase.add(body1);
        rayBroadPhase.add(body2);

        rayBroadPhase.clear();
        
        Ray ray = new Ray(new Vector2f(), new Vector2f());
        assertTrue(rayBroadPhase.potentialBodies(ray).isEmpty());
    }
}
