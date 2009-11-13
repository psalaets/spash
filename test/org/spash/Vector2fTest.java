package org.spash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.ALLOWABLE_DELTA;

import org.junit.Test;
import org.spash.Vector2f;

public class Vector2fTest {
    @Test
    public void DefaultConstructorStartsOffAtZeroZero() {
        Vector2f v = new Vector2f();

        assertEquals(0f, v.getX());
        assertEquals(0f, v.getY());
    }

    @Test
    public void CopyConstructorTakesValuesFromParam() {
        Vector2f v = new Vector2f(new Vector2f(1f, 2f));

        assertEquals(1f, v.getX());
        assertEquals(2f, v.getY());
    }

    @Test
    public void TwoArgConstructorSetsXAndYDirectly() {
        Vector2f v = new Vector2f(2f, 1f);

        assertEquals(2f, v.getX());
        assertEquals(1f, v.getY());
    }

    @Test
    public void VectorCanBeSetToAnother() {
        Vector2f v = new Vector2f();

        v.set(new Vector2f(2f, 1f));

        assertEquals(2f, v.getX());
        assertEquals(1f, v.getY());
    }

    @Test
    public void Dot_ReturnsDotProduct() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(3f, 4f);

        float dot = v.dot(other);

        assertEquals(11f, dot);
    }

    @Test
    public void Negate_ReturnsNegatedCopy() {
        Vector2f v = new Vector2f(1f, 2f);

        Vector2f negated = v.negate();

        assertEquals(new Vector2f(-1f, -2f), negated);
    }

    @Test
    public void Negate_DoesNotChangeThis() {
        Vector2f v = new Vector2f(1f, 2f);

        v.negate();

        assertEquals(new Vector2f(1f, 2f), v);
    }

    @Test
    public void Add_AddsParamToThis() {
        Vector2f v = new Vector2f();
        Vector2f param = new Vector2f(1f, 2f);

        v.add(param);

        assertEquals(new Vector2f(1f, 2f), v);
    }

    @Test
    public void Sub_SubtractsParamFromThis() {
        Vector2f v = new Vector2f();
        Vector2f param = new Vector2f(1f, 2f);

        v.sub(param);

        assertEquals(new Vector2f(-1f, -2f), v);
    }

    @Test
    public void Scale_MultipliesXAndYByScaleFactor() {
        Vector2f v = new Vector2f(2f, 3f);

        v.scale(4f);

        assertEquals(new Vector2f(8f, 12f), v);
    }

    @Test
    public void Normalise_OnNonZeroLengthVector_MakesLengthOne() {
        Vector2f v = new Vector2f(2f, 4f);

        v.normalise();

        assertEquals(1f, v.length(), ALLOWABLE_DELTA);
    }

    @Test
    public void Normalise_OnZeroLengthVector_DoesNotChangeVector() {
        Vector2f v = new Vector2f(0f, 0f);

        v.normalise();

        assertEquals(new Vector2f(0f, 0f), v);
    }

    @Test
    public void LengthSquared_OnNonZeroLengthVector_ReturnsLengthSquared() {
        Vector2f v = new Vector2f(0f, 2f);

        assertEquals(4f, v.lengthSquared(), ALLOWABLE_DELTA);
    }

    @Test
    public void LengthSquared_OnZeroLengthVector_ReturnsZero() {
        Vector2f v = new Vector2f(0f, 0f);

        assertEquals(0f, v.lengthSquared());
    }

    @Test
    public void Length_OnNonZeroLengthVector_ReturnsLength() {
        Vector2f v = new Vector2f(0f, 2f);

        assertEquals(2f, v.length(), ALLOWABLE_DELTA);
    }

    @Test
    public void Length_OnZeroLengthVector_ReturnsZero() {
        Vector2f v = new Vector2f(0f, 0f);

        assertEquals(0f, v.length());
    }

    @Test
    public void ProjectOntoUnit_GivenHorizontalUnitVector_ProjectsThisOntoParam() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f unit = new Vector2f(1f, 0f);
        Vector2f result = new Vector2f();

        v.projectOntoUnit(unit, result);

        assertEquals(new Vector2f(1f, 0f), result);
    }

    @Test
    public void Distance_ReturnsDistanceBetweenPoints() {
        Vector2f v = new Vector2f(0f, 0f);
        Vector2f other = new Vector2f(2f, 0f);

        assertEquals(2f, v.distance(other));
    }

    @Test
    public void DistanceSquared_ReturnsDistanceBetweenPointsSquared() {
        Vector2f v = new Vector2f(0f, 0f);
        Vector2f other = new Vector2f(2f, 0f);

        assertEquals(4f, v.distanceSquared(other));
    }

    @Test
    public void Equals_ParamHasDifferentXAndY_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);

        assertFalse(v.equals(new Vector2f(3f, 4f)));
    }

    @Test
    public void Equals_ParamIsNull_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);

        assertFalse(v.equals(null));
    }

    @Test
    public void Equals_ParamIsNotAVector2f_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);

        assertFalse(v.equals("not a Vector2f"));
    }

    @Test
    public void Equals_ParamHasSameXAndY_ReturnsTrue() {
        Vector2f v = new Vector2f(1f, 2f);

        assertTrue(v.equals(new Vector2f(1f, 2f)));
    }

    @Test
    public void Equals_ParamIsSelf_ReturnsTrue() {
        Vector2f v = new Vector2f(1f, 2f);

        assertTrue(v.equals(v));
    }

    @Test
    public void EqualsDelta_XOutsideDeltaYOutsideDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.5f, 2.5f);

        assertFalse(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void EqualsDelta_XInsideDeltaYOutsideDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.3f, 2.5f);

        assertFalse(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void EqualsDelta_XOutsideDeltaYInsideDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.5f, 2.3f);

        assertFalse(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void EqualsDelta_XInsideDeltaYOnDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.4f, 2.5f);

        assertFalse(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void EqualsDelta_XOnDeltaYInsideDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.4f, 2.3f);

        assertFalse(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void EqualsDelta_XInsideDeltaYInsideDelta_ReturnsFalse() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1.3f, 2.3f);

        assertTrue(v.equalsDelta(other, 0.4f));
    }

    @Test
    public void HashCode_EqualsVectorsHaveSameHashCode() {
        Vector2f v = new Vector2f(1f, 2f);
        Vector2f other = new Vector2f(1f, 2f);

        assertEquals(v.hashCode(), other.hashCode());
    }

    @Test
    public void HashCode_IsConsistentAsLongAsVectorsDoNotChange() {
        Vector2f v = new Vector2f(1f, 2f);

        assertEquals(v.hashCode(), v.hashCode());
    }
}
