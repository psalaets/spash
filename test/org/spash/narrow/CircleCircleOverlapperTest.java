package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.CircleCircleOverlapper;
import org.spash.shape.Circle;


public class CircleCircleOverlapperTest {
    CircleCircleOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new CircleCircleOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_CirclesAreDisjoint_ReturnsNull() {
        Circle c1 = new Circle(0, 0, 5);
        Circle c2 = new Circle(10, 0, 5);

        assertNull(overlapper.getMinTranslation(c1, c2));
    }

    @Test
    public void GetMinTranslation_CirclesAreOverlapping_ReturnsMinTranslation() {
        Circle c1 = new Circle(0, 0, 5);
        Circle c2 = new Circle(7.5f, 0, 5);

        Translation actual = overlapper.getMinTranslation(c1, c2);

        assertEquals(new Translation(new Vector2f(1, 0), 2.5f), actual);
    }

    @Test
    public void GetMinTranslation_CirclesAreTouchingButDontOverlap_ReturnsNull() {
        Circle c1 = new Circle(0, 0, 5);
        Circle c2 = new Circle(10, 0, 5);

        assertNull(overlapper.getMinTranslation(c1, c2));
    }
}
