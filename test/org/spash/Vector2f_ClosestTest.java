package org.spash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.spash.ROVector2f;
import org.spash.Vector2f;

public class Vector2f_ClosestTest {
    @Test
    public void NoArgsPassed_ReturnsNull() {
        ROVector2f point = new Vector2f(0, 0);

        assertNull(point.closest());
    }

    @Test
    public void OneNullPassed_ReturnsNull() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f nullVector = null;

        assertNull(point.closest(nullVector));
    }

    @Test
    public void TwoNullsPassed_ReturnsNull() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f aNull = null;
        ROVector2f anotherNull = null;

        assertNull(point.closest(aNull, anotherNull));
    }

    @Test
    public void OneVectorPassed_ReturnsTheVector() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f nonNull = new Vector2f();

        assertEquals(nonNull, point.closest(nonNull));
    }

    @Test
    public void TwoVectorsPassed_ReturnsTheCloserVector() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f closer = new Vector2f(1, 0);
        ROVector2f farther = new Vector2f(2, 0);

        assertEquals(closer, point.closest(closer, farther));
    }

    @Test
    public void OneVectorOneNullPassed_ReturnsTheNonNullVector() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f nonNull = new Vector2f(1, 0);
        ROVector2f theNull = null;

        assertEquals(nonNull, point.closest(nonNull, theNull));
    }

    @Test
    public void ThreeVectorsPassed_ReturnsTheClosestVector() {
        ROVector2f point = new Vector2f(0, 0);
        ROVector2f closest = new Vector2f(1, 0);
        ROVector2f middle = new Vector2f(2, 0);
        ROVector2f farthest = new Vector2f(3, 0);

        assertEquals(closest, point.closest(farthest, middle, closest));
    }
}
