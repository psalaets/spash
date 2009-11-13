package org.spash.narrow;

import java.util.ArrayList;
import java.util.List;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.shape.Circle;
import org.spash.shape.Line;


public class LineCircleOverlapper extends AbstractShapeOverlapper implements ShapeOverlapper {
    public Translation getMinTranslation(Shape s1, Shape s2) {
        Line line = (Line)s1;
        Circle circle = (Circle)s2;

        List<ROVector2f> normals = new ArrayList<ROVector2f>(2);
        normals.add(circle.normalAlongLineToClosest(line.getVertices()));
        normals.add(line.getNormal());

        return findMinTranslation(line, circle, normals);
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Line.class == class1 && Circle.class == class2;
    }

    @Override
    public String toString() {
        return "LineCircle";
    }
}
