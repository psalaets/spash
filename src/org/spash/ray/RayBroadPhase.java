package org.spash.ray;

import java.util.Set;

import org.spash.Body;

/**
 * Determines which bodies might be touched by a ray.
 */
public interface RayBroadPhase {
    /**
     * Gets bodies that might be touched by a ray.
     * 
     * @param ray The ray
     * @return Bodies that could be touched by the ray
     */
    Set<Body> potentialBodies(Ray ray);

    /**
     * Adds a body to this ray broad phase.
     * 
     * @param body Body to add
     */
    void add(Body body);

    /**
     * Clears all bodies from this ray broad phase.
     */
    void clear();
}
