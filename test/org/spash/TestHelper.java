package org.spash;

import org.spash.Body;
import org.spash.DefaultBody;
import org.spash.Shape;

public class TestHelper {
    /** delta for float comparisons */
    public static final float ALLOWABLE_DELTA = 0.00001f;

    /**
     * Creates a new body.
     * 
     * @param shape Shape for the body.
     */
    public static Body bodyWith(Shape shape) {
        DefaultBody body = new DefaultBody(shape);
        return body;
    }
}
