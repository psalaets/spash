package org.spash;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

/**
 * Shows some overlaps checks and what pair filters do. 
 */
public class OverlapDemo extends AbstractDemo {
    Body circle;
    List<Body> bodies;
    boolean filterIn;
    PairFilter filter;
    
    protected OverlapDemo() {
        super("Overlap demo");
    }

    @Override
    protected void init() {
        circle = new DefaultBody(new Circle(200, 200, 10));
        
        bodies = new ArrayList<Body>();
        bodies.add(circle);
        bodies.add(new DefaultBody(new Rect(250, 200, 20, 20)));
        bodies.add(new DefaultBody(new Rect(200, 150, 20, 20)));
        bodies.add(new DefaultBody(new Rect(200, 250, 20, 20)));
        bodies.add(new DefaultBody(new Rect(150, 200, 20, 20)));
        bodies.add(new DefaultBody(new Line(325, 150, 325, 250)));
        bodies.add(new DefaultBody(new Line(75, 150, 75, 250)));
        
        getSpace().addOverlapListener(new PlayerPushesAllOthers(circle));
        
        filter = circlesDoNotOverlapAnything();
    }

    private PairFilter circlesDoNotOverlapAnything() {
        return new PairFilter() {
            public boolean shouldSkipNarrowPhase(Pair pair) {
                Body bodyA = pair.getBodyA();
                Body bodyB = pair.getBodyB();
                
                return bodyA.getShape() instanceof Circle || bodyB.getShape() instanceof Circle;
            }
        };
    }

    @Override
    public void keyHit(int keyCode) {
        if(keyCode == KeyEvent.VK_A && !filterIn) {
            filterIn = true;
            getSpace().addPairFilter(filter);
            
        } else if(keyCode == KeyEvent.VK_S && filterIn) {
            filterIn = false;
            getSpace().removePairFilter(filter);
        }
    }
    
    @Override
    protected void draw(Graphics2D g) {
        for(Body body : bodies) {
            drawBody(g, body);
        }
        
        if(filterIn) {
            g.drawString("Circle doesn't hit anything", 15, 330);    
        } else {
            g.drawString("Circle hits stuff", 15, 330);
        }
        
        g.drawString("A - Add pair filter", 15, 370);
        g.drawString("S - Remove pair filter", 15, 390);
        g.drawString("Arrow keys - move circle", 15, 410);
    }

    @Override
    protected void update() {
        getSpace().addBodies(bodies);
        
        if(isPressed(KeyEvent.VK_UP)) {
            circle.adjustPosition(new Vector2f(0, -1));
        } else if(isPressed(KeyEvent.VK_DOWN)) {
            circle.adjustPosition(new Vector2f(0, 1));
        }
        if(isPressed(KeyEvent.VK_LEFT)) {
            circle.adjustPosition(new Vector2f(-1, 0));
        } else if(isPressed(KeyEvent.VK_RIGHT)) {
            circle.adjustPosition(new Vector2f(1, 0));
        }
        
        getSpace().processOverlaps();
        
        getSpace().clearBodies();
    }

    public static void main(String[] args) {
        OverlapDemo demo = new OverlapDemo();
        demo.start();
    }
}
