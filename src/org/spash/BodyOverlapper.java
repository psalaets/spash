package org.spash;

/**
 * Checks if bodies are overlapping.
 */
public interface BodyOverlapper {
    /**
     * Checks if bodies are overlapping.
     * 
     * @param body1 First body
     * @param body2 Second body
     * @return minimum translation needed to separate the bodies or null if the
     * bodies are not overlapping
     * @throws CannotOverlapShapesException if we don't know how to find overlap
     * of the bodies
     */
    Translation getMinTranslation(Body body1, Body body2) throws CannotOverlapShapesException;
}
