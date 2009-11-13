package org.spash.narrow;

import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;
import org.spash.shape.Circle;

public strictfp class CircleCircleOverlapper implements ShapeOverlapper {
    public Translation getMinTranslation(Shape s1, Shape s2) {
        Circle circle1 = (Circle)s1;
        Circle circle2 = (Circle)s2;

        ROVector2f position1 = circle1.getPosition();
        ROVector2f position2 = circle2.getPosition();

        float distance = position1.distance(position2);
        float sumOfRadii = circle1.getRadius() + circle2.getRadius();
        if(distance < sumOfRadii) {
            Vector2f relativePosition = new Vector2f(position2);
            relativePosition.sub(position1);
            relativePosition.normalise();
            return new Translation(relativePosition, Math.abs(distance - sumOfRadii));
        }
        return null;
    }

    public boolean canHandle(Class<? extends Shape> class1, Class<? extends Shape> class2) {
        return Circle.class == class1 && Circle.class == class2;
    }

    @Override
    public String toString() {
        return "CircleCircle";
    }
}
