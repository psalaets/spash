package org.spash.broad.hash;

import org.spash.Shape;
import org.spash.ray.Ray;

/**
 * Creates spans for shapes and rays.
 */
public interface SpanFactory {
    /**
     * Creates a span for a shape.
     * 
     * @param shape Shape to create span for
     * @return span
     */
    Span createShapeSpan(Shape shape);
    /**
     * Creates a span for a ray.
     * 
     * @param ray
     * @return span
     */
    Span createRaySpan(Ray ray);
}
