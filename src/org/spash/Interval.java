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
package org.spash;

/**
 * A range of values. Intervals begin at a start value and go up to, but do not
 * reach, their end value.
 */
public strictfp class Interval {
    private float start;
    private float end;

    /**
     * Creates an interval.
     * 
     * @param start Start of the interval
     * @param end End of the interval
     * @throws IllegalArgumentException if end < start
     */
    public Interval(float start, float end) {
        if(end < start) throw new IllegalArgumentException("end must be >= start");
        this.start = start;
        this.end = end;
    }

    public float getEnd() {
        return end;
    }

    public float getStart() {
        return start;
    }

    /**
     * Tells if this interval overlaps another interval.
     * 
     * @param other The other interval
     * @return true if the intervals overlap, false otherwise
     */
    public boolean overlaps(Interval other) {
        return distance(other) < 0f;
    }

    /**
     * Gets the distance between this and another interval.
     * 
     * @param other The other interval
     * @return distance from this to other, negative number means there is
     * overlap
     */
    public float distance(Interval other) {
        float dist1 = getStart() - other.getEnd();
        float dist2 = other.getStart() - getEnd();
        return Math.max(dist1, dist2);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Interval) {
            Interval other = (Interval)o;
            return start == other.getStart() && end == other.getEnd();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 13;
        result = (31 * result) + Float.floatToIntBits(start);
        result = (31 * result) + Float.floatToIntBits(end);
        return result;
    }
    
    @Override
    public String toString() {
        return "[" + start + ", " + end + ")";
    }
}
