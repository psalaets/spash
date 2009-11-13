package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.LineCircleOverlapper;
import org.spash.shape.Circle;
import org.spash.shape.Line;


public class LineCircleOverlapperTest {
    LineCircleOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new LineCircleOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_LineNormalTranslation() {
        Circle circle = new Circle(0, 0, 5);
        Line line = new Line(4, 0, 4, 5);

        assertEquals(new Translation(new Vector2f(-1, 0), 1f), overlapper.getMinTranslation(line, circle));
    }

    @Test
    public void GetMinTranslation_ClosestVertexTranslation() {
        Circle circle = new Circle(-4, 0, 5);
        Line line = new Line(0, 0, 4, 0);

        assertEquals(new Translation(new Vector2f(-1, 0), 1f), overlapper.getMinTranslation(line, circle));
    }

    @Test
    public void GetMinTranslation_LineAndCircleAreDisjoint_ReturnsNull() {
        Circle circle = new Circle(0, 0, 5);
        Line line = new Line(6, 0, 6, 5);

        assertNull(overlapper.getMinTranslation(line, circle));
    }

    @Test
    public void GetMinTranslation_LineAndCircleAreTouchingButDontOverlap_ReturnsNull() {
        Circle circle = new Circle(0, 0, 5);
        Line line = new Line(5, -5, 5, 5);

        assertNull(overlapper.getMinTranslation(line, circle));
    }
}
