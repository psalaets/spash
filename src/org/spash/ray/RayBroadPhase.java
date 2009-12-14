package org.spash.ray;

import java.util.List;

import org.spash.Body;

/**
 * Determines which bodies might be touched by a ray.
 */
public interface RayBroadPhase {
    /**
     * Gets bodies that might be touched by a ray.
     * 
     * @param ray The ray
     * @return List of bodies that could be touched by the ray
     */
    List<Body> potentialBodies(Ray ray);

    /**
     * Adds a body to this ray broad phase.
     * 
     * @param body Body to add
     */
    void addBody(Body body);

    /**
     * Clears all bodies from this ray broad phase.
     */
    void clear();
}
