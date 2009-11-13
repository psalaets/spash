package org.spash.narrow;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.shape.Rect;

public class RectRectOverlapper extends AbstractShapeOverlapper implements ShapeOverlapper {
    public Translation getMinTranslation(Shape shape1, Shape shape2) {
        Rect rect1 = (Rect)shape1;
        Rect rect2 = (Rect)shape2;

        Vector2f relativePosition = relativePosition(rect1, rect2);
        float sumOfHalfWidths = sumOfHalfWidths(rect1, rect2);
        float sumOfHalfHeights = sumOfHalfHeights(rect1, rect2);

        float overlapAlongX = sumOfHalfWidths - abs(relativePosition.getX());
        float overlapAlongY = sumOfHalfHeights - abs(relativePosition.getY());
        if(overlapsAlongBothAxes(overlapAlongX, overlapAlongY)) {
            Vector2f direction = shortestSeparationDirection(overlapAlongX, overlapAlongY);
            direction = ensureVectorSeparatesShapes(direction, rect1, rect2);
            return new Translation(direction, min(overlapAlongX, overlapAlongY));
        }
        return null;
    }

    private Vector2f relativePosition(Rect rect1, Rect rect2) {
        Vector2f relativePosition = new Vector2f(rect1.getPosition());
        relativePosition.sub(rect2.getPosition());
        return relativePosition;
    }

    private float sumOfHalfWidths(Rect rect1, Rect rect2) {
        return (rect1.getWidth() + rect2.getWidth()) / 2;
    }

    private float sumOfHalfHeights(Rect rect1, Rect rect2) {
        return (rect1.getHeight() + rect2.getHeight()) / 2;
    }

    private boolean overlapsAlongBothAxes(float overlapAlongX, float overlapAlongY) {
        return overlapAlongX > 0 && overlapAlongY > 0;
    }

    private Vector2f shortestSeparationDirection(float overlapAlongX, float overlapAlongY) {
        if(overlapAlongX < overlapAlongY) {
            return new Vector2f(1, 0);
        } else {
            return new Vector2f(0, 1);
        }
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Rect.class == class1 && Rect.class == class2;
    }

    @Override
    public String toString() {
        return "RectRect";
    }
}
