/*
 * Copyright (c) 2006 Laurent Cozic
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.spash.narrow;

import java.util.List;

import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Translation;
import org.spash.Vector2f;

public strictfp abstract class AbstractShapeOverlapper {
    protected Translation findMinTranslation(Shape shape1, Shape shape2, List<ROVector2f> normals) {
        ROVector2f minNormal = null;
        float minTranslationDistance = Float.MAX_VALUE;

        for(ROVector2f normal : normals) {
            Interval interval1 = shape1.projectOntoUnit(normal);
            Interval interval2 = shape2.projectOntoUnit(normal);
            if(interval1.overlaps(interval2)) {
                if(distanceBetweenIntervals(interval1, interval2) < minTranslationDistance) {
                    minTranslationDistance = distanceBetweenIntervals(interval1, interval2);
                    minNormal = normal;
                }
            } else {
                return null;
            }
        }

        Vector2f direction = new Vector2f(minNormal);
        direction = ensureVectorSeparatesShapes(direction, shape1, shape2);
        return new Translation(direction, minTranslationDistance);
    }

    private float distanceBetweenIntervals(Interval interval1, Interval interval2) {
        return Math.abs(interval1.distance(interval2));
    }

    protected Vector2f ensureVectorSeparatesShapes(Vector2f vector, Shape shape1, Shape shape2) {
        if(vectorPushesShapesTogether(vector, shape1, shape2)) {
            return vector.negate();
        }
        return vector;
    }

    private boolean vectorPushesShapesTogether(ROVector2f vector, Shape shape1, Shape shape2) {
        Vector2f relativePosition = new Vector2f(shape1.getPosition());
        relativePosition.sub(shape2.getPosition());
        return relativePosition.dot(vector) > 0f;
    }
}
