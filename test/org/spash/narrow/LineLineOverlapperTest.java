package org.spash.narrow;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.narrow.LineLineOverlapper;
import org.spash.shape.Line;


public class LineLineOverlapperTest {
    LineLineOverlapper overlapper;

    @Before
    public void before() {
        overlapper = new LineLineOverlapper();
    }

    @After
    public void after() {
        overlapper = null;
    }

    @Test
    public void GetMinTranslation_LinesAreDisjoint_ReturnsNull() {
        Line line1 = new Line(0, 0, 2, 0);
        Line line2 = new Line(0, 2, 2, 2);

        assertNull(overlapper.getMinTranslation(line1, line2));
    }

    @Test
    public void GetMinTranslation_LinesIntersect_ReturnsNull() {
        Line line1 = new Line(0, 0, 2, 0);
        Line line2 = new Line(1, 1, 1, -1);

        assertNull(overlapper.getMinTranslation(line1, line2));
    }
}
