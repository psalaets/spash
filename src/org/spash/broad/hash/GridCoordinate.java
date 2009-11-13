package org.spash.broad.hash;

/**
 * A cell's location in a grid.
 */
public strictfp class GridCoordinate {
    private int x;
    private int y;

    /**
     * Creates a grid coordinate.
     */
    public GridCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int result = 1523;
        result = (5711 * result) + x;
        result = (5711 * result) + y;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof GridCoordinate) {
            GridCoordinate other = (GridCoordinate)o;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
