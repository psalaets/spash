package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.ray.Ray;

/**
 * Intersects rays and shapes. 
 */
public interface RayShapeIntersector {
    /**
     * Find intersection point between a ray and shape.
     * 
     * @param ray
     * @param shape
     * @return Intersection point, if any, otherwise null.
     */
    ROVector2f intersect(Ray ray, Shape shape);
    /**
     * Tells if this intersector can handle a certain shape.
     * 
     * @param shapeType
     * @return true if this intersector can handle shapeType, false otherwise
     */
    boolean canHandle(Class<? extends Shape> shapeType);
}
