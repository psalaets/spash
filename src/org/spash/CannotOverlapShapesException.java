package org.spash;

/**
 * Runtime exception thrown when current setup is not equipped to find the 
 * overlap between two shapes.
 */
public class CannotOverlapShapesException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotOverlapShapesException(String message) {
        super(message);
    }
}
