package org.spash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;

public class TranslationTest {
    @Test(expected = IllegalArgumentException.class)
    public void DirectionCannotBeNull() {
        new Translation(null, 5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DistanceCannotBeNegative() {
        new Translation(new Vector2f(1, 0), -5f);
    }

    @Test
    public void ConstructorSetsDirection() {
        Translation translation = new Translation(new Vector2f(1, 0), 5f);

        assertEquals(new Vector2f(1, 0), translation.getDirection());
    }
    
    @Test
    public void ConstructorSetsDistance() {
        Translation translation = new Translation(new Vector2f(1, 0), 5f);

        assertEquals(5f, translation.getDistance());
    }

    @Test
    public void ToVector_ReturnsVectorEqualToDirectionScaledByDistance() {
        Translation translation = new Translation(new Vector2f(1, 0), 5f);

        assertEquals(new Vector2f(5, 0), translation.toVector());
    }

    @Test
    public void Equals_GivenSelf_ReturnsTrue() {
        Translation translation = new Translation(new Vector2f(1, 0), 1f);

        assertTrue(translation.equals(translation));
    }

    @Test
    public void Equals_GivenEqualDirectionEqualDistance_ReturnsTrue() {
        Translation translation1 = new Translation(new Vector2f(1, 0), 1f);
        Translation translation2 = new Translation(new Vector2f(1, 0), 1f);

        assertTrue(translation1.equals(translation2));
    }

    @Test
    public void Equals_GivenDifferentDirectionEqualDistance_ReturnsFalse() {
        Translation translation1 = new Translation(new Vector2f(1, 0), 1f);
        Translation translation2 = new Translation(new Vector2f(0, 1), 1f);

        assertFalse(translation1.equals(translation2));
    }

    @Test
    public void Equals_GivenEqualDirectionDifferentDistance_ReturnsFalse() {
        Translation translation1 = new Translation(new Vector2f(1, 0), 1f);
        Translation translation2 = new Translation(new Vector2f(1, 0), 2f);

        assertFalse(translation1.equals(translation2));
    }

    @Test
    public void Equals_GivenDifferentDirectionDifferentDistance_ReturnsFalse() {
        Translation translation1 = new Translation(new Vector2f(1, 0), 1f);
        Translation translation2 = new Translation(new Vector2f(0, 1), 2f);

        assertFalse(translation1.equals(translation2));
    }

    @Test
    public void Equals_GivenNonTranslation_ReturnsFalse() {
        Translation translation = new Translation(new Vector2f(1, 0), 1f);

        assertFalse(translation.equals("not a translation"));
    }

    @Test
    public void Equals_GivenNull_ReturnsFalse() {
        Translation translation = new Translation(new Vector2f(1, 0), 1f);

        assertFalse(translation.equals(null));
    }

    @Test
    public void HashCode_EqualTranslationsHaveSameHashCode() {
        Translation translation1 = new Translation(new Vector2f(1, 0), 1f);
        Translation translation2 = new Translation(new Vector2f(1, 0), 1f);

        assertEquals(translation1.hashCode(), translation2.hashCode());
    }

    @Test
    public void HashCode_IsConsistent() {
        Translation translation = new Translation(new Vector2f(1, 0), 1f);

        assertEquals(translation.hashCode(), translation.hashCode());
    }

    @Test
    public void Flip_ReturnsTranslationWithOppositeDirection() {
        Translation translation = new Translation(new Vector2f(1, 0), 2);

        Translation flipped = translation.flip();

        assertEquals(new Vector2f(-1, 0), flipped.getDirection());
    }

    @Test
    public void Flip_ReturnsTranslationWithSameDistance() {
        Translation translation = new Translation(new Vector2f(1, 0), 2);

        Translation flipped = translation.flip();

        assertEquals(2f, flipped.getDistance());
    }
}
