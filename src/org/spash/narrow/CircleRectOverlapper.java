package org.spash.narrow;

import java.util.ArrayList;
import java.util.List;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.shape.Circle;
import org.spash.shape.Rect;

public class CircleRectOverlapper extends AbstractShapeOverlapper implements ShapeOverlapper {
    public Translation getMinTranslation(Shape s1, Shape s2) {
        Circle circle = (Circle)s1;
        Rect rect = (Rect)s2;

        List<ROVector2f> normals = new ArrayList<ROVector2f>(3);
        normals.add(new Vector2f(0, 1));
        normals.add(new Vector2f(1, 0));
        normals.add(circle.normalAlongLineToClosest(rect.getVertices()));

        return findMinTranslation(circle, rect, normals);
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Circle.class == class1 && Rect.class == class2;
    }

    @Override
    public String toString() {
        return "CircleRect";
    }
}
