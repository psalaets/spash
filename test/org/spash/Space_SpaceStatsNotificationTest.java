package org.spash;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spash.TestHelper.bodyWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.spash.Body;
import org.spash.BodyOverlapper;
import org.spash.BroadPhase;
import org.spash.Pair;
import org.spash.PairFilter;
import org.spash.Space;
import org.spash.SpaceStats;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.shape.Circle;


public class Space_SpaceStatsNotificationTest {
    @Test
    public void NotifiesStatsOfOverlapWhenOverlapOccurs() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(new Translation(new Vector2f(), 0));

        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats).overlapFound();
    }

    @Test
    public void NotifiesStatsOfRunCompletionForEachCallToProcessOverlaps() {
        Set<Pair> pairs = new HashSet<Pair>();
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats).runCompleted();
    }

    @Test
    public void AddsToFindPairsNanosForEachCallToProcessOverlaps() {
        Set<Pair> pairs = new HashSet<Pair>();
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats).addFindPairsNanos(anyLong());
    }

    @Test
    public void AddsToProcessOverlapsNanosForEachCallToProcessOverlaps() {
        Set<Pair> pairs = new HashSet<Pair>();
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats).addProcessOverlapsNanos(anyLong());
    }

    @Test
    public void AddsToAddBodiesNanosForEachCallToAddBodies() {
        SpaceStats stats = mock(SpaceStats.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        space.setStats(stats);

        Body body = bodyWith(new Circle(5, 5, 5));
        List<Body> bodies = new ArrayList<Body>();
        bodies.add(body);

        space.addBodies(bodies);

        verify(stats).addAddBodiesNanos(anyLong());
    }

    @Test
    public void AddsToClearBodiesNanosForEachCallToClearBodies() {
        SpaceStats stats = mock(SpaceStats.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        space.setStats(stats);

        space.clearBodies();

        verify(stats).addClearBodiesNanos(anyLong());
    }

    @Test
    public void NotifiesStatsOfPairForEachPair() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        Body body3 = mock(Body.class);
        Body body4 = mock(Body.class);
        pairs.add(new Pair(body3, body4));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats, times(2)).pairFound();
    }

    @Test
    public void DoesNotNotifyStatsOfOverlapWhenNoOverlapOccurs() {
        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        pairs.add(new Pair(body1, body2));
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        when(overlapper.getMinTranslation(body1, body2)).thenReturn(null);

        Space space = new Space(broadPhase, overlapper);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats, never()).overlapFound();
    }

    @Test
    public void NotifiesStatsOfPairFilteringWhenPairIsFiltered() {
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
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats).pairFiltered();
    }

    @Test
    public void DoesNotNotifyStatsOfPairFilteringWhenPairIsNotFiltered() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);

        Set<Pair> pairs = new HashSet<Pair>();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Pair pair = new Pair(body1, body2);
        pairs.add(pair);
        BroadPhase broadPhase = mock(BroadPhase.class);
        when(broadPhase.findPairs()).thenReturn(pairs);

        PairFilter filter = mock(PairFilter.class);
        when(filter.shouldSkipNarrowPhase(pair)).thenReturn(false);

        Space space = new Space(broadPhase, overlapper);
        space.addPairFilter(filter);
        SpaceStats stats = mock(SpaceStats.class);
        space.setStats(stats);

        space.processOverlaps();

        verify(stats, never()).pairFiltered();
    }
}
