package org.spash.intersect;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Vector2f;
import org.spash.intersect.IntersectionState.Colinear;
import org.spash.intersect.IntersectionState.Intersecting;
import org.spash.intersect.IntersectionState.None;
import org.spash.intersect.IntersectionState.Parallel;

public class LineIntersector_DetermineIntersectionTest {
    LineIntersector intersector;
    
    @Before
    public void before() {
        intersector = new LineIntersector();
    }
    
    @After
    public void after() {
        intersector = null;
    }
    
    @Test
    public void SegmentsAreDisjoint_ReturnsNone() {
        IntersectionState intersection = intersector.determineIntersection(1, 1, 2, 2, 3, 1, 3, 2);

        assertEquals(new None(), intersection);
    }

    @Test
    public void SegmentsAreParallelButNotOnSameLine_ReturnsParallel() {
        IntersectionState intersection = intersector.determineIntersection(1, 1, 1, 2, 3, 1, 3, 2);

        assertEquals(new Parallel(), intersection);
    }

    @Test
    public void SegmentsAreColinear_ReturnsColinear() {
        IntersectionState intersection = intersector.determineIntersection(1, 1, 5, 5, 6, 6, 7, 7);

        assertEquals(new Colinear(), intersection);
    }

    @Test
    public void SegmentsAreIntersecting_ReturnsIntersectingAtIntersectionPoint() {
        IntersectionState intersection = intersector.determineIntersection(0, 1, 4, 1, 2, -2, 2, 2);

        assertEquals(new Intersecting(new Vector2f(2f, 1f)), intersection);
    }
}
