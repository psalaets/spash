package org.spash.narrow;

import java.util.ArrayList;
import java.util.List;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.shape.Line;
import org.spash.shape.Rect;


public class LineRectOverlapper extends AbstractShapeOverlapper implements ShapeOverlapper {
    public Translation getMinTranslation(Shape s1, Shape s2) {
        Line line = (Line)s1;
        Rect rect = (Rect)s2;

        List<ROVector2f> normals = new ArrayList<ROVector2f>(3);
        normals.add(line.getNormal());
        normals.add(new Vector2f(1, 0));
        normals.add(new Vector2f(0, 1));

        return findMinTranslation(line, rect, normals);
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Line.class == class1 && Rect.class == class2;
    }

    @Override
    public String toString() {
        return "LineRect";
    }
}
