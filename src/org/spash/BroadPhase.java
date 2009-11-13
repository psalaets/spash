package org.spash;

import java.util.Set;

/**
 * Determines which bodies could be overlapping.
 */
public interface BroadPhase {
    /**
     * Adds a body to this broad phase.
     * 
     * @param body Body to add
     */
    void add(Body body);

    /**
     * Clears bodies from this broad phase.
     */
    void clear();

    /**
     * Finds pairs of bodies that could be overlapping.
     * 
     * @return Body pairs to be checked in narrow phase
     */
    Set<Pair> findPairs();
}
