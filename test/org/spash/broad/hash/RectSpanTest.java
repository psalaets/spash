package org.spash.broad.hash;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.spash.broad.BroadTestHelper.assertCoordinateBagsEqual;
import static org.spash.broad.BroadTestHelper.coords;

import java.util.List;

import org.junit.Test;
import org.spash.Shape;
import org.spash.broad.hash.GridCoordinate;
import org.spash.broad.hash.RectSpan;
import org.spash.broad.hash.Span;


public class RectSpanTest {
    @Test
    public void ShapeExtremesAreInSameCell_ReturnsTheCoordinate() {
        Span span = new RectSpan(shapeWithExtremesAt(11, 11, 12, 12));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void ShapeExtremesInSameRow_ReturnsCoordsInRow() {
        Span span = new RectSpan(shapeWithExtremesAt(1, 11, 21, 11));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 1),
                new GridCoordinate(2, 1)), cells);
    }

    @Test
    public void ShapeExtremesInSameColumn_ReturnsCoordsInColumn() {
        Span span = new RectSpan(shapeWithExtremesAt(1, 1, 1, 21));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1),
                new GridCoordinate(0, 2)), cells);
    }

    @Test
    public void ShapeExtremesFormRect_ReturnsCoordsInRect() {
        Span span = new RectSpan(shapeWithExtremesAt(1, 1, 11, 21));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1),
                new GridCoordinate(0, 2),
                new GridCoordinate(1, 0),
                new GridCoordinate(1, 1),
                new GridCoordinate(1, 2)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtNECornerOfACell_ReturnsTheCoordAndRightNeighbor() {
        Span span = new RectSpan(shapeWithExtremesAt(5, 0, 10, 5));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 0)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtEastEdgeOfACell_ReturnsTheCoordAndRightNeighbor() {
        Span span = new RectSpan(shapeWithExtremesAt(5, 3, 10, 5));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);
        
        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 0)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtSECornerOfACell_ReturnsTheCoordAndRightAndBottomAndBottomRightNeighbors() {
        Span span = new RectSpan(shapeWithExtremesAt(5, 5, 10, 10));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 1),
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 0)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtSouthEdgeOfACell_ReturnsTheCoordAndBottomNeighbor() {
        Span span = new RectSpan(shapeWithExtremesAt(3, 5, 8, 10));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);
        
        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtSWCornerOfACell_ReturnsTheCoordAndBottomNeighbors() {
        Span span = new RectSpan(shapeWithExtremesAt(0, 5, 5, 10));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtWestEdgeOfACell_ReturnsTheCoord() {
        Span span = new RectSpan(shapeWithExtremesAt(0, 2, 5, 5));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(new GridCoordinate(0, 0)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtNWCornerOfACell_ReturnsTheCoord() {
        Span span = new RectSpan(shapeWithExtremesAt(0, 5, 5, 0));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(new GridCoordinate(0, 0)), cells);
    }

    @Test
    public void ShapeExtremesFormRectAtNorthEdgeOfACell_ReturnsTheCoord() {
        Span span = new RectSpan(shapeWithExtremesAt(2, 0, 5, 5));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(new GridCoordinate(0, 0)), cells);
    }

    private Shape shapeWithExtremesAt(float minX, float minY, float maxX, float maxY) {
        Shape shape = mock(Shape.class);
        when(shape.getMinX()).thenReturn(minX);
        when(shape.getMinY()).thenReturn(minY);
        when(shape.getMaxX()).thenReturn(maxX);
        when(shape.getMaxY()).thenReturn(maxY);
        return shape;
    }
}
