package org.spash.ray.intersect;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.spash.TestHelper.bodyWith;

import org.junit.Test;
import org.spash.UnsupportedShapeException;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.ray.intersect.ConfigurableRayBodyIntersector;
import org.spash.ray.intersect.RayShapeIntersector;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

public class ConfigurableRayBodyIntersectorTest {
    @Test(expected=UnsupportedShapeException.class)
    public void CannotIntersectRayWithUnregisteredShape() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        Ray ray = new Ray(new Vector2f(), new Vector2f());

        intersector.intersect(ray, bodyWith(new Circle(0, 0, 3)));
    }
    
    @Test
    public void IntersectorsCanBeRegisteredForShapeTypes() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        RayShapeIntersector shapeIntersector = mock(RayShapeIntersector.class);
        when(shapeIntersector.canHandle(Rect.class)).thenReturn(true);
        
        intersector.register(Rect.class, shapeIntersector);
        
        assertTrue(intersector.hasIntersectorFor(Rect.class));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void CannotRegisterIntersectorForNullShapeType() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        RayShapeIntersector shapeIntersector = mock(RayShapeIntersector.class);

        intersector.register(null, shapeIntersector);
    }

    @Test(expected=IllegalArgumentException.class)
    public void CannotRegisterIntersectorForShapeTypeItCannotHandle() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        RayShapeIntersector shapeIntersector = mock(RayShapeIntersector.class);
        when(shapeIntersector.canHandle(Rect.class)).thenReturn(false);

        intersector.register(Rect.class, shapeIntersector);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void CannotRegisterNullIntersector() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        
        intersector.register(Rect.class, null);
    }
    
    @Test
    public void RegisterDefaults_RegistersEverySpashShape() {
        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        
        intersector.registerDefaults();
        
        assertTrue(intersector.hasIntersectorFor(Rect.class));
        assertTrue(intersector.hasIntersectorFor(Circle.class));
        assertTrue(intersector.hasIntersectorFor(Line.class));
    }
}
