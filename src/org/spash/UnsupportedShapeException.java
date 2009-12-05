package org.spash;

/**
 * Runtime exception thrown when current setup is not equipped to handle a
 * certain shape.
 */
public class UnsupportedShapeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnsupportedShapeException(String message) {
        super(message);
    }
}
