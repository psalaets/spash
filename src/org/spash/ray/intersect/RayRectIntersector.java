package org.spash.ray.intersect;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.ray.Ray;
import org.spash.shape.Rect;

/**
 * Does intersection checks between rays and rects.
 */
public class RayRectIntersector extends BaseRayShapeIntersector {
    public ROVector2f intersect(Ray ray, Shape shape) {
        Rect rect = (Rect)shape;
        if(rect.contains(ray.getStart())) {
            return ray.getStart();
        } else {
            //check for ray intersect against all 4 sides
            ROVector2f[] v = rect.getVertices();
            ROVector2f top = findIntersectionPoint(ray, v[0], v[1]);
            ROVector2f right = findIntersectionPoint(ray, v[1], v[2]);
            ROVector2f bottom = findIntersectionPoint(ray, v[2], v[3]);
            ROVector2f left = findIntersectionPoint(ray, v[3], v[0]);
            //and return whatever is closest intersect point, if any
            return ray.getStart().closest(top, right, bottom, left);
        }
    }
    
    public boolean canHandle(Class<? extends Shape> shapeType) {
        return shapeType == Rect.class;
    }
}
