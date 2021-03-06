package org.spash.broad.sortsweep;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;

/**
 * Broad phase that is good when bodies are spread out along the x or y axis.
 */
public class SortAndSweep implements BroadPhase {
    private Axis axis;
    private List<Body> bodies;

    /**
     * Creates broad phase good for bodies spread out along the x axis.
     */
    public static SortAndSweep alongX() {
        return new SortAndSweep(new XAxis());
    }

    /**
     * Creates broad phase good for bodies spread out along the y axis.
     */
    public static SortAndSweep alongY() {
        return new SortAndSweep(new YAxis());
    }

    private SortAndSweep(Axis axis) {
        this.axis = axis;
        bodies = new ArrayList<Body>();
    }

    public void add(Body body) {
        bodies.add(body);
    }

    public Set<Pair> findPairs() {
        sortAlongAxis();
        return sweepForPairs();
    }

    private void sortAlongAxis() {
        sort(bodies, axis);
    }
    
    /*
     * Assumes bodies are already sorted along the axis
     */
    private Set<Pair> sweepForPairs() {
        Set<Pair> pairs = new HashSet<Pair>();
        for(int i = 0; i < bodies.size(); i++) {
            //for each body I
            Body bodyI = bodies.get(i);
            for(int j = i + 1; j < bodies.size(); j++) {
                //and every body J after it on the axis
                Body bodyJ = bodies.get(j);
                if(axis.innerEdgesOverlap(bodyI, bodyJ)) { //pair I and J
                    pairs.add(new Pair(bodyI, bodyJ));
                } else { //until we find a J that is past I
                    break;
                }
            }
        }
        return pairs;
    }

    public void clear() {
        bodies.clear();
    }

    @Override
    public String toString() {
        return "SortAndSweepAlong" + axis;
    }
}
