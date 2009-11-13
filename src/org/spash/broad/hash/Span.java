package org.spash.broad.hash;

import java.util.List;

/**
 * A group of coordinates in a grid.
 */
public interface Span {
    /**
     * Gets the coordinates of this span.
     * 
     * @param cellWidth Width of a grid's cell
     * @param cellHeight Height of a grid's cell
     * @return coordinates
     */
    List<GridCoordinate> getCoordinates(int cellWidth, int cellHeight);
}
