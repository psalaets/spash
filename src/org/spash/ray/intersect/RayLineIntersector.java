package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.ray.Ray;
import org.spash.shape.Line;

/**
 * Does intersect checks between rays and lines. 
 */
public class RayLineIntersector extends BaseRayShapeIntersector {
    public ROVector2f intersect(Ray ray, Shape shape) {
        Line line = (Line)shape;
        return findIntersectionPoint(ray, line.getP1(), line.getP2());
    }
    
    public boolean canHandle(Class<? extends Shape> shapeType) {
        return shapeType == Line.class;
    }
}
