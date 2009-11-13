package org.spash;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.spash.TestHelper.bodyWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.spash.Body;
import org.spash.BodyOverlapper;
import org.spash.BroadPhase;
import org.spash.Space;
import org.spash.shape.Circle;


public class SpaceTest {
    @Test(expected = IllegalArgumentException.class)
    public void BodyOverlapperCannotBeNull() {
        BroadPhase broad = mock(BroadPhase.class);
        new Space(broad, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void BroadPhaseCannotBeNull() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        new Space(null, overlapper);
    }

    @Test
    public void AddBodies_AddsEachBodyToBroadPhase() {
        BroadPhase broad = mock(BroadPhase.class);
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broad, overlapper);
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        List<Body> bodies = Arrays.asList(body1, body2);

        space.addBodies(bodies);

        verify(broad).add(body1);
        verify(broad).add(body2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddBodies_CollectionCannotContainNullBodies() {
        BroadPhase broad = mock(BroadPhase.class);
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broad, overlapper);
        List<Body> bodies = new ArrayList<Body>();
        bodies.add(null);

        space.addBodies(bodies);
    }

    @Test
    public void ClearBodies_ClearsBroadPhase() {
        BroadPhase broad = mock(BroadPhase.class);
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broad, overlapper);

        space.clearBodies();

        verify(broad).clear();
    }
}
