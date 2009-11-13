package org.spash;

/**
 * Info about two bodies overlapping eachother.
 */
public class OverlapEvent {
    private Body bodyA;
    private Body bodyB;
    private Translation minTranslation;

    /**
     * Creates an overlap event.
     * 
     * @param pair Pair of overlapping bodies
     * @param minTranslation
     */
    public OverlapEvent(Pair pair, Translation minTranslation) {
        this.bodyA = pair.getBodyA();
        this.bodyB = pair.getBodyB();
        this.minTranslation = minTranslation;
    }

    public Body getBodyA() {
        return bodyA;
    }

    public Body getBodyB() {
        return bodyB;
    }

    /**
     * Gets the minimum translation that separates the bodies involved in this
     * overlap.
     */
    public Translation getMinTranslation() {
        return minTranslation;
    }

    @Override
    public String toString() {
        return "[Overlap with " + bodyA + " and " + bodyB + ", minTranslation=" + minTranslation + "]";
    }
}
