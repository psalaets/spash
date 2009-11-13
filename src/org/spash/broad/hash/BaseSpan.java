package org.spash.broad.hash;

public strictfp class BaseSpan {
    /**
     * Converts space position to grid coordinate, along one axis.
     */
    protected int spaceToGridCoordinate(float spacePosition, float cellSize) {
        if(spacePosition >= 0f) {
            return (int)(spacePosition / cellSize);
        }
        return (int)Math.floor(spacePosition / cellSize);
    }
}
