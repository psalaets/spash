package org.spash.narrow;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.spash.TestHelper.bodyWith;

import org.junit.Test;
import org.spash.Shape;
import org.spash.UnsupportedShapeException;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class ConfigurableBodyOverlapperTest {
    @Test(expected = UnsupportedShapeException.class)
    public void GetMinTranslation_GivenBodiesWithShapesThatAreNotRegistered_ThrowsUnsupportedShapeException() {
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();

        overlapper.getMinTranslation(bodyWith(new Circle(0, 0, 1)), bodyWith(new Circle(0, 0, 1)));
    }

    @Test
    public void Register_RegistersShapeComboWithShapeOverlapper() {
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();
        ShapeOverlapper shapeOverlapper = mock(ShapeOverlapper.class);
        when(shapeOverlapper.canHandle(Rect.class, Circle.class)).thenReturn(true);
        overlapper.register(Rect.class, Circle.class, shapeOverlapper);

        assertTrue(overlapper.hasShapeOverlapperFor(Rect.class, Circle.class));
    }

    @Test
    public void Register_AutomaticallyRegistersOppositeShapeComboWithShapeOverlapper() {
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();
        ShapeOverlapper shapeOverlapper = mock(ShapeOverlapper.class);
        when(shapeOverlapper.canHandle(Rect.class, Circle.class)).thenReturn(true);
        overlapper.register(Rect.class, Circle.class, shapeOverlapper);

        assertTrue(overlapper.hasShapeOverlapperFor(Circle.class, Rect.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Register_Class1IsNull_ThrowsIllegalArgumentException() {
        ShapeOverlapper shapeOverlapper = mock(ShapeOverlapper.class);
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();

        overlapper.register(null, Shape.class, shapeOverlapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Register_Class2IsNull_ThrowsIllegalArgumentException() {
        ShapeOverlapper shapeOverlapper = mock(ShapeOverlapper.class);
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();

        overlapper.register(Shape.class, null, shapeOverlapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Register_OverlapperIsNull_ThrowsIllegalArgumentException() {
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();

        overlapper.register(Shape.class, Shape.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Register_OverlapperCannotHandleRegisteredClassCombo_ThrowsIllegalArgumentException() {
        ShapeOverlapper shapeOverlapper = mock(ShapeOverlapper.class);
        when(shapeOverlapper.canHandle(Shape.class, Shape.class)).thenReturn(false);
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();

        overlapper.register(Shape.class, Shape.class, shapeOverlapper);
    }

    @Test
    public void RegisterDefaults_RegistersAllCombosOfDefaultShapeImpls() {
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();
        overlapper.registerDefaults();

        assertTrue(overlapper.hasShapeOverlapperFor(Rect.class, Circle.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Rect.class, Rect.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Rect.class, Line.class));

        assertTrue(overlapper.hasShapeOverlapperFor(Circle.class, Circle.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Circle.class, Rect.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Circle.class, Line.class));

        assertTrue(overlapper.hasShapeOverlapperFor(Line.class, Rect.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Line.class, Circle.class));
        assertTrue(overlapper.hasShapeOverlapperFor(Line.class, Line.class));
    }
}
