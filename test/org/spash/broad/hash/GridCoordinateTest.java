package org.spash.broad.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.spash.broad.hash.GridCoordinate;

public class GridCoordinateTest {
    @Test
    public void ConstructorSetsX() {
        GridCoordinate coord = new GridCoordinate(2, 3);

        assertEquals(2, coord.getX());
    }

    @Test
    public void ConstructorSetsY() {
        GridCoordinate coord = new GridCoordinate(2, 3);

        assertEquals(3, coord.getY());
    }

    @Test
    public void CoordsWithDifferentXAndY_NotEqual() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        GridCoordinate other = new GridCoordinate(4, 5);

        assertFalse(coord.equals(other));
    }

    @Test
    public void CoordsWithXEqualYDifferent_NotEqual() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        GridCoordinate other = new GridCoordinate(2, 5);

        assertFalse(coord.equals(other));
    }

    @Test
    public void CoordsWithYEqualXDifferent_NotEqual() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        GridCoordinate other = new GridCoordinate(4, 3);

        assertFalse(coord.equals(other));
    }

    @Test
    public void CoordsWithXEqualAndYEqual_Equal() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        GridCoordinate other = new GridCoordinate(2, 3);

        assertTrue(coord.equals(other));
    }

    @Test
    public void CoordsAreEqualToSelf() {
        GridCoordinate coord = new GridCoordinate(2, 3);

        assertTrue(coord.equals(coord));
    }

    @Test
    public void CoordsAreNotEqualToNull() {
        GridCoordinate coord = new GridCoordinate(2, 3);

        assertFalse(coord.equals(null));
    }

    @Test
    public void CoordsAreNotEqualToNonCoords() {
        GridCoordinate coord = new GridCoordinate(2, 3);

        assertFalse(coord.equals("not a grid coordinate"));
    }

    @Test
    public void HashCodeForEqualCoords_ProducesSameValues() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        GridCoordinate other = new GridCoordinate(2, 3);

        assertEquals(coord.hashCode(), other.hashCode());
    }

    @Test
    public void HashCodeDoesNotChangeOverMultipleCalls() {
        GridCoordinate coord = new GridCoordinate(2, 3);
        int hashCode1 = coord.hashCode();
        int hashCode2 = coord.hashCode();

        assertEquals(hashCode1, hashCode2);
    }
}
