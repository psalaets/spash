package org.spash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.spash.Body;
import org.spash.Pair;

public class PairTest {
    @Test
    public void ConstructorSetsBodyA() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);

        Pair pair = new Pair(bodyA, bodyB);

        assertEquals(bodyA, pair.getBodyA());
    }

    @Test
    public void ConstructorSetsBodyB() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);

        Pair pair = new Pair(bodyA, bodyB);

        assertEquals(bodyB, pair.getBodyB());
    }

    @Test(expected = IllegalArgumentException.class)
    public void BodyACannotBeNull() {
        Body bodyB = mock(Body.class);

        new Pair(null, bodyB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void BodyBCannotBeNull() {
        Body bodyA = mock(Body.class);

        new Pair(bodyA, null);
    }

    @Test
    public void Equals_OtherHasSameBodies_ReturnsTrue() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair other = new Pair(bodyA, bodyB);

        assertTrue(pair.equals(other));
    }

    @Test
    public void Equals_OtherHasSameBodiesButSwapped_ReturnsTrue() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair other = new Pair(bodyB, bodyA);

        assertTrue(pair.equals(other));
    }

    @Test
    public void Equals_OtherHasSameADifferentB_ReturnsFalse() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Body bodyC = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair other = new Pair(bodyA, bodyC);

        assertFalse(pair.equals(other));
    }

    @Test
    public void Equals_OtherHasDifferentASameB_ReturnsFalse() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Body bodyC = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair other = new Pair(bodyC, bodyB);

        assertFalse(pair.equals(other));
    }

    @Test
    public void Equals_OtherHasDifferentADifferentB_ReturnsFalse() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Body bodyC = mock(Body.class);
        Body bodyD = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair other = new Pair(bodyC, bodyD);

        assertFalse(pair.equals(other));
    }

    @Test
    public void Equals_OtherIsNotAPair_ReturnsFalse() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);

        assertFalse(pair.equals("not a Pair"));
    }

    @Test
    public void Equals_OtherIsNull_ReturnsFalse() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);

        assertFalse(pair.equals(null));
    }

    @Test
    public void HashCode_EqualPairsHaveEqualHashCodes() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair1 = new Pair(bodyA, bodyB);
        Pair pair2 = new Pair(bodyA, bodyB);

        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    public void HashCode_EqualPairsWithSwappedBodiesHaveEqualHashCodes() {
        Body bodyA = mock(Body.class);
        Body bodyB = mock(Body.class);
        Pair pair = new Pair(bodyA, bodyB);
        Pair swappedPair = new Pair(bodyB, bodyA);

        assertEquals(pair.hashCode(), swappedPair.hashCode());
    }

    @Test
    public void HashCode_CalledTwice_ReturnsSameValueEachTime() {
        Pair pair = new Pair(mock(Body.class), mock(Body.class));

        assertEquals(pair.hashCode(), pair.hashCode());
    }
}
