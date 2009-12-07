package org.spash.ray;

import org.spash.Body;
import org.spash.ROVector2f;
import org.spash.Vector2f;

/**
 * Info about a ray hitting a body.
 */
public class RayContact {
    private Body body;
    private ROVector2f location;
    private Ray ray;

    /**
     * Creates a contact.
     * 
     * @param ray The ray
     * @param body The body
     * @param location Contact location
     */
    public RayContact(Ray ray, Body body, ROVector2f location) {
        this.ray = ray;
        this.body = body;
        this.location = new Vector2f(location);
    }

    /**
     * Gets the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Gets the contact location
     */
    public ROVector2f getLocation() {
        return location;
    }

    /**
     * Gets the ray
     */
    public Ray getRay() {
        return ray;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RayContact) {
            RayContact other = (RayContact) o;
            return location.equals(other.getLocation())
                    && body.equals(other.getBody())
                    && ray.equals(other.getRay());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = (37 * result) + body.hashCode();
        result = (37 * result) + ray.hashCode();
        result = (37 * result) + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[RayContact " + body + " hit at (" + location.getX() + "," + location.getY() + ")]";
    }
}
