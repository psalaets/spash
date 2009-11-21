package org.spash.broad.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.spash.broad.BroadTestHelper.set;

import org.junit.Test;
import org.spash.Body;
import org.spash.Pair;


public class CellTest {
    @Test
    public void StartsOffWithNoPairs() {
        Cell cell = new Cell();
        
        assertEquals(0, cell.getPairs().size());
        assertFalse("cell should say it doesn't have pairs", cell.hasPairs());
    }
    
    @Test
    public void AfterAddingOneBody_HasNoPairs() {
        Cell cell = new Cell();
        Body body = mock(Body.class);
        
        cell.add(body);
 
        assertEquals(0, cell.getPairs().size());
        assertFalse("cell should say it doesn't have pairs", cell.hasPairs());
    }
    
    @Test
    public void AfterAddingTwoBodies_HasOnePair() {
        Cell cell = new Cell();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        
        cell.add(body1);
        cell.add(body2);
 
        assertEquals(set(new Pair(body1, body2)), set(cell.getPairs()));
        assertTrue("cell should say it has pairs", cell.hasPairs());
    }
    
    @Test
    public void AfterAddingThreeBodies_HasThreePairs() {
        Cell cell = new Cell();
        Body body1 = mock(Body.class);
        Body body2 = mock(Body.class);
        Body body3 = mock(Body.class);
        
        cell.add(body1);
        cell.add(body2);
        cell.add(body3);
 
        assertEquals(
                set(new Pair(body1, body2), new Pair(body1, body3), new Pair(body2, body3)),
                set(cell.getPairs()));
        assertTrue("cell should say it has pairs", cell.hasPairs());
    }
}
