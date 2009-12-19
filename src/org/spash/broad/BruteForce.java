package org.spash.broad;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;
import org.spash.ray.Ray;
import org.spash.ray.RayBroadPhase;


/**
 * When acting as an overlaps broad phase, pairs each body with every other
 * body.  When acting as a ray broad phase, considers every body as a potential
 * body.
 */
public class BruteForce implements BroadPhase, RayBroadPhase {
    private List<Body> bodies;

    public BruteForce() {
        bodies = new ArrayList<Body>();
    }

    public Set<Pair> findPairs() {
        Set<Pair> pairs = new HashSet<Pair>();
        for(int i = 0; i < bodies.size(); i++) {
            for(int j = i + 1; j < bodies.size(); j++) {
                pairs.add(new Pair(bodies.get(i), bodies.get(j)));
            }
        }
        return pairs;
    }
    
    public Set<Body> potentialBodies(Ray ray) {
        return new HashSet<Body>(bodies);
    }

    public void add(Body body) {
        bodies.add(body);
    }

    public void clear() {
        bodies.clear();
    }

    @Override
    public String toString() {
        return "BruteForce";
    }
}
