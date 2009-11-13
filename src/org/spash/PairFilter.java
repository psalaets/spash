package org.spash;

/**
 * Decides what pairs are omitted from the narrow phase check.  Pairs are
 * "filtered out" by these as they go from broad phase to narrow phase.
 */
public interface PairFilter {
    /**
     * Decides if a pair should skip the narrow phase check.
     * 
     * @param pair The pair
     * @return true if pair should skip the narrow phase check, false if the
     * narrow phase check should be done
     */
    boolean shouldSkipNarrowPhase(Pair pair);
}
