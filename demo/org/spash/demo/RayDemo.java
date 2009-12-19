package org.spash.demo;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.spash.Body;
import org.spash.DefaultBody;
import org.spash.Vector2f;
import org.spash.ray.Ray;
import org.spash.ray.RayContact;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

/**
 * Shows ray contacts as small circles. 
 */
public class RayDemo extends AbstractDemo {
    List<RayContact> contacts;
    Ray ray;
    Vector2f start;
    Vector2f end;
    List<Body> bodies;
    
    protected RayDemo() {
        super("Ray demo");
    }

    @Override
    protected void init() {
        start = new Vector2f(100, 100);
        end = new Vector2f(250, 250);
        
        bodies = new ArrayList<Body>();
        bodies.add(new DefaultBody(new Circle(200, 200, 10)));
        bodies.add(new DefaultBody(new Circle(300, 300, 40)));
        bodies.add(new DefaultBody(new Rect(150, 250, 40, 60)));
        bodies.add(new DefaultBody(new Line(250, 50, 250, 260)));
    }

    @Override
    protected void draw(Graphics2D g) {
        drawRay(g, ray);

        for(RayContact contact : contacts) {
            drawCircle(g, (int)contact.getLocation().getX(), (int)contact.getLocation().getY(), 5);
        }
        
        for(Body body : bodies) {
            drawBody(g, body);
        }
        
        g.drawString("Arrow keys - move ray end", 15, 410);
    }

    @Override
    protected void update() {
        getSpace().addBodies(bodies);
        
        if(isPressed(KeyEvent.VK_UP)) {
            end.add(new Vector2f(0, -1));
        } else if(isPressed(KeyEvent.VK_DOWN)) {
            end.add(new Vector2f(0, 1));
        }
        if(isPressed(KeyEvent.VK_LEFT)) {
            end.add(new Vector2f(-1, 0));
        } else if(isPressed(KeyEvent.VK_RIGHT)) {
            end.add(new Vector2f(1, 0));
        }
        ray = new Ray(start, end);
        
        contacts = getSpace().allReached(ray);
        
        getSpace().clearBodies();
    }

    public static void main(String[] args) {
        RayDemo demo = new RayDemo();
        demo.start();
    }
}
