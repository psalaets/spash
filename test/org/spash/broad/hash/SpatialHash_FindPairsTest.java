package org.spash.broad.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.bodyWith;
import static org.spash.broad.BroadTestHelper.set;

import java.util.Set;

import org.junit.Test;
import org.spash.Body;
import org.spash.Pair;
import org.spash.broad.hash.DefaultSpanFactory;
import org.spash.broad.hash.SpatialHash;
import org.spash.shape.Circle;


public class SpatialHash_FindPairsTest {
    @Test
    public void EmptyHash_ReturnsNoPairs() {
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);

        Set<Pair> pairs = hash.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenOneBody_ReturnsNoPairs() {
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(bodyWith(new Circle(1, 1, 5)));

        Set<Pair> pairs = hash.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenTwoBodiesInSameCell_ReturnsOnePair() {
        Body body1 = bodyWith(new Circle(1, 1, 5));
        Body body2 = bodyWith(new Circle(1, 1, 5));
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(body1);
        hash.add(body2);

        Set<Pair> pairs = hash.findPairs();

        assertEquals(set(new Pair(body1, body2)), pairs);
    }

    @Test
    public void GivenTwoBodiesInDifferentCells_ReturnsNoPairs() {
        Body bodyInLeftCell = bodyWith(new Circle(5, 5, 5));
        Body bodyInRightCell = bodyWith(new Circle(25, 5, 5));
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(bodyInLeftCell);
        hash.add(bodyInRightCell);

        Set<Pair> pairs = hash.findPairs();

        assertTrue(pairs.isEmpty());
    }

    @Test
    public void GivenThreeBodiesInSameCell_ReturnsThreePairs() {
        Body body1 = bodyWith(new Circle(1, 1, 5));
        Body body2 = bodyWith(new Circle(1, 1, 5));
        Body body3 = bodyWith(new Circle(1, 1, 5));
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(body1);
        hash.add(body2);
        hash.add(body3);

        Set<Pair> pairs = hash.findPairs();

        assertEquals(set(
                new Pair(body1, body2),
                new Pair(body1, body3),
                new Pair(body2, body3)), pairs);
    }

    @Test
    public void GivenThreeBodiesSpreadAcrossTwoCells_ReturnsTwoPairs() {
        Body bodyInLeftCell = bodyWith(new Circle(5, 5, 5));
        Body bodyInRightCell = bodyWith(new Circle(25, 5, 5));
        Body bodyInBothCells = bodyWith(new Circle(20, 5, 5));
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(bodyInLeftCell);
        hash.add(bodyInRightCell);
        hash.add(bodyInBothCells);

        Set<Pair> pairs = hash.findPairs();

        assertEquals(set(
                new Pair(bodyInLeftCell, bodyInBothCells),
                new Pair(bodyInRightCell, bodyInBothCells)), pairs);
    }
}
