package org.spash.broad.hash;

import static org.junit.Assert.assertEquals;
import static org.spash.TestHelper.bodyWith;

import org.junit.Test;
import org.spash.Body;
import org.spash.broad.hash.DefaultSpanFactory;
import org.spash.broad.hash.SpatialHash;
import org.spash.shape.Circle;


public class SpatialHashTest {
    @Test(expected = IllegalArgumentException.class)
    public void SpanFactoryCannotBeNull() {
        new SpatialHash(null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CellSizeCannotBeNegative() {
        new SpatialHash(new DefaultSpanFactory(), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CellSizeCannotBeZero() {
        new SpatialHash(new DefaultSpanFactory(), 0);
    }

    @Test
    public void ConstructorSetsCellSize() {
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 5);

        assertEquals(5, hash.getCellSize());
    }

    @Test
    public void ReturnsNoPairsAfterClearing() {
        Body body1 = bodyWith(new Circle(1, 1, 5));
        Body body2 = bodyWith(new Circle(1, 1, 5));
        SpatialHash hash = new SpatialHash(new DefaultSpanFactory(), 20);
        hash.add(body1);
        hash.add(body2);

        assertEquals(1, hash.findPairs().size());

        hash.clear();

        assertEquals(0, hash.findPairs().size());
    }
}
