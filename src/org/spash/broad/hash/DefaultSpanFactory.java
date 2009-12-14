package org.spash.broad.hash;

import org.spash.Shape;
import org.spash.ray.Ray;
import org.spash.shape.Line;

/**
 * Span factory that has special handling for rays and lines and uses
 * rectangular spans for all other shapes.
 */
public class DefaultSpanFactory implements SpanFactory {
    public Span createShapeSpan(Shape shape) {
        if(shape instanceof Line) {
            Line line = (Line)shape;
            return new LineSpan(line.getP1(), line.getP2());
        }
        return new RectSpan(shape);
    }

    public Span createRaySpan(Ray ray) {
        return new LineSpan(ray.getStart(), ray.getEnd());
    }
}
