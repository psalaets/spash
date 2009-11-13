package org.spash;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.spash.Body;
import org.spash.BodyOverlapper;
import org.spash.BroadPhase;
import org.spash.OverlapEvent;
import org.spash.OverlapListener;
import org.spash.Pair;
import org.spash.PairFilter;
import org.spash.Space;
import org.spash.Translation;
import org.spash.Vector2f;

public class Space_ProcessOverlapsTest {
    @Test
    public void GetsPairsFromBroadPhase() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        Space space = new Space(broadPhase, overlapper);

        space.processOverlaps();

        verify(broadPhase).findPairs();
    }

    @Test
    public void NotifiesBodiesWhenTheyOverlapEachother() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Set<Pair> pairs = new HashSet<Pair>();
        pairs.add(new Pair(bodyA, bodyB));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(isA(Body.class), isA(Body.class))).thenReturn(new Translation(new Vector2f(), 0));
        Space space = new Space(broadPhase, overlapper);

        space.processOverlaps();

        verify(bodyA).overlapping(bodyB);
        verify(bodyB).overlapping(bodyA);
    }

    @Test
    public void DoesNotNotifyBodiesWhenTheyDoNotOverlap() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Set<Pair> pairs = new HashSet<Pair>();
        pairs.add(new Pair(bodyA, bodyB));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(isA(Body.class), isA(Body.class))).thenReturn(null);
        Space space = new Space(broadPhase, overlapper);

        space.processOverlaps();

        verify(bodyA, never()).overlapping(bodyB);
        verify(bodyB, never()).overlapping(bodyA);
    }

    @Test
    public void NotifiesOverlapListenerWhenOverlapOccurs() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(new Translation(new Vector2f(), 0));

        OverlapListener listener = mock(OverlapListener.class);
        Space space = new Space(broadPhase, overlapper);
        space.addOverlapListener(listener);

        space.processOverlaps();

        verify(listener).onOverlap(overlapEventWithBodies(body1, body2));
    }

    @Test
    public void DoesNotNotifyListenerOfOverlapWhenNoOverlap() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(null);

        OverlapListener listener = mock(OverlapListener.class);
        Space space = new Space(broadPhase, overlapper);
        space.addOverlapListener(listener);

        space.processOverlaps();

        verify(listener, never()).onOverlap(isA(OverlapEvent.class));
    }

    @Test
    public void DoesNotNotifyOverlapListenerAfterItHasBeenRemoved() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(new Translation(new Vector2f(), 0));

        OverlapListener listener = mock(OverlapListener.class);

        Space space = new Space(broadPhase, overlapper);
        space.addOverlapListener(listener);
        space.removeOverlapListener(listener);

        space.processOverlaps();

        verify(listener, never()).onOverlap(isA(OverlapEvent.class));
    }

    @Test
    public void NotifiesListenersInAddOrder() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Pair pair = new Pair(body1, body2);
        pairs.add(pair);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        OverlapListener firstListener = mock(OverlapListener.class);
        OverlapListener secondListener = mock(OverlapListener.class);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(new Translation(new Vector2f(), 0));

        Space space = new Space(broadPhase, overlapper);
        space.addOverlapListener(firstListener);
        space.addOverlapListener(secondListener);

        space.processOverlaps();

        InOrder callOrder = inOrder(firstListener, secondListener);
        callOrder.verify(firstListener).onOverlap(overlapEventWithBodies(body1, body2));
        callOrder.verify(secondListener).onOverlap(overlapEventWithBodies(body1, body2));
    }

    @Test
    public void ConsultsPairFilterForEachPairWhenPairFilterExists() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);

        Set<Pair> pairs = new HashSet<Pair>();
        Pair pair1 = new Pair(mock(Body.class), mock(Body.class));
        pairs.add(pair1);
        Pair pair2 = new Pair(mock(Body.class), mock(Body.class));
        pairs.add(pair2);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        PairFilter filter = mock(PairFilter.class);

        Space space = new Space(broadPhase, overlapper);
        space.addPairFilter(filter);

        space.processOverlaps();

        verify(filter).shouldSkipNarrowPhase(pair1);
        verify(filter).shouldSkipNarrowPhase(pair2);
    }

    @Test
    public void ConsultsPairFiltersInAddOrder() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);

        Set<Pair> pairs = new HashSet<Pair>();
        Pair pair = new Pair(mock(Body.class), mock(Body.class));
        pairs.add(pair);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        PairFilter firstFilter = mock(PairFilter.class);
        PairFilter secondFilter = mock(PairFilter.class);

        Space space = new Space(broadPhase, overlapper);
        space.addPairFilter(firstFilter);
        space.addPairFilter(secondFilter);

        space.processOverlaps();

        InOrder inOrder = inOrder(firstFilter, secondFilter);
        inOrder.verify(firstFilter).shouldSkipNarrowPhase(pair);
        inOrder.verify(secondFilter).shouldSkipNarrowPhase(pair);
    }

    @Test
    public void DoesNotConsultPairFilterAfterItHasBeenRemoved() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);

        Set<Pair> pairs = new HashSet<Pair>();
        Pair pair = new Pair(mock(Body.class), mock(Body.class));
        pairs.add(pair);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        PairFilter filter = mock(PairFilter.class);

        Space space = new Space(broadPhase, overlapper);
        space.addPairFilter(filter);
        space.removePairFilter(filter);

        space.processOverlaps();

        verify(filter, never()).shouldSkipNarrowPhase(pair);
    }

    @Test
    public void DoesNotCheckFilteredOutPairForOverlap() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);

        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Pair pair = new Pair(body1, body2);
        pairs.add(pair);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        PairFilter filter = mock(PairFilter.class);
        when(filter.shouldSkipNarrowPhase(pair)).thenReturn(true);

        Space space = new Space(broadPhase, overlapper);
        space.addPairFilter(filter);

        space.processOverlaps();

        verify(overlapper, never()).getMinTranslation(body1, body2);
    }

    private static OverlapEvent overlapEventWithBodies(Body bodyA, Body bodyB) {
        return argThat(new OverlapEventWithBodiesMatcher(bodyA, bodyB));
    }

    static class OverlapEventWithBodiesMatcher extends ArgumentMatcher<OverlapEvent> {
        private Body bodyA;
        private Body bodyB;

        public OverlapEventWithBodiesMatcher(Body bodyA, Body bodyB) {
            this.bodyA = bodyA;
            this.bodyB = bodyB;
        }

        @Override
        public boolean matches(Object item) {
            OverlapEvent event = (OverlapEvent) item;
            return event.getBodyA() == bodyA && event.getBodyB() == bodyB;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("OverlapEvent with bodyA: " + bodyA
                    + " and bodyB: " + bodyB);
        }
    }
}
