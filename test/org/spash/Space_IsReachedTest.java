package org.spash;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spash.ray.Ray;
import org.spash.ray.RayBodyIntersector;
import org.spash.ray.RayBroadPhase;
import org.spash.ray.RayContact;

public class Space_IsReachedTest {
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
        Body body = mock(Body.class);
        
        space.isReached(body, ray);
    }

    @Test
    public void ReturnsRayContactWhenIntersectorReturnsPoint() {
        Ray ray = new Ray(new Vector2f(), new Vector2f());
        Body body = mock(Body.class);
        
        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        RayBodyIntersector intersector = mock(RayBodyIntersector.class);
        when(intersector.intersect(ray, body)).thenReturn(new Vector2f(1, 2));
        
        space.equipForRays(rayBroadPhase, intersector);
        
        assertEquals(new RayContact(ray, body, new Vector2f(1, 2)), space.isReached(body, ray));
    }
    
    @Test
    public void ReturnsNullWhenIntersectorReturnsNullPoint() {
        Ray ray = new Ray(new Vector2f(), new Vector2f());
        Body body = mock(Body.class);
        
        RayBroadPhase rayBroadPhase = mock(RayBroadPhase.class);
        RayBodyIntersector intersector = mock(RayBodyIntersector.class);
        when(intersector.intersect(ray, body)).thenReturn(null);
        
        space.equipForRays(rayBroadPhase, intersector);
        
        assertNull(space.isReached(body, ray));
    }
}
