package org.spash.broad.hash;

import java.util.ArrayList;
import java.util.List;

import org.spash.Shape;
import org.spash.Vector2f;


/**
 * Rectangular span of coordinates.
 */
public strictfp class RectSpan extends BaseSpan implements Span {
    private Vector2f upperLeft;
    private Vector2f lowerRight;

    /**
     * Creates a rect span for a shape.
     */
    public RectSpan(Shape shape) {
        upperLeft = new Vector2f(shape.getMinX(), shape.getMinY());
        lowerRight = new Vector2f(shape.getMaxX(), shape.getMaxY());
    }

    public List<GridCoordinate> getCoordinates(int cellWidth, int cellHeight) {
        int left = spaceToGridCoordinate(upperLeft.getX(), cellWidth);
        int right = spaceToGridCoordinate(lowerRight.getX(), cellWidth);
        int top = spaceToGridCoordinate(upperLeft.getY(), cellHeight);
        int bottom = spaceToGridCoordinate(lowerRight.getY(), cellHeight);

        List<GridCoordinate> coordinates = new ArrayList<GridCoordinate>(spanSize(left, right, top, bottom));
        for(int i = left; i <= right; i++) {
            for(int j = top; j <= bottom; j++) {
                coordinates.add(new GridCoordinate(i, j));
            }
        }
        return coordinates;
    }

    /**
     * Calculates how many coords will be in this span.
     */
    private int spanSize(int left, int right, int top, int bottom) {
        return ((right - left) + 1) * ((bottom - top) + 1);
    }
}
