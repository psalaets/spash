package org.spash.narrow;

import org.spash.Shape;
import org.spash.Translation;

/**
 * Finds overlaps for the opposite shape combo of an overlapper.
 */
public class InverseOverlapper implements ShapeOverlapper {
    private ShapeOverlapper source;

    public InverseOverlapper(ShapeOverlapper source) {
        if(source == null) throw new IllegalArgumentException("source overlapper cannot be null");
        this.source = source;
    }

    public Translation getMinTranslation(Shape s1, Shape s2) {
        Translation translation = source.getMinTranslation(s2, s1);
        if(translation != null) {
            return translation.flip();
        }
        return null;
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return source.canHandle(class2, class1);
    }

    @Override
    public String toString() {
        return "Inverse(" + source + ")";
    }
}
