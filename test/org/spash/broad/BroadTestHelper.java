package org.spash.broad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spash.broad.hash.GridCoordinate;


/**
 * Test helper methods. 
 */
public class BroadTestHelper {
    /**
     * Turns some items into a set.
     */
    public static <T> Set<T> set(T... items) {
        return set(Arrays.asList(items));
    }

    /**
     * Turns some items into a set.
     */
    public static <T> Set<T> set(Collection<T> items) {
        return new HashSet<T>(items);
    }
    
    /**
     * Turns some grid coordinates into a list. 
     */
    public static List<GridCoordinate> coords(GridCoordinate... coords) {
        return Arrays.asList(coords);
    }
    
    /**
     * Assert equality of coordinate collections using bag semantics. 
     * 
     * @param expected
     * @param actual
     */
    public static void assertCoordinateBagsEqual(Collection<GridCoordinate> expected, Collection<GridCoordinate> actual) {
        assertNotNull("actual should not be null", actual);
        assertEquals("bag sizes are not equal", expected.size(), actual.size());
        
        //for each coord in expected, there should be an equal one in actual
        List<GridCoordinate> actualCopy = new ArrayList<GridCoordinate>(actual);
        for(GridCoordinate coord : expected) {
            actualCopy.remove(coord);
        }
        assertTrue("bags differed\nexpected: "+expected+"\nbut was: "+actual, actualCopy.isEmpty());
    }
}
