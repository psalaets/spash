package org.spash.intersect;

import java.util.HashMap;
import java.util.Map;

import org.spash.Body;
import org.spash.ROVector2f;
import org.spash.RayBodyIntersector;
import org.spash.Shape;
import org.spash.UnsupportedShapeException;
import org.spash.ray.Ray;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

public class ConfigurableRayBodyIntersector implements RayBodyIntersector {
    private Map<Class<? extends Shape>, RayShapeIntersector> intersectors;
    
    public ConfigurableRayBodyIntersector() {
        intersectors = new HashMap<Class<? extends Shape>, RayShapeIntersector>();
    }
    
    /**
     * Sets up an intersector to handle ray checks for a shape.
     * 
     * @param shapeType
     * @param intersector
     */
    public void register(Class<? extends Shape> shapeType, RayShapeIntersector intersector) {
        if(shapeType == null) throw new IllegalArgumentException("shapeType cannot be null");
        if(intersector == null) throw new IllegalArgumentException("intersector cannot be null");
        if(!intersector.canHandle(shapeType)) {
            throw new IllegalArgumentException("Cannot register intersector because it cannot handle " + shapeType);
        }
        intersectors.put(shapeType, intersector);
    }
    
    public boolean hasIntersectorFor(Class<? extends Shape> shapeType) {
        return intersectors.containsKey(shapeType);
    }
    
    public ROVector2f intersect(Ray ray, Body body) {
        throw new UnsupportedShapeException("No intersector registered for " + body.getShape().getClass());
    }
    
    public void registerDefaults() {
        register(Circle.class, new RayCircleIntersector());
        register(Rect.class, new RayRectIntersector());
        register(Line.class, new RayLineIntersector());
    }
}
