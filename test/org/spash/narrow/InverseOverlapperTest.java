package org.spash.narrow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.narrow.InverseOverlapper;
import org.spash.narrow.ShapeOverlapper;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class InverseOverlapperTest {
    @Test(expected = IllegalArgumentException.class)
    public void SourceOverlapperCannotBeNull() {
        new InverseOverlapper(null);
    }

    @Test
    public void WhenSourceReturnsNullMinTranslation_ReturnsNullMinTranslation() {
        ShapeOverlapper source = mock(ShapeOverlapper.class);
        when(source.getMinTranslation(isA(Shape.class), isA(Shape.class))).thenReturn(null);
        InverseOverlapper inverse = new InverseOverlapper(source);

        assertNull(inverse.getMinTranslation(mock(Shape.class), mock(Shape.class)));
    }

    @Test
    public void WhenSourceReturnsTranslation_ReturnsTranslationInOppositeDirection() {
        ShapeOverlapper source = mock(ShapeOverlapper.class);
        Translation sourceTranslation = new Translation(new Vector2f(10, 5), 5);
        when(source.getMinTranslation(isA(Shape.class), isA(Shape.class))).thenReturn(sourceTranslation);
        InverseOverlapper inverse = new InverseOverlapper(source);

        Translation actual = inverse.getMinTranslation(mock(Shape.class), mock(Shape.class));

        assertEquals(new Translation(new Vector2f(-10, -5), 5), actual);
    }

    @Test
    public void CanHandleOppositeShapeComboOfSourceOverlapper() {
        ShapeOverlapper source = mock(ShapeOverlapper.class);
        when(source.canHandle(Rect.class, Circle.class)).thenReturn(true);
        InverseOverlapper inverse = new InverseOverlapper(source);

        assertTrue(inverse.canHandle(Circle.class, Rect.class));
    }

    @Test
    public void CannotHandleSameShapeComboAsSourceOverlapper() {
        ShapeOverlapper source = mock(ShapeOverlapper.class);
        when(source.canHandle(Rect.class, Circle.class)).thenReturn(true);
        InverseOverlapper inverse = new InverseOverlapper(source);

        assertFalse(inverse.canHandle(Rect.class, Circle.class));
    }

    @Test
    public void CannotHandleShapeComboUnrelatedToSourceOverlapper() {
        ShapeOverlapper source = mock(ShapeOverlapper.class);
        //source handles rect/circle
        when(source.canHandle(Rect.class, Circle.class)).thenReturn(true);
        InverseOverlapper inverse = new InverseOverlapper(source);

        //rect/line is not rect/circle nor circle/rect
        assertFalse(inverse.canHandle(Rect.class, Line.class));
    }
}
