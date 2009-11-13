package org.spash.narrow;

import org.spash.Shape;
import org.spash.Translation;
import org.spash.shape.Line;

public class LineLineOverlapper implements ShapeOverlapper {
    /**
     * @return always null
     */
    public Translation getMinTranslation(Shape s1, Shape s2) {
        return null;
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Line.class == class1 && Line.class == class2;
    }

    @Override
    public String toString() {
        return "LineLine";
    }
}
