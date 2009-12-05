package org.spash;

import org.spash.ray.Ray;

/**
 * Finds the intersection of a ray and a body.
 */
public interface RayBodyIntersector {
    /**
     * Gets the first intersection point between a ray and a body.
     * 
     * @param ray
     * @param body
     * @return First intersection point or null if there was no intersection
     */
    ROVector2f intersect(Ray ray, Body body);
}
