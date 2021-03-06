package org.spash;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        
        intersector = noIntersector();
        rayBroadPhase = noRayBroadPhase();
    }

    private RayBroadPhase noRayBroadPhase() {
        return new RayBroadPhase() {
            public Set<Body> potentialBodies(Ray ray) {
                throw new IllegalStateException("This space has not been equipped for ray queries, see Space#equipForRays");
            }
            public void add(Body body) {}
            public void clear() {}
        };
    }
    
    private RayBodyIntersector noIntersector() {
        return new RayBodyIntersector() {
            public ROVector2f intersect(Ray ray, Body body) {
                throw new IllegalStateException("This space has not been equipped for ray queries, see Space#equipForRays");
            }
        };
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
            add(body);
        }
    }

    private void add(Body body) {
        if(body == null) throw new IllegalArgumentException("bodies cannot contain null");
        broadPhase.add(body);
        if(broadPhase != rayBroadPhase) {
            rayBroadPhase.add(body);
        }
    }

    public void clearBodies() {
        broadPhase.clear();
        if(broadPhase != rayBroadPhase) {
            rayBroadPhase.clear();
        }
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
     * Returns all contacts made by a ray.
     *
     * @param ray
     * @return Ray contacts, never null
     */
    public List<RayContact> allReached(Ray ray) {
        List<RayContact> contacts = new ArrayList<RayContact>();
        for(Body body : rayBroadPhase.potentialBodies(ray)) {
            RayContact contact = isReached(body, ray);
            if(contact != null) {
                contacts.add(contact);
            }
        }
        sort(contacts, byAscendingDistanceFromRayStart());
        return contacts;
    }

    private Comparator<RayContact> byAscendingDistanceFromRayStart() {
        return new Comparator<RayContact>() {
            public int compare(RayContact c1, RayContact c2) {
                if(c1.distanceFromRayStart() < c2.distanceFromRayStart()) return -1;
                if(c1.distanceFromRayStart() > c2.distanceFromRayStart()) return 1;
                return 0;
            }
        };
    }
    
    /**
     * Tells if a body is reached by a ray.
     *
     * @param body
     * @param ray
     * @return Ray contact if the ray reaches the body, null otherwise
     */
    public RayContact isReached(Body body, Ray ray) {
        ROVector2f point = intersector.intersect(ray, body);
        if(point != null) {
            return new RayContact(ray, body, point);
        }
        return null;
    }
}
