package org.spash.narrow;

import org.spash.Shape;
import org.spash.Translation;

public interface ShapeOverlapper {
    /**
     * Calculates the minimum translation needed to separate shapes. If
     * {@link #canHandle(Class, Class)} returns false for the classes of the
     * shape params, all bets are off - a RuntimeException will probably be 
     * thrown.
     * 
     * @return minimum translation to separate the shapes or null if shapes do
     * not overlap
     */
    Translation getMinTranslation(Shape shape1, Shape shape2);

    /**
     * Tells if this overlapper can handle certain shape combos.
     * 
     * @return true if the given combo, in the given order, can be handled
     */
    boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2);
}