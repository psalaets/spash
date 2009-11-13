package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.spash.TestHelper.ALLOWABLE_DELTA;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.LineRectOverlapper;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class LineRectOverlapperTest {
    /** square root of 2 */
    private static final float SQRT_2 = (float)Math.sqrt(2);

    LineRectOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new LineRectOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_RectAndLineAreDisjoint_ReturnsNull() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(0, 10, 20, 10);

        assertNull(overlapper.getMinTranslation(line, rect));
    }

    @Test
    public void GetMinTranslation_VerticalTranslation() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(-5, -2, 5, -2);

        assertEquals(new Translation(new Vector2f(0, 1), 3f), overlapper.getMinTranslation(line, rect));
    }

    @Test
    public void GetMinTranslation_HorizontalTranslation() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(4, -10, 4, 10);

        assertEquals(new Translation(new Vector2f(-1, 0), 1f), overlapper.getMinTranslation(line, rect));
    }

    @Test
    public void GetMinTranslation_LineNormalTranslation() {
        Rect rect = new Rect(4, 4, 10, 10);
        Line line = new Line(1, -1, -1, 1);

        Translation actual = overlapper.getMinTranslation(line, rect);
        assertEquals("translation.direction.x is wrong", SQRT_2 / 2, actual.getDirection().getX(), ALLOWABLE_DELTA);
        assertEquals("translation.direction.y is wrong", SQRT_2 / 2, actual.getDirection().getY(), ALLOWABLE_DELTA);
        assertEquals("translation.distance is wrong", SQRT_2, actual.getDistance(), ALLOWABLE_DELTA);
    }

    @Test
    public void GetMinTranslation_RectAndLineTouchVerticallyButDontOverlap_ReturnsNull() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(-10, 5, 10, 5);

        assertNull(overlapper.getMinTranslation(line, rect));
    }

    @Test
    public void GetMinTranslation_RectAndLineTouchHorizontallyButDontOverlap_ReturnsNull() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(5, -10, 5, 10);

        assertNull(overlapper.getMinTranslation(line, rect));
    }

    @Test
    public void GetMinTranslation_RectAndLineTouchAtCornerButDontOverlap_ReturnsNull() {
        Rect rect = new Rect(-5, 5, 10, 10);
        Line line = new Line(-2, -2, 2, 2);

        assertNull(overlapper.getMinTranslation(line, rect));
    }
}
