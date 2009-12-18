package org.spash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spash.ray.Ray;
import org.spash.ray.RayBodyIntersector;
import org.spash.ray.RayBroadPhase;
import org.spash.ray.RayContact;

/**
 * Brings broad phase and narrow phase together to find overlapping bodies.
 */
public class Space {
    private BodyOverlapper overlapper;
    private BroadPhase broadPhase;
    private List<OverlapListener> listeners;
    private List<PairFilter> filters;
    private RayBroadPhase rayBroadPhase;
    private RayBodyIntersector intersector;

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

    /**
     * Gives this space the stuff to do ray queries.
     *
     * @param rayBroadPhase
     * @param intersector
     */
    public void equipForRays(RayBroadPhase rayBroadPhase, RayBodyIntersector intersector) {
        if(rayBroadPhase == null) throw new IllegalArgumentException("rayBroadPhase cannot be null");
        if(intersector == null) throw new IllegalArgumentException("intersector cannot be null");

        this.rayBroadPhase = rayBroadPhase;
        this.intersector = intersector;
    }

    /**
     * Tells if a body is reached by a ray.
     *
     * @param body
     * @param ray
     * @return Ray contact if the ray reaches the body, null otherwise
     */
    public RayContact isReached(Body body, Ray ray) {
        if(intersector == null) throw new IllegalStateException("This space has not been equipped for ray queries, see Space#equipForRays");
        ROVector2f point = intersector.intersect(ray, body);
        if(point != null) {
            return new RayContact(ray, body, point);
        }
        return null;
    }
}
