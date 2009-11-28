package org.spash.intersect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.spash.Vector2f;
import org.spash.intersect.IntersectionState.Colinear;
import org.spash.intersect.IntersectionState.Intersecting;
import org.spash.intersect.IntersectionState.None;
import org.spash.intersect.IntersectionState.Parallel;

public class IntersectionStateTest {
    @Test
    public void NoneHasNullIntersectionPoint() {
        assertNull(new None().getPoint());
    }
    
    @Test
    public void ParallelHasNullIntersectionPoint() {
        assertNull(new Parallel().getPoint());
    }
    
    @Test
    public void ColinearHasNullIntersectionPoint() {
        assertNull(new Colinear().getPoint());
    }
    
    @Test
    public void IntersectingHasGivenIntersectionPoint() {
        IntersectionState state = new Intersecting(new Vector2f(1, 2));
        
        assertEquals(new Vector2f(1, 2), state.getPoint());
    }
    
    @Test
    public void InstancesOfNoneAreEqual() {
        assertEquals(new None(), new None());
    }
    
    @Test
    public void InstancesOfParallelAreEqual() {
        assertEquals(new Parallel(), new Parallel());
    }
    
    @Test
    public void InstancesOfColinearAreEqual() {
        assertEquals(new Colinear(), new Colinear());
    }
    
    @Test
    public void InstancesOfIntersectingAreEqualWhenTheirPointsAreEqual() {
        assertEquals(new Intersecting(new Vector2f(1, 2)), new Intersecting(new Vector2f(1, 2)));
    }
    
    @Test
    public void InstancesOfIntersectingAreNotEqualWhenTheirPointAreNotEqual() {
        IntersectionState intersecting1 = new Intersecting(new Vector2f(1, 2));
        IntersectionState intersecting2 = new Intersecting(new Vector2f(3, 4));
        
        assertFalse(intersecting1.equals(intersecting2));
    }
    
    @Test
    public void IntersectionStatesAreNotEqualToOtherTypesOfStates() {
        assertFalse(new Colinear().equals(new Parallel()));
        assertFalse(new None().equals(new Colinear()));
        assertFalse(new Intersecting(new Vector2f()).equals(new None()));
        assertFalse(new Parallel().equals(new Intersecting(new Vector2f())));
    }

    @Test
    public void SameNonIntersectingStatesHaveEqualHashcodes() {
        assertEquals(new None().hashCode(), new None().hashCode());
        assertEquals(new Colinear().hashCode(), new Colinear().hashCode());
        assertEquals(new Parallel().hashCode(), new Parallel().hashCode());
    }
    
    @Test
    public void EqualIntersectingsHaveSameHashcodes() {
        assertEquals(new Intersecting(new Vector2f(1, 2)).hashCode(), new Intersecting(new Vector2f(1, 2)).hashCode());
    }
    
    @Test
    public void UnequalIntersectingsHaveDifferentHashcodes() {
        assertFalse(new Intersecting(new Vector2f(1, 2)).hashCode() == new Intersecting(new Vector2f(3, 4)).hashCode());
    }
    
    @Test
    public void DifferentIntersectionStatesHaveDifferentHashcodes() {
        assertFalse(new Colinear().hashCode() == new Parallel().hashCode());
        assertFalse(new None().hashCode() == new Colinear().hashCode());
        assertFalse(new Intersecting(new Vector2f()).hashCode() == new None().hashCode());
        assertFalse(new Parallel().hashCode() == new Intersecting(new Vector2f()).hashCode());
    }
    
    @Test
    public void HashcodeDoesNotChangeOverMultipleCalls() {
        Colinear colinear = new Colinear();
        assertEquals(colinear.hashCode(), colinear.hashCode());
        
        None none = new None();
        assertEquals(none.hashCode(), none.hashCode());
        
        Intersecting intersecting = new Intersecting(new Vector2f());
        assertEquals(intersecting.hashCode(), intersecting.hashCode());
        
        Parallel parallel = new Parallel();
        assertEquals(parallel.hashCode(), parallel.hashCode());
    }
}
