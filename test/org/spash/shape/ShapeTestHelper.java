package org.spash.shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spash.ROVector2f;


public class ShapeTestHelper {
    /**
     * Turn some vectors into a list of vectors. 
     */
    public static List<ROVector2f> list(ROVector2f... vectors) {
        return Arrays.asList(vectors);
    }
    
    /**
     * Assert equality of vector collections using bag semantics. 
     * 
     * @param expected
     * @param actual
     */
    public static void assertVectorBagsEqual(Collection<ROVector2f> expected, Collection<ROVector2f> actual) {
        assertNotNull("actual should not be null", actual);
        assertEquals("bag sizes are not equal", expected.size(), actual.size());
        
        //for each vector in expected, there should be an equal one in actual
        List<ROVector2f> actualCopy = new ArrayList<ROVector2f>(actual);
        for(ROVector2f vector : expected) {
            actualCopy.remove(vector);
        }
        assertTrue("bags differed\nexpected: "+expected+"\nbut was: "+actual, actualCopy.isEmpty());
    }
}
