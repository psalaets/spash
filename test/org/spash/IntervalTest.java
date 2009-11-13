package org.spash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.spash.Interval;

public class IntervalTest {
    @Test(expected = IllegalArgumentException.class)
    public void EndCannotBeLessThanStart() {
        new Interval(21f, 20f);
    }

    @Test
    public void ConstructorSetsStart() {
        Interval interval = new Interval(10f, 20f);

        assertEquals(10f, interval.getStart());
    }

    @Test
    public void ConstructorSetsEnd() {
        Interval interval = new Interval(10f, 20f);

        assertEquals(20f, interval.getEnd());
    }

    @Test
    public void EndCanBeEqualToStart() {
        Interval interval = new Interval(10f, 10f);

        assertEquals(10f, interval.getStart());
        assertEquals(10f, interval.getEnd());
    }

    @Test
    public void Overlaps_IntervalsAreDisjoint_ReturnsFalse() {
        Interval interval = new Interval(5f, 10f);
        Interval other = new Interval(15f, 20f);

        assertFalse(interval.overlaps(other));
    }

    @Test
    public void Overlaps_IntervalsAreEndToEnd_ReturnsFalse() {
        Interval interval = new Interval(5f, 10f);
        Interval other = new Interval(10f, 15f);

        assertFalse(interval.overlaps(other));
    }

    @Test
    public void Overlaps_IntervalsArePartiallyOverlapping_ReturnsTrue() {
        Interval interval = new Interval(5f, 10f);
        Interval other = new Interval(8f, 15f);

        assertTrue(interval.overlaps(other));
    }

    @Test
    public void Overlaps_IntervalsAreEqual_ReturnsTrue() {
        Interval interval = new Interval(5f, 10f);
        Interval other = new Interval(5f, 10f);

        assertTrue(interval.overlaps(other));
    }

    @Test
    public void Overlaps_GivenSelf_ReturnsTrue() {
        Interval interval = new Interval(1f, 2f);

        assertTrue(interval.overlaps(interval));
    }

    @Test
    public void Overlaps_IntervalContainsTheOtherInterval_ReturnsTrue() {
        Interval interval = new Interval(5f, 10f);
        Interval other = new Interval(8f, 9f);

        assertTrue(interval.overlaps(other));
    }

    @Test
    public void Distance_IntervalsAreDisjoint_ReturnsPositiveValue() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(5, 6);

        assertEquals(2f, interval.distance(other));
    }

    @Test
    public void Distance_IntervalsAreEndToEnd_ReturnsZero() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(3, 6);

        assertEquals(0f, interval.distance(other));
    }

    @Test
    public void Distance_IntervalsPartiallyOverlap_ReturnsNegativeValue() {
        Interval interval = new Interval(2, 4);
        Interval other = new Interval(3, 6);

        assertEquals(-1f, interval.distance(other));
    }

    @Test
    public void Distance_IntervalContainsTheOther_ReturnsNegativeValue() {
        Interval interval = new Interval(2, 6);
        Interval other = new Interval(3, 4);

        assertEquals(-2f, interval.distance(other));
    }
    
    @Test
    public void IntervalsWithSameStartAndEndAreEqual() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(2, 3);
        
        assertEquals(interval, other);
    }
    
    @Test
    public void IntervalsWithSameStartButDifferentEndAreNotEqual() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(2, 4);
        
        assertFalse(interval.equals(other));
    }
    
    @Test
    public void IntervalsWithSameEndButDifferentStartAreNotEqual() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(1, 3);
        
        assertFalse(interval.equals(other));
    }
    
    @Test
    public void IntervalsAreEqualToSelf() {
        Interval interval = new Interval(2, 3);
        
        assertEquals(interval, interval);
    }
    
    @Test
    public void IntervalsAreNotEqualToNull() {
        Interval interval = new Interval(2, 3);
        
        assertFalse(interval.equals(null));
    }
    
    @Test
    public void IntervalsAreNotEqualToNonInterval() {
        Interval interval = new Interval(2, 3);
        
        assertFalse(interval.equals("not interval"));
    }
    
    @Test
    public void EqualIntervalsHaveEqualHashcodes() {
        Interval interval = new Interval(2, 3);
        Interval other = new Interval(2, 3);
        
        assertEquals(interval.hashCode(), other.hashCode());
    }
    
    @Test
    public void HashcodeStaysTheSameOverMultipleCalls() {
        Interval interval = new Interval(2, 3);
        
        assertEquals(interval.hashCode(), interval.hashCode());
    }
}
