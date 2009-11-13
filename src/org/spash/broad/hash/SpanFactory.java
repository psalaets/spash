package org.spash.broad.hash;

import org.spash.Shape;

/**
 * Creates spans of grid coordinates.
 */
public interface SpanFactory {
    /**
     * Creates a span.
     * 
     * @param shape Shape to create span for
     * @return span
     */
    Span createSpanFor(Shape shape);
}
