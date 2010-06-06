/*
 * The MIT License
 * 
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
package org.spash.shape;

import org.spash.Interval;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.Vector2f;

public strictfp abstract class AbstractShape implements Shape {
    /** mid point of this shape */
    private Vector2f position;

    /**
     * Creates an abstract shape.
     */
    protected AbstractShape() {
        this(new Vector2f(0, 0));
    }

    /**
     * Creates an abstract shape.
     */
    protected AbstractShape(Vector2f position) {
        this.position = position;
    }

    /**
     * Projects vertices onto a unit vector.
     * 
     * @param vertices The vertices to project
     * @param unit Unit vector to project onto
     * @return Interval on unit formed by the vertices' projection
     */
    protected Interval projectVerticesOntoUnit(ROVector2f[] vertices, ROVector2f unit) {
        float dp = unit.dot(vertices[0]);
        float min = dp;
        float max = dp;

        for(int i = 1; i < vertices.length; i++) {
            dp = unit.dot(vertices[i]);
            min = Math.min(min, dp);
            max = Math.max(max, dp);
        }
        return new Interval(min, max);
    }

    public ROVector2f getPosition() {
        return position;
    }

    public void setPosition(ROVector2f pos) {
        position.set(pos);
    }

    public void moveBy(ROVector2f delta) {
        position.add(delta);
    }
}
