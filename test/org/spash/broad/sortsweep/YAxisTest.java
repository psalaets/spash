package org.spash.broad.sortsweep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.bodyWith;

import org.junit.Test;
import org.spash.Body;
import org.spash.broad.sortsweep.Axis;
import org.spash.broad.sortsweep.YAxis;
import org.spash.shape.Rect;


public class YAxisTest {
    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesDoNotOverlap_ReturnsFalse() {
        Axis yAxis = new YAxis();
        Body lower = bodyWith(new Rect(0, 0, 5, 5));
        Body upper = bodyWith(new Rect(0, 6, 5, 5));

        assertFalse(yAxis.innerEdgesOverlap(lower, upper));
    }

    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesOverlap_ReturnsTrue() {
        Axis yAxis = new YAxis();
        Body lower = bodyWith(new Rect(0, 0, 5, 5));
        Body upper = bodyWith(new Rect(0, 4, 5, 5));

        assertTrue(yAxis.innerEdgesOverlap(lower, upper));
    }

    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesAreEqual_ReturnsFalse() {
        Axis yAxis = new YAxis();
        Body lower = bodyWith(new Rect(0, 0, 5, 5));
        Body upper = bodyWith(new Rect(0, 5, 5, 5));

        assertFalse(yAxis.innerEdgesOverlap(lower, upper));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsLessThanSecond_ReturnsNegativeOne() {
        Axis yAxis = new YAxis();
        Body body1 = bodyWith(new Rect(0, 0, 5, 5));
        Body body2 = bodyWith(new Rect(0, 1, 5, 5));

        assertEquals(-1, yAxis.compare(body1, body2));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsEqualToSecond_ReturnsZero() {
        Axis yAxis = new YAxis();
        Body body1 = bodyWith(new Rect(0, 0, 5, 5));
        Body body2 = bodyWith(new Rect(0, 0, 5, 5));

        assertEquals(0, yAxis.compare(body1, body2));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsGreaterThanSecond_ReturnsOne() {
        Axis yAxis = new YAxis();
        Body body1 = bodyWith(new Rect(0, 1, 5, 5));
        Body body2 = bodyWith(new Rect(0, 0, 5, 5));

        assertEquals(1, yAxis.compare(body1, body2));
    }
}
