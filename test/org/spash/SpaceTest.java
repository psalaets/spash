package org.spash;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.spash.TestHelper.bodyWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.spash.ray.RayBodyIntersector;
import org.spash.ray.RayBroadPhase;
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

    @Test(expected=IllegalArgumentException.class)
    public void CannotEquipWithNullRayBroadPhase() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        Space space = new Space(broadPhase, overlapper);

        space.equipForRays(null, mock(RayBodyIntersector.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void CannotEquipWithNullRayIntersector() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        Space space = new Space(broadPhase, overlapper);

        space.equipForRays(mock(RayBroadPhase.class), null);
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
    
    @Test
    public void AddBodies_WhenRayBroadPhaseIsSet_AddsEachBodyToRayBroadPhase() {
        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        Space space = new Space(mock(BroadPhase.class), mock(BodyOverlapper.class));
        space.equipForRays(rayBroadPhase, mock(RayBodyIntersector.class));
        
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        List<Body> bodies = Arrays.asList(body1, body2);

        space.addBodies(bodies);

        verify(rayBroadPhase).add(body1);
        verify(rayBroadPhase).add(body2);
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
    
    @Test
    public void ClearBodies_WhenRayBroadPhaseIsSet_ClearsRayBroadPhase() {
        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        Space space = new Space(mock(BroadPhase.class), mock(BodyOverlapper.class));
        space.equipForRays(rayBroadPhase, mock(RayBodyIntersector.class));

        space.clearBodies();

        verify(rayBroadPhase).clear();
    }
    
    @Test
    public void ClearBodies_WhenBroadPhaseAndRayBroadPhaseAreSameObject_ClearsOnce() {
        BothBroadPhases broadPhases = mock(BothBroadPhases.class);
        Space space = new Space(broadPhases, mock(BodyOverlapper.class));
        space.equipForRays(broadPhases, mock(RayBodyIntersector.class));

        space.clearBodies();

        verify(broadPhases, times(1)).clear();
    }

    @Test
    public void AddBodies_WhenBroadPhaseAndRayBroadPhaseAreSameObject_AddsEachBodyOnce() {
        BothBroadPhases broadPhases = mock(BothBroadPhases.class);
        Space space = new Space(broadPhases, mock(BodyOverlapper.class));
        space.equipForRays(broadPhases, mock(RayBodyIntersector.class));
        
        Body body1 = bodyWith(new Circle(0, 0, 5));
        Body body2 = bodyWith(new Circle(0, 3, 5));
        List<Body> bodies = Arrays.asList(body1, body2);

        space.addBodies(bodies);

        verify(broadPhases, times(1)).add(body1);
        verify(broadPhases, times(1)).add(body2);
    }
    
    /** Ray broadphase and overlap broadphase in one */
    interface BothBroadPhases extends BroadPhase, RayBroadPhase {}
}
