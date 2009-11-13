package org.spash.broad;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.spash.broad.BroadTestHelper.set;

import java.util.Set;

import org.junit.Test;
import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;
import org.spash.broad.BruteForce;


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
}
