package org.spash.broad.hash;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.spash.Shape;
import org.spash.broad.hash.DefaultSpanFactory;
import org.spash.broad.hash.LineSpan;
import org.spash.broad.hash.RectSpan;
import org.spash.broad.hash.SpanFactory;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class DefaultSpanFactoryTest {
    @Test
    public void GivenRectReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Rect rect = new Rect(0, 0, 10, 10);

        assertTrue(factory.createSpanFor(rect) instanceof RectSpan);
    }

    @Test
    public void GivenCircleReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Circle circle = new Circle(0, 0, 5);

        assertTrue(factory.createSpanFor(circle) instanceof RectSpan);
    }

    @Test
    public void GivenLineReturnsLineSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Line line = new Line(1, 2, 3, 4);

        assertTrue(factory.createSpanFor(line) instanceof LineSpan);
    }
    
    @Test
    public void GivenAnythingElseReturnsRectSpan() {
        SpanFactory factory = new DefaultSpanFactory();
        Shape shape = mock(Shape.class);

        assertTrue(factory.createSpanFor(shape) instanceof RectSpan);
    }
}
