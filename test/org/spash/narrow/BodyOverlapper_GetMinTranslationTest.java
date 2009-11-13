package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.spash.TestHelper.bodyWith;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.spash.BodyOverlapper;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.ConfigurableBodyOverlapper;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;


/**
 * Parameterized test that runs shape vs shape tests on body overlapper impls.
 */
@RunWith(Parameterized.class)
public class BodyOverlapper_GetMinTranslationTest {
    private BodyOverlapper overlapper;

    @Parameters
    public static Collection overlappers() {
        ConfigurableBodyOverlapper configurable = new ConfigurableBodyOverlapper();
        configurable.registerDefaults();

        //add overlappers to this array to get them run through the tests
        return Arrays.asList(new Object[][] {
            { configurable }
        });
    }

    public BodyOverlapper_GetMinTranslationTest(BodyOverlapper overlapper) {
        this.overlapper = overlapper;
    }

    @Test
    public void CircleCircle_ReturnsMinTranslation() {
        Circle circle1 = new Circle(0, 0, 5);
        Circle circle2 = new Circle(7.5f, 0, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(circle1), bodyWith(circle2));
        
        assertEquals(failMessage(), new Translation(new Vector2f(1, 0), 2.5f), actual);
    }

    @Test
    public void CircleCircle_ParamsReversed_ReturnsOppositeTranslation() {
        Circle circle1 = new Circle(0, 0, 5);
        Circle circle2 = new Circle(7.5f, 0, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(circle2), bodyWith(circle1));
        
        assertEquals(failMessage(), new Translation(new Vector2f(-1, 0), 2.5f), actual);
    }

    @Test
    public void CircleLine_ReturnsMinTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Line line = new Line(4, 0, 4, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(circle), bodyWith(line));
        
        assertEquals(failMessage(), new Translation(new Vector2f(1, 0), 1f), actual);
    }

    @Test
    public void LineCircle_ReturnsOppositeTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Line line = new Line(4, 0, 4, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(line), bodyWith(circle));
        
        assertEquals(failMessage(), new Translation(new Vector2f(-1, 0), 1f), actual);
    }

    @Test
    public void CircleRect_ReturnsMinTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(5, 0, 5, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(circle), bodyWith(rect));
        
        assertEquals(failMessage(), new Translation(new Vector2f(1, 0), 2.5f), actual);
    }

    @Test
    public void RectCircle_ReturnsOppositeTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Rect rect = new Rect(5, 0, 5, 5);

        Translation actual = overlapper.getMinTranslation(bodyWith(rect), bodyWith(circle));
        
        assertEquals(failMessage(), new Translation(new Vector2f(-1, 0), 2.5f), actual);
    }

    @Test
    public void LinesAreDisjoint_ReturnsNull() {
        Line line1 = new Line(0, 0, 2, 0);
        Line line2 = new Line(0, 2, 2, 2);

        assertNull(failMessage(), overlapper.getMinTranslation(bodyWith(line1), bodyWith(line2)));
    }

    @Test
    public void LinesIntersect_ReturnsNull() {
        Line line1 = new Line(0, 0, 2, 0);
        Line line2 = new Line(1, 1, 1, -1);

        assertNull(failMessage(), overlapper.getMinTranslation(bodyWith(line1), bodyWith(line2)));
    }

    @Test
    public void LineRect_ReturnsMinTranslation() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(4, -10, 4, 10);

        Translation actual = overlapper.getMinTranslation(bodyWith(line), bodyWith(rect));
        
        assertEquals(failMessage(), new Translation(new Vector2f(-1, 0), 1f), actual);
    }

    @Test
    public void RectLine_ReturnsOppositeTranslation() {
        Rect rect = new Rect(0, 0, 10, 10);
        Line line = new Line(4, -10, 4, 10);

        Translation actual = overlapper.getMinTranslation(bodyWith(rect), bodyWith(line));
        
        assertEquals(failMessage(), new Translation(new Vector2f(1, 0), 1f), actual);
    }

    @Test
    public void RectRect_ReturnsMinTranslation() {
        Rect rect1 = new Rect(0, 0, 20, 20);
        Rect rect2 = new Rect(15, 0, 20, 20);

        Translation actual = overlapper.getMinTranslation(bodyWith(rect1), bodyWith(rect2));
        
        assertEquals(failMessage(), new Translation(new Vector2f(1, 0), 5f), actual);
    }

    @Test
    public void RectRect_ParamsReversed_ReturnsOppositeTranslation() {
        Rect rect1 = new Rect(0, 0, 20, 20);
        Rect rect2 = new Rect(15, 0, 20, 20);

        Translation actual = overlapper.getMinTranslation(bodyWith(rect2), bodyWith(rect1));
        
        assertEquals(failMessage(), new Translation(new Vector2f(-1, 0), 5f), actual);
    }

    private String failMessage() {
        return "from " + overlapper.getClass().getName() + "\n";
    }
}
