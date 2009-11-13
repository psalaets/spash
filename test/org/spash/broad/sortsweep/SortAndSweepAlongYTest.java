package org.spash.broad.sortsweep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.bodyWith;
import static org.spash.broad.BroadTestHelper.set;

import java.util.Set;

import org.junit.Test;
import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;
import org.spash.broad.sortsweep.SortAndSweep;
import org.spash.shape.Circle;


public class SortAndSweepAlongYTest {
    @Test
    public void WhenEmpty_ReturnsNoPairs() {
        BroadPhase broad = SortAndSweep.alongY();

        Set<Pair> pairs = broad.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenTwoBodiesThatOverlapAlongY_ReturnsOnePair() {
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        BroadPhase broad = SortAndSweep.alongY();
        broad.add(body1);
        broad.add(body2);

        Set<Pair> pairs = broad.findPairs();
        
        assertEquals(set(new Pair(body1, body2)), pairs);
    }

    @Test
    public void GivenThreeBodiesThatOverlapAlongX_ReturnsThreePairs() {
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        Body body3 = bodyWith(new Circle(0, 5, 5));
        BroadPhase broad = SortAndSweep.alongY();
        broad.add(body1);
        broad.add(body2);
        broad.add(body3);

        Set<Pair> pairs = broad.findPairs();

        assertEquals(set(
                new Pair(body1, body2),
                new Pair(body1, body3),
                new Pair(body2, body3)), pairs);
    }

    @Test
    public void GivenTwoBodiesThatDoNotOverlapAlongX_ReturnsNoPairs() {
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 11, 5));
        BroadPhase broad = SortAndSweep.alongY();
        broad.add(body1);
        broad.add(body2);

        Set<Pair> pairs = broad.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenTwoBodiesThatOverlapAlongXOneThatDoesnt_ReturnsOnePairForOverlappingBodies() {
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        Body body3 = bodyWith(new Circle(0, 15, 5));
        BroadPhase broad = SortAndSweep.alongY();
        broad.add(body1);
        broad.add(body2);
        broad.add(body3);

        Set<Pair> pairs = broad.findPairs();

        assertEquals(set(new Pair(body1, body2)), pairs);
    }

    @Test
    public void ReturnsNoPairsAfterClear() {
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        BroadPhase broad = SortAndSweep.alongY();
        broad.add(body1);
        broad.add(body2);

        assertEquals(1, broad.findPairs().size());

        broad.clear();

        assertEquals(0, broad.findPairs().size());
    }
}
