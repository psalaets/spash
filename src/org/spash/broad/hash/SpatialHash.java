package org.spash.broad.hash;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.spash.Body;
import org.spash.BroadPhase;
import org.spash.Pair;

/**
 * Spatial hash implementation.
 */
public strictfp class SpatialHash implements BroadPhase {
    private Map<GridCoordinate, Cell> hash;
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
        hash = new HashMap<GridCoordinate, Cell>();
    }

    public Set<Pair> findPairs() {
        Set<Pair> pairs = new HashSet<Pair>();
        for(Cell cell : populatedCells()) {
            if(cell.hasPairs()) {
                pairs.addAll(cell.getPairs());
            }
        }
        return pairs;
    }

    private Collection<Cell> populatedCells() {
        return hash.values();
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
    private Cell getCellAt(GridCoordinate coord) {
        Cell cell = hash.get(coord);
        if(cell == null) {
            cell = new Cell();
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
