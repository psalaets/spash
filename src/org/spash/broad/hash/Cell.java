package org.spash.broad.hash;

import java.util.ArrayList;
import java.util.List;

import org.spash.Body;
import org.spash.Pair;

/**
 * A group of bodies. 
 */
public class Cell {
    private List<Body> bodies;
    
    public Cell() {
        bodies = new ArrayList<Body>();
    }
    
    /**
     * @return Pairs for every possible combo of bodies in this cell. 
     */
    public List<Pair> getPairs() {
        List<Pair> pairs = new ArrayList<Pair>();
        for(int i = 0; i < bodies.size(); i++) {
            for(int j = i + 1; j < bodies.size(); j++) {
                pairs.add(new Pair(bodies.get(i), bodies.get(j)));
            }
        }
        return pairs;
    }

    /**
     * Tells if this cell has at least one pair.
     */
    public boolean hasPairs() {
        return bodies.size() > 1;
    }
    
    public void add(Body body) {
        bodies.add(body);
    }
}
