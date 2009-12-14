package org.spash.narrow;

import java.util.HashMap;
import java.util.Map;

import org.spash.Body;
import org.spash.BodyOverlapper;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.UnsupportedShapeException;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

/**
 * Overlapper that can handle bodies with any combo of Shape implementations.
 */
public class ConfigurableBodyOverlapper implements BodyOverlapper {
    private Map<ShapeClassPair, ShapeOverlapper> overlappers;

    public ConfigurableBodyOverlapper() {
        overlappers = new HashMap<ShapeClassPair, ShapeOverlapper>();
    }

    /**
     * Registers a shape overlapper for checking certain shapes.  An inverse 
     * overlapper will automatically be registered.  So registering ABOverlapper
     * for shape combo A and B will also register BAOverlapper for shape combo
     * B and A.
     * 
     * @param class1 Class of first shape
     * @param class2 Class of second shape
     * @param overlapper Overlapper that can handle class1 and class2, in that
     * order
     * @throws IllegalArgumentException if overlapper can't handle shape combo
     * @see ShapeOverlapper#canHandle(Class, Class)
     */
    public void register(Class<? extends Shape> class1, Class<? extends Shape> class2, ShapeOverlapper overlapper) {
        if(class1 == null) throw new IllegalArgumentException("class1 cannot be null");
        if(class2 == null) throw new IllegalArgumentException("class2 cannot be null");
        if(overlapper == null) throw new IllegalArgumentException("overlapper cannot be null");
        if(!overlapper.canHandle(class1, class2)) {
            badClassCombo(class1, class2);
        }
        ShapeClassPair pair = new ShapeClassPair(class1, class2);
        overlappers.put(pair, overlapper);
        if(!pair.isForSameClass()) {
            overlappers.put(pair.reverse(), new InverseOverlapper(overlapper));
        }
    }

    private void badClassCombo(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        throw new IllegalArgumentException(
                "Cannot register overlapper because it cannot handle "
                        + class1 + " and " + class2 + ", see "
                        + ShapeOverlapper.class.getName() + "#canHandle");
    }

    /**
     * Registers every combo of Rect, Circle and Line with the appropriate shape
     * overlapper.
     */
    public void registerDefaults() {
        register(Rect.class, Rect.class, new RectRectOverlapper());
        register(Line.class, Rect.class, new LineRectOverlapper());
        register(Circle.class, Rect.class, new CircleRectOverlapper());
        register(Circle.class, Circle.class, new CircleCircleOverlapper());
        register(Line.class, Circle.class, new LineCircleOverlapper());
        register(Line.class, Line.class, new LineLineOverlapper());
    }

    /**
     * Tells if a ShapeOverlapper is registered for a shape combo.
     * 
     * @param class1 Shape impl class
     * @param class2 Shape impl class
     * @return true if a ShapeOverlapper is registered for the combo
     */
    public boolean hasShapeOverlapperFor(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return overlappers.containsKey(new ShapeClassPair(class1, class2));
    }

    public Translation getMinTranslation(Body body1, Body body2) throws UnsupportedShapeException {
        Shape shape1 = body1.getShape();
        Shape shape2 = body2.getShape();
        ShapeClassPair pair = new ShapeClassPair(shape1.getClass(), shape2.getClass());

        ShapeOverlapper overlapper = overlappers.get(pair);
        if(overlapper != null) {
            return overlapper.getMinTranslation(shape1, shape2);
        }
        throw new UnsupportedShapeException(
                "No ShapeOverlapper registered for "
                        + shape1.getClass().getName() + " and "
                        + shape2.getClass().getName() + ".  See "
                        + getClass().getName() + "#register");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Configurable Body Overlapper with:\n");
        for(Map.Entry<ShapeClassPair, ShapeOverlapper> entry : overlappers.entrySet()) {
            builder.append("(").append(entry.getKey()).append(")");
            builder.append(" -> ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }

    private static class ShapeClassPair {
        Class<? extends Shape> class1;
        Class<? extends Shape> class2;

        public ShapeClassPair(Class<? extends Shape> class1, Class<? extends Shape> class2) {
            this.class1 = class1;
            this.class2 = class2;
        }

        public boolean isForSameClass() {
            return class1 == class2;
        }

        public ShapeClassPair reverse() {
            return new ShapeClassPair(class2, class1);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof ShapeClassPair) {
                ShapeClassPair pair = (ShapeClassPair) obj;
                return class1 == pair.class1 && class2 == pair.class2;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = 8779;
            result = (4093 * result) + class1.hashCode();
            result = (4093 * result) + class2.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return class1.getName() + ", " + class2.getName();
        }
    }
}
