package org.spash.ray.intersect;

import java.util.HashMap;
import java.util.Map;

import org.spash.Body;
import org.spash.ROVector2f;
import org.spash.Shape;
import org.spash.UnsupportedShapeException;
import org.spash.ray.Ray;
import org.spash.ray.RayBodyIntersector;
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
        Shape shape = body.getShape();
        RayShapeIntersector intersector = intersectors.get(shape.getClass());
        if(intersector != null) {
            return intersector.intersect(ray, shape);
        }
        throw new UnsupportedShapeException("No intersector registered for " + body.getShape().getClass());
    }
    
    public void registerDefaults() {
        register(Circle.class, new RayCircleIntersector());
        register(Rect.class, new RayRectIntersector());
        register(Line.class, new RayLineIntersector());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Configurable Ray Body Intersector with:\n");
        for(Map.Entry<Class<? extends Shape>, RayShapeIntersector> entry : intersectors.entrySet()) {
            builder.append(entry.getKey()).append(" -> ");
            builder.append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
}
