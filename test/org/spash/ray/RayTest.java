package org.spash.ray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.spash.TestHelper.ALLOWABLE_DELTA;

import org.junit.Test;
import org.spash.Vector2f;

public class RayTest {
    private static final float SQRT_2 = (float)Math.sqrt(2);

    @Test
    public void ConstructorTwoArg_SetsStart() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f end = new Vector2f(3, 4);

        Ray ray = new Ray(start, end);

        assertEquals(new Vector2f(0, 1), ray.getStart());
    }

    @Test
    public void ConstructorTwoArg_SetsEnd() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f end = new Vector2f(3, 4);

        Ray ray = new Ray(start, end);

        assertEquals(new Vector2f(3, 4), ray.getEnd());
    }

    @Test
    public void ConstructorTwoArg_SetsDirectionX() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f end = new Vector2f(3, 4);

        Ray ray = new Ray(start, end);

        assertEquals(SQRT_2 / 2, ray.getDirection().getX(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorTwoArg_SetsDirectionY() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f end = new Vector2f(3, 4);

        Ray ray = new Ray(start, end);

        assertEquals(SQRT_2 / 2, ray.getDirection().getY(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorTwoArg_StartAndEndAreEqual_DirectionIsStraightUp() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f end = new Vector2f(0, 1);

        Ray ray = new Ray(start, end);

        assertEquals(new Vector2f(0, -1), ray.getDirection());
    }

    @Test
    public void ConstructorThreeArg_SetsStart() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f somePoint = new Vector2f(3, 4);

        Ray ray = new Ray(start, somePoint, 5);

        assertEquals(new Vector2f(0, 1), ray.getStart());
    }

    @Test
    public void ConstructorThreeArg_SetsEndX() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f somePoint = new Vector2f(3, 4);

        Ray ray = new Ray(start, somePoint, 5);

        assertEquals(5 * (SQRT_2 / 2), ray.getEnd().getX(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorThreeArg_SetsEndY() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f somePoint = new Vector2f(3, 4);

        Ray ray = new Ray(start, somePoint, 5);

        assertEquals(1 + (5 * (SQRT_2 / 2)), ray.getEnd().getY(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorThreeArg_SetsDirectionX() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f somePoint = new Vector2f(3, 4);

        Ray ray = new Ray(start, somePoint, 5);

        assertEquals(SQRT_2 / 2, ray.getDirection().getX(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorThreeArg_SetsDirectionY() {
        Vector2f start = new Vector2f(0, 1);
        Vector2f somePoint = new Vector2f(3, 4);

        Ray ray = new Ray(start, somePoint, 5);

        assertEquals(SQRT_2 / 2, ray.getDirection().getY(), ALLOWABLE_DELTA);
    }

    @Test
    public void ConstructorThreeArg_StartAndOtherPointAreEqual_DirectionIsStraightUp() {
        Vector2f origin = new Vector2f(0, 1);
        Vector2f endpoint = new Vector2f(0, 1);

        Ray ray = new Ray(origin, endpoint, 5);

        assertEquals(new Vector2f(0, -1), ray.getDirection());
    }

    @Test
    public void DirectionIsNormalized() {
        Ray ray = new Ray(new Vector2f(0, 1), new Vector2f(3, 4));

        assertEquals(1f, ray.getDirection().length(), ALLOWABLE_DELTA);
    }

    @Test
    public void GoingUpLeft_IsAbleToReportItsDirection() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(-1, -1));

        assertTrue(ray.isGoingLeft());
        assertFalse(ray.isGoingRight());
        assertTrue(ray.isGoingUp());
        assertFalse(ray.isGoingDown());
    }

    @Test
    public void GoingDownRight_IsAbleToReportItsDirection() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(1, 1));

        assertFalse(ray.isGoingLeft());
        assertTrue(ray.isGoingRight());
        assertFalse(ray.isGoingUp());
        assertTrue(ray.isGoingDown());
    }
}
