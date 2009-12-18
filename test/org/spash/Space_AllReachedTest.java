package org.spash;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.ray.Ray;

public class Space_AllReachedTest {
    private Space space;
    
    @Before
    public void before() {
        BodyOverlapper overlapper = mock(BodyOverlapper.class);
        BroadPhase broadPhase = mock(BroadPhase.class);
        space = new Space(broadPhase, overlapper);        
    }

    @After
    public void after() {
        space = null;
    }
    
    @Test(expected=IllegalStateException.class)
    public void WhenNotEquippedForRays_ThrowsIllegalStateException() {
        Ray ray = new Ray(new Vector2f(), new Vector2f());
        
        space.allReached(ray);
    }
}
