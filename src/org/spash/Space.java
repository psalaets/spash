package org.spash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Brings broad phase and narrow phase together to find overlapping bodies.
 */
public class Space {
    private BodyOverlapper overlapper;
    private BroadPhase broadPhase;
    private List<OverlapListener> listeners;
    private List<PairFilter> filters;

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
    }

    /**
     * Finds overlaps and notifies listeners.
     */
    public void processOverlaps() {
        for(Pair pair : broadPhase.findPairs()) {
            if(shouldDoNarrowPhase(pair)) {
                Translation minTranslation = doNarrowPhase(pair);
                if(minTranslation != null) {
                    overlapFound(pair, minTranslation);
                }
            }
        }
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

    private void overlapFound(Pair pair, Translation minTranslation) {
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
        for(Body body : bodies) {
            if(body == null) throw new IllegalArgumentException("bodies cannot contain null");
            broadPhase.add(body);
        }
    }

    public void clearBodies() {
        broadPhase.clear();
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
