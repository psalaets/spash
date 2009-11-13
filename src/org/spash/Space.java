package org.spash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Brings broad phase and narrow phase together to find overlapping bodies.
 */
public class Space {
    private BodyOverlapper overlapper;
    private BroadPhase broadPhase;
    private List<OverlapListener> listeners;
    private List<PairFilter> filters;
    //TODO remove stats
    private SpaceStats stats;

    /**
     * Creates a space.
     * 
     * @param broadPhase Broadphase to find pairs of bodies, cannot be null
     * @param overlapper Overlapper for narrow phase checks, cannot be null
     */
    public Space(BroadPhase broadPhase, BodyOverlapper overlapper) {
        if(overlapper == null) throw new IllegalArgumentException("overlapper cannot be null");
        if(broadPhase == null) throw new IllegalArgumentException("broadPhase cannot be null");
        this.overlapper = overlapper;
        this.broadPhase = broadPhase;
        listeners = new ArrayList<OverlapListener>();
        filters = new ArrayList<PairFilter>();
        stats = new SpaceStats();
    }

    public void setStats(SpaceStats stats) {
        this.stats = stats;
    }

    public SpaceStats getStats() {
        return stats;
    }

    /**
     * Finds overlaps and notifies listeners.
     */
    public void processOverlaps() {
        long runStartTime = System.nanoTime();
        for(Pair pair : pairsFrom(broadPhase)) {
            firePairFound();
            if(shouldDoNarrowPhase(pair)) {
                Translation minTranslation = doNarrowPhase(pair);
                if(minTranslation != null) {
                    fireOverlapFound(pair, minTranslation);
                }
            } else {
                firePairFiltered();
            }
        }
        stats.addProcessOverlapsNanos(System.nanoTime() - runStartTime);
        fireRunCompleted();
    }

    private boolean shouldDoNarrowPhase(Pair pair) {
        for(PairFilter filter : filters) {
            if(filter.shouldSkipNarrowPhase(pair)) {
                return false;
            }
        }
        return true;
    }
    
    private Translation doNarrowPhase(Pair pair) {
        return overlapper.getMinTranslation(pair.getBodyA(), pair.getBodyB());
    }

    private Set<Pair> pairsFrom(BroadPhase broadPhase) {
        long findPairsStartTime = System.nanoTime();
        Set<Pair> pairs = broadPhase.findPairs();
        stats.addFindPairsNanos(System.nanoTime() - findPairsStartTime);
        return pairs;
    }
    
    private void fireRunCompleted() {
        stats.runCompleted();
    }

    private void firePairFound() {
        stats.pairFound();
    }

    private void firePairFiltered() {
        stats.pairFiltered();
    }

    private void fireOverlapFound(Pair pair, Translation minTranslation) {
        stats.overlapFound();
        notifyBodies(pair.getBodyA(), pair.getBodyB());
        notifyListeners(new OverlapEvent(pair, minTranslation));
    }

    private void notifyBodies(Body bodyA, Body bodyB) {
        bodyA.overlapping(bodyB);
        bodyB.overlapping(bodyA);
    }

    private void notifyListeners(OverlapEvent event) {
        for(OverlapListener listener : listeners) {
            listener.onOverlap(event);
        }
    }

    public void addBodies(Collection<? extends Body> bodies) {
        long addBodiesStartTime = System.nanoTime();
        for(Body body : bodies) {
            if(body == null) throw new IllegalArgumentException("bodies cannot contain null");
            broadPhase.add(body);
        }
        stats.addAddBodiesNanos(System.nanoTime() - addBodiesStartTime);
    }

    public void clearBodies() {
        long clearBodiesStartTime = System.nanoTime();
        broadPhase.clear();
        stats.addClearBodiesNanos(System.nanoTime() - clearBodiesStartTime);
    }

    public void addOverlapListener(OverlapListener listener) {
        listeners.add(listener);
    }

    public void removeOverlapListener(OverlapListener listener) {
        listeners.remove(listener);
    }

    public void addPairFilter(PairFilter filter) {
        filters.add(filter);
    }

    public void removePairFilter(PairFilter filter) {
        filters.remove(filter);
    }
}
