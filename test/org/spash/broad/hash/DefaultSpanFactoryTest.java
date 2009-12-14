package org.spash.broad.hash;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.spash.Shape;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class DefaultSpanFactoryTest {
    @Test
    public void GivenRectReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Rect rect = new Rect(0, 0, 10, 10);

        assertTrue(factory.createShapeSpan(rect) instanceof RectSpan);
    }

    @Test
    public void GivenCircleReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Circle circle = new Circle(0, 0, 5);

        assertTrue(factory.createShapeSpan(circle) instanceof RectSpan);
    }

    @Test
    public void GivenLineReturnsLineSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Line line = new Line(1, 2, 3, 4);

        assertTrue(factory.createShapeSpan(line) instanceof LineSpan);
    }
    
    @Test
    public void GivenAnyOtherShapeReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Shape shape = mock(Shape.class);

        assertTrue(factory.createShapeSpan(shape) instanceof RectSpan);
    }
    
    @Test
    public void GivenRayReturnsLineSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Ray ray = new Ray(new Vector2f(), new Vector2f());

        assertTrue(factory.createRaySpan(ray) instanceof LineSpan);
    }
}
