package org.spash.broad.hash;

import static org.spash.broad.BroadTestHelper.assertCoordinateBagsEqual;
import static org.spash.broad.BroadTestHelper.coords;

import java.util.List;

import org.junit.Test;
import org.spash.broad.hash.GridCoordinate;
import org.spash.broad.hash.LineSpan;
import org.spash.broad.hash.Span;
import org.spash.shape.Line;


public class LineSpanTest {
    @Test
    public void LineEndpointsAreInSameCell_ReturnsTheCoord() {
        Span span = new LineSpan(new Line(11, 11, 12, 12));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void LineEndpointsAreInHorizontallyAdjacentCells_ReturnsCoordsOfCells() {
        Span span = new LineSpan(new Line(11, 11, 21, 11));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 1),
                new GridCoordinate(2, 1)), cells);
    }

    @Test
    public void LineEndpointsAreInHorizontallyAdjacentCells_OppositeDirection_ReturnsCoordsOfCells() {
        Span span = new LineSpan(new Line(21, 11, 11, 11));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 1),
                new GridCoordinate(2, 1)), cells);
    }

    @Test
    public void LineEndpointsAreInVerticallyAdjacentCells_ReturnsCoordsOfCells() {
        Span span = new LineSpan(new Line(11, 11, 11, 21));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 1),
                new GridCoordinate(1, 2)), cells);
    }

    @Test
    public void LineEndpointsAreInVerticallyAdjacentCells_OppositeDirection_ReturnsCoordsOfCells() {
        Span span = new LineSpan(new Line(11, 21, 11, 11));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 1),
                new GridCoordinate(1, 2)), cells);
    }

    @Test
    public void DiagonalLineAboveCenterOfCellQuad_ReturnsCoordsOfThreeNEMostCellsOfQuad() {
        Span span = new LineSpan(new Line(9, 1, 18, 11));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 0),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void DiagonalLineBelowCenterOfCellQuad_ReturnsCoordsOfThreeSWMostCellsOfQuad() {
        Span span = new LineSpan(new Line(1, 9, 11, 18));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void DiagonalLineThroughCenterOfCellQuad_NWtoSE_ReturnsThreeMostBottomLeftCells() {
        Span span = new LineSpan(new Line(2, 2, 18, 18));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void DiagonalLineThroughCenterOfCellQuad_SEtoNW_ReturnsThreeMostTopRightCells() {
        Span span = new LineSpan(new Line(18, 18, 2, 2));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 0),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void DiagonalLineThroughCenterOfCellQuad_NEtoSW_ReturnsThreeMostBottomRightCells() {
        Span span = new LineSpan(new Line(18, 2, 2, 18));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 0),
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void DiagonalLineThroughCenterOfCellQuad_SWtoNE_ReturnsThreeMostTopLeftCells() {
        Span span = new LineSpan(new Line(2, 18, 18, 2));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 0),
                new GridCoordinate(1, 0),
                new GridCoordinate(0, 1)), cells);
    }

    @Test
    public void VerticalLineAlongCellBoundary_ReturnsCoordsOfCellOnRightSideOfBoundary() {
        Span span = new LineSpan(new Line(10, 2, 10, 18));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(1, 0),
                new GridCoordinate(1, 1)), cells);
    }

    @Test
    public void HorizontalLineAlongCellBoundary_ReturnsCoordsOfCellOnLowerSideOfBoundary() {
        Span span = new LineSpan(new Line(2, 10, 18, 10));

        List<GridCoordinate> cells = span.getCoordinates(10, 10);

        assertCoordinateBagsEqual(coords(
                new GridCoordinate(0, 1),
                new GridCoordinate(1, 1)), cells);
    }
}
