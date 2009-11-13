package org.spash;

/**
 * An unordered pair of bodies.
 */
public class Pair {
    private Body bodyA;
    private Body bodyB;

    /**
     * Creates a pair.
     * 
     * @param bodyA Body A, cannot be null
     * @param bodyB Body B, cannot be null
     */
    public Pair(Body bodyA, Body bodyB) {
        if(bodyA == null) throw new IllegalArgumentException("bodyA cannot be null");
        if(bodyB == null) throw new IllegalArgumentException("bodyB cannot be null");
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }

    /**
     * @return Body A, never null
     */
    public Body getBodyA() {
        return bodyA;
    }

    /**
     * @return Body B, never null
     */
    public Body getBodyB() {
        return bodyB;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair other = (Pair) o;
            return (bodyA == other.bodyA && bodyB == other.bodyB)
                    || (bodyA == other.bodyB && bodyB == other.bodyA);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return bodyA.hashCode() * bodyB.hashCode();
    }

    @Override
    public String toString() {
        return "[" + bodyA + "," + bodyB + "]";
    }
}
