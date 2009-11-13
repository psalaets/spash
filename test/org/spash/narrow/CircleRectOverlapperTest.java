package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.spash.TestHelper.ALLOWABLE_DELTA;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.CircleRectOverlapper;
import org.spash.shape.Circle;
import org.spash.shape.Rect;


public class CircleRectOverlapperTest {
    /** square root of 2 */
    private static final float SQRT_2 = (float)Math.sqrt(2);

    CircleRectOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new CircleRectOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_CircleAndRectAreDisjoint_ReturnsNull() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(10, 10, 5, 5);

        assertNull(overlapper.getMinTranslation(circle, rect));
    }

    @Test
    public void GetMinTranslation_HorizontalTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(5, 1, 5, 5);

        assertEquals(new Translation(new Vector2f(1, 0), 2.5f), overlapper.getMinTranslation(circle, rect));
    }

    @Test
    public void GetMinTranslation_VerticalTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(1, 5, 5, 5);

        assertEquals(new Translation(new Vector2f(0, 1), 2.5f), overlapper.getMinTranslation(circle, rect));
    }

    @Test
    public void GetMinTranslation_ClosestVertexTranslation() {
        Circle circle = new Circle(30, -30, 30);
        Rect rect = new Rect(0, 0, 2, 2);

        // put upper right corner of rect at circle center
        Vector2f rectPos = new Vector2f(29, -29);
        // move rect towards (0, 0) so its top right corner barely overlaps edge
        // of circle
        Vector2f moveRectBy = new Vector2f(-1, 1);
        moveRectBy.normalise();
        moveRectBy.scale(circle.getRadius() - 2f);
        rectPos.add(moveRectBy);
        rect.setPosition(rectPos);

        Translation actual = overlapper.getMinTranslation(circle, rect);
        assertEquals("translation.direction.x is wrong", -SQRT_2 / 2, actual.getDirection().getX(), ALLOWABLE_DELTA);
        assertEquals("translation.direction.y is wrong", SQRT_2 / 2, actual.getDirection().getY(), ALLOWABLE_DELTA);
        assertEquals("translation.distance is wrong", 2f, actual.getDistance(), ALLOWABLE_DELTA);
    }

    @Test
    public void GetMinTranslation_CircleAndRectTouchHorizontallyButDontOverlap_ReturnsNull() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(10, 0, 10, 10);

        assertNull(overlapper.getMinTranslation(circle, rect));
    }

    @Test
    public void GetMinTranslation_CircleAndRectTouchVerticallyButDontOverlap_ReturnsNull() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(0, 10, 10, 10);

        assertNull(overlapper.getMinTranslation(circle, rect));
    }

    @Test
    public void GetMinTranslation_CircleAndRectTouchAtCornerButDontOverlap_ReturnsNull() {
        Circle circle = new Circle(30, -30, 30);
        Rect rect = new Rect(0, 0, 2, 2);

        // put upper right corner of rect at circle center
        Vector2f rectPos = new Vector2f(29, -29);
        // move rect towards origin so its top right corner touches edge of
        // circle
        Vector2f scaler = new Vector2f(-1, 1);
        scaler.normalise();
        scaler.scale(circle.getRadius());
        rectPos.add(scaler);
        rect.setPosition(rectPos);

        assertNull(overlapper.getMinTranslation(circle, rect));
    }
}
