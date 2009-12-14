package org.spash.broad.hash;

import java.util.ArrayList;
import java.util.List;

import org.spash.ROVector2f;
import org.spash.Vector2f;


/**
 * Span that walks along a line. Algorithm and code taken from<br>
 * 
 * http://www.cs.yorku.ca/~amana/research/grid.pdf<br>
 * http://www.flipcode.com/archives/Raytracing_Topics_Techniques-Part_4_Spatial_Subdivisions.shtml
 */
public strictfp class LineSpan extends BaseSpan implements Span {
    private Vector2f startPoint;
    private Vector2f endPoint;
    /** normalized direction vector */
    private Vector2f direction;
    private GridCoordinate currentCoord;
    private Vector2f tmax;
    private Vector2f tdelta;
    private Vector2f cellBound;
    private int stepX;
    private int stepY;

    public LineSpan(ROVector2f start, ROVector2f end) {
        startPoint = new Vector2f(start);
        endPoint = new Vector2f(end);
        direction = getDirection(startPoint, endPoint);

        tmax = new Vector2f();
        tdelta = new Vector2f();
        cellBound = new Vector2f();
        stepX = 0;
        stepY = 0;
    }

    private Vector2f getDirection(ROVector2f start, ROVector2f end) {
        Vector2f direction = new Vector2f(end);
        direction.sub(start);
        direction.normalise();
        return direction;
    }

    public List<GridCoordinate> getCoordinates(int cellWidth, int cellHeight) {
        currentCoord = positionToCoordinate(startPoint, cellWidth, cellHeight);
        GridCoordinate endCoord = positionToCoordinate(endPoint, cellWidth, cellHeight);

        initStepInfo(cellWidth, cellHeight);

        List<GridCoordinate> coords = new ArrayList<GridCoordinate>();
        while(!hasGonePast(endCoord)) {
            coords.add(currentCoord);
            if(shouldMoveHorizontally()) {
                moveHorizontally();
            } else {
                moveVertically();
            }
        }
        return coords;
    }

    private boolean shouldMoveHorizontally() {
        return tmax.getX() < tmax.getY();
    }

    private void moveHorizontally() {
        tmax.setX(tmax.getX() + tdelta.getX());
        currentCoord = new GridCoordinate(currentCoord.getX() + stepX, currentCoord.getY());
    }

    private void moveVertically() {
        tmax.setY(tmax.getY() + tdelta.getY());
        currentCoord = new GridCoordinate(currentCoord.getX(), currentCoord.getY() + stepY);
    }

    private GridCoordinate positionToCoordinate(ROVector2f position, int cellWidth, int cellHeight) {
        int x = spaceToGridCoordinate(position.getX(), cellWidth);
        int y = spaceToGridCoordinate(position.getY(), cellHeight);
        return new GridCoordinate(x, y);
    }

    /**
     * Prepare some stuff for the hard math
     */
    private void initStepInfo(int cellWidth, int cellHeight) {
        if (direction.getX() > 0.0f) {
            stepX = 1;
            cellBound.setX((currentCoord.getX() + 1) * cellWidth);
        } else {
            stepX = -1;
            cellBound.setX(currentCoord.getX() * cellWidth);
        }
        if (direction.getY() > 0.0f) {
            stepY = 1;
            cellBound.setY((currentCoord.getY() + 1) * cellHeight);
        } else {
            stepY = -1;
            cellBound.setY(currentCoord.getY() * cellHeight);
        }

        float rxr = 0f;
        if (direction.getX() != 0f) {
            rxr = 1f / direction.getX();
            tmax.setX((cellBound.getX() - startPoint.getX()) * rxr);
            tdelta.setX(cellWidth * stepX * rxr);
        } else {
            tmax.setX(Float.MAX_VALUE);
        }

        float ryr = 0f;
        if (direction.getY() != 0f) {
            ryr = 1f / direction.getY();
            tmax.setY((cellBound.getY() - startPoint.getY()) * ryr);
            tdelta.setY(cellHeight * stepY * ryr);
        } else {
            tmax.setY(Float.MAX_VALUE);
        }
    }

    private boolean hasGonePast(GridCoordinate coord) {
        if (stepX > 0) { // going right
            if (currentCoord.getX() >= coord.getX() + stepX) {
                return true;
            }
        } else if (stepX < 0) { // going left
            if (currentCoord.getX() <= coord.getX() + stepX) {
                return true;
            }
        }
        if (stepY > 0) { // going down
            if (currentCoord.getY() >= coord.getY() + stepY) {
                return true;
            }
        } else if (stepY < 0) { // going up
            if (currentCoord.getY() <= coord.getY() + stepY) {
                return true;
            }
        }
        return false;
    }
}
