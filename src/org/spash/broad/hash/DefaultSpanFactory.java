package org.spash.broad.hash;

import org.spash.Shape;
import org.spash.shape.Line;

/**
 * Span factory that has special handling for lines and uses rectangular spans
 * for all other shapes.
 */
public class DefaultSpanFactory implements SpanFactory {
    public Span createSpanFor(Shape shape) {
        if(shape instanceof Line) {
            return new LineSpan((Line)shape);
        }
        return new RectSpan(shape);
    }
}
