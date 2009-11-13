package org.spash.broad.sortsweep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.bodyWith;

import org.junit.Test;
import org.spash.Body;
import org.spash.broad.sortsweep.Axis;
import org.spash.broad.sortsweep.XAxis;
import org.spash.shape.Rect;


public class XAxisTest {
    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesDoNotOverlap_ReturnsFalse() {
        Axis xAxis = new XAxis();
        Body left = bodyWith(new Rect(0, 0, 5, 5));
        Body right = bodyWith(new Rect(6, 0, 5, 5));

        assertFalse(xAxis.innerEdgesOverlap(left, right));
    }

    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesOverlap_ReturnsTrue() {
        Axis xAxis = new XAxis();
        Body left = bodyWith(new Rect(0, 0, 5, 5));
        Body right = bodyWith(new Rect(4, 0, 5, 5));

        assertTrue(xAxis.innerEdgesOverlap(left, right));
    }

    @Test
    public void InnerEdgesOverlap_BodiesInnerEdgesAreEqual_ReturnsFalse() {
        Axis xAxis = new XAxis();
        Body left = bodyWith(new Rect(0, 0, 5, 5));
        Body right = bodyWith(new Rect(5, 0, 5, 5));

        assertFalse(xAxis.innerEdgesOverlap(left, right));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsLessThanSecond_ReturnsNegativeOne() {
        Axis xAxis = new XAxis();
        Body body1 = bodyWith(new Rect(0, 0, 5, 5));
        Body body2 = bodyWith(new Rect(1, 0, 5, 5));

        assertEquals(-1, xAxis.compare(body1, body2));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsEqualToSecond_ReturnsZero() {
        Axis xAxis = new XAxis();
        Body body1 = bodyWith(new Rect(0, 0, 5, 5));
        Body body2 = bodyWith(new Rect(0, 0, 5, 5));

        assertEquals(0, xAxis.compare(body1, body2));
    }

    @Test
    public void Compare_LeftEdgeOfFirstIsGreaterThanSecond_ReturnsOne() {
        Axis xAxis = new XAxis();
        Body body1 = bodyWith(new Rect(1, 0, 5, 5));
        Body body2 = bodyWith(new Rect(0, 0, 5, 5));

        assertEquals(1, xAxis.compare(body1, body2));
    }
}
