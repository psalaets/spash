package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.RectRectOverlapper;
import org.spash.shape.Rect;


public class RectRectOverlapperTest {
    RectRectOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new RectRectOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_VerticalTranslation() {
        Rect rect1 = new Rect(0, 0, 20, 20);
        Rect rect2 = new Rect(0, 15, 20, 20);

        assertEquals(new Translation(new Vector2f(0, 1), 5f), overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_HorizontalTranslation() {
        Rect rect1 = new Rect(0, 0, 20, 20);
        Rect rect2 = new Rect(15, 0, 20, 20);

        assertEquals(new Translation(new Vector2f(1, 0), 5f), overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_BothCouldBeMinTranslation_ReturnsVerticalTranslation() {
        Rect rect1 = new Rect(0, 0, 20, 20);
        Rect rect2 = new Rect(15, 15, 20, 20);

        assertEquals(new Translation(new Vector2f(0, 1), 5f), overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_RectsAreDisjoint_ReturnsNull() {
        Rect rect1 = new Rect(0, 0, 10, 10);
        Rect rect2 = new Rect(10, 0, 10, 10);

        assertNull(overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_RectsTouchVerticallyButDontOverlap_ReturnsNull() {
        Rect rect1 = new Rect(0, 0, 10, 10);
        Rect rect2 = new Rect(0, 10, 10, 10);

        assertNull(overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_RectsTouchHorizontallyButDontOverlap_ReturnsNull() {
        Rect rect1 = new Rect(0, 0, 10, 10);
        Rect rect2 = new Rect(10, 0, 10, 10);

        assertNull(overlapper.getMinTranslation(rect1, rect2));
    }

    @Test
    public void GetMinTranslation_RectsTouchAtCornersButDontOverlap_ReturnsNull() {
        Rect rect1 = new Rect(0, 0, 10, 10);
        Rect rect2 = new Rect(10, 10, 10, 10);

        assertNull(overlapper.getMinTranslation(rect1, rect2));
    }
}
