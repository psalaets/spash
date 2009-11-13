package org.spash;

/**
 * Direction and a distance.
 */
public class Translation {
    private Vector2f direction;
    private float distance;

    /**
     * Creates a translation.
     * 
     * @param direction Direction of the translation, should be non-null, with
     * length 1 or really close
     * @param distance Distance of the translation, cannot be negative
     */
    public Translation(ROVector2f direction, float distance) {
        if(direction == null) throw new IllegalArgumentException("direction cannot be null");
        if(distance < 0f) throw new IllegalArgumentException("distance cannot be negative");
        this.direction = new Vector2f(direction);
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    public ROVector2f getDirection() {
        return direction;
    }

    public Vector2f toVector() {
        Vector2f vector = new Vector2f(direction);
        vector.scale(distance);
        return vector;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Translation) {
            Translation other = (Translation)object;
            return direction.equals(other.getDirection()) && distance == other.getDistance();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = (79 * result) + direction.hashCode();
        result = (79 * result) + Float.floatToIntBits(distance);
        return result;
    }

    @Override
    public String toString() {
        return "[Translation dir=" + direction + ", dist=" + distance + "]";
    }

    /**
     * Creates new translation in the opposite direction.
     */
    public Translation flip() {
        return new Translation(direction.negate(), distance);
    }
}
