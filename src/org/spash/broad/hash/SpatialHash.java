package org.spash.broad.hash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;

/**
 * Spatial hash implementation.
 */
//TODO make a Cell class?
public strictfp class SpatialHash implements BroadPhase {
    private Map<GridCoordinate, List<Body>> hash;
    private int cellSize;
    private SpanFactory spanFactory;

    /**
     * Creates a spatial hash.
     * 
     * @param spanFactory Factory to create coord spans for bodies, cannot be
     * null
     * @param cellSize Width and height of a cell, must be > 0
     */
    public SpatialHash(SpanFactory spanFactory, int cellSize) {
        if(spanFactory == null) throw new IllegalArgumentException("spanFactory cannot be null");
        if(cellSize <= 0) throw new IllegalArgumentException("cellSize must be > 0");
        this.spanFactory = spanFactory;
        this.cellSize = cellSize;
        hash = new HashMap<GridCoordinate, List<Body>>();
    }

    public Set<Pair> findPairs() {
        Set<Pair> pairs = new HashSet<Pair>();
        for(List<Body> cell : populatedCells()) {
            if(hasAtLeastOnePair(cell)) {
                collectPairs(cell, pairs);
            }
        }
        return pairs;
    }

    private Collection<List<Body>> populatedCells() {
        return hash.values();
    }

    private boolean hasAtLeastOnePair(List<Body> cell) {
        return cell.size() > 1;
    }

    /**
     * Adds all pairs from a body list to a pair set.
     * 
     * @param bodies Bodies to add
     * @param pairs Pairs from the body list
     */
    private void collectPairs(List<Body> bodies, Set<Pair> pairs) {
        for(int i = 0; i < bodies.size(); i++) {
            for(int j = i + 1; j < bodies.size(); j++) {
                pairs.add(new Pair(bodies.get(i), bodies.get(j)));
            }
        }
    }

    public void add(Body body) {
        Span span = spanFactory.createSpanFor(body.getShape());
        for(GridCoordinate coord : span.getCoordinates(cellSize, cellSize)) {
            getCellAt(coord).add(body);
        }
    }

    /**
     * Returns a grid cell.
     * 
     * @param coord Location of the cell to get
     * @return Cell at coord, never null
     */
    private List<Body> getCellAt(GridCoordinate coord) {
        List<Body> cell = hash.get(coord);
        if(cell == null) {
            cell = new ArrayList<Body>();
            hash.put(coord, cell);
        }
        return cell;
    }

    public void clear() {
        hash.clear();
    }

    /**
     * Gets the width and height of a cell in this hash.
     * 
     * @return cell size
     */
    public int getCellSize() {
        return cellSize;
    }

    @Override
    public String toString() {
        return "[SpatialHash cellSize=" + cellSize + "]";
    }
}
