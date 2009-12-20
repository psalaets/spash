/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto. The
 * original source remains:
 * 
 * Copyright (c) 2006 Erin Catto http://www.gphysics.com
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package org.spash;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashSet;
import java.util.Set;

import org.spash.Body;
import org.spash.ROVector2f;
import org.spash.Space;
import org.spash.broad.BruteForce;
import org.spash.narrow.ConfigurableBodyOverlapper;
import org.spash.ray.Ray;
import org.spash.ray.intersect.ConfigurableRayBodyIntersector;
import org.spash.shape.Circle;
import org.spash.shape.Line;
import org.spash.shape.Rect;

/**
 * Modified version of Phys2d's AbstractDemo.  Demos should override 
 * {@link #init()}, {@link #update()} and {@link #draw(Graphics2D)} to do what
 * they do.  A Space is available using {@link #getSpace()}. 
 */
public abstract class AbstractDemo {
    /** The frame displaying the demo */
    protected Frame frame;
    /** The title of the current demo */
    protected String title;
    /** True if the simulation is running */
    private boolean running = true;
    /** The rendering strategy */
    private BufferStrategy strategy;

    private Set<Integer> pressed;
    protected Color rayColor;
    protected Color bodyColor;
    protected Color foreground;
    protected Color background;
    protected Space space;

    /**
     * Create a new demo
     * 
     * @param title The title of the demo
     */
    public AbstractDemo(String title) {
        this.title = title;
        pressed = new HashSet<Integer>();
        foreground = Color.black;
        background = Color.white;
        bodyColor = Color.black;
        rayColor = Color.red;

        BruteForce bf = new BruteForce();
        ConfigurableBodyOverlapper overlapper = new ConfigurableBodyOverlapper();
        overlapper.registerDefaults();
        space = new Space(bf, overlapper);

        ConfigurableRayBodyIntersector intersector = new ConfigurableRayBodyIntersector();
        intersector.registerDefaults();
        space.equipForRays(bf, intersector);
    }

    /**
     * Gets the space (already configured for overlap and rays).
     */
    protected Space getSpace() {
        return space;
    }
    
    protected void setSpace(Space space) {
        this.space = space;
    }

    /**
     * Retrieve the title of the demo
     * 
     * @return The title of the demo
     */
    public String getTitle() {
        return title;
    }

    /**
     * Initialise the GUI
     */
    private void initGUI() {
        frame = new Frame(title);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.setSize(500, 500);

        int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 500) / 2;
        int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 500) / 2;

        frame.setLocation(x, y);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
                System.exit(0);
            }
        });
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else {
                    pressed.add(e.getKeyCode());
                }
            }

            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyCode());
                
                AbstractDemo.this.keyHit(e.getKeyCode());
            }
        });

        frame.setVisible(true);
        frame.createBufferStrategy(2);

        strategy = frame.getBufferStrategy();
    }

    /**
     * Called when a key is released.
     */
    protected void keyHit(int keyCode) {
    }
    
    protected boolean isPressed(int keyCode) {
        return pressed.contains(keyCode);
    }

    /**
     * Start the simulation running
     */
    public void start() {
        initGUI();
        initDemo();

        float target = 1000 / 60.0f;
        float frameAverage = target;
        long lastFrame = System.currentTimeMillis();
        float yield = 10000f;
        float damping = 0.1f;

        while (running) {
            // adaptive timing loop from Master Onyx
            long timeNow = System.currentTimeMillis();
            frameAverage = (frameAverage * 10 + (timeNow - lastFrame)) / 11;
            lastFrame = timeNow;

            yield += yield * ((target / frameAverage) - 1) * damping + 0.05f;

            for (int i = 0; i < yield; i++) {
                Thread.yield();
            }

            update();

            // render
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(background);
            g.fillRect(0, 0, 500, 500);

            draw(g);
            renderGUI(g);
            g.dispose();
            strategy.show();
        }
    }

    /**
     * Demo customisable GUI render
     * 
     * @param g The graphics context to use for rendering here
     */
    protected void renderGUI(Graphics2D g) {
        g.setColor(foreground);
        g.drawString("ESC - Exit", 15, 430);
    }

    /**
     * Draw a body
     * 
     * @param g The graphics contact on which to draw
     * @param body The body to be drawn
     */
    protected void drawBody(Graphics2D g, Body body) {
        if (body.getShape() instanceof Rect) {
            drawRectBody(g, body, (Rect) body.getShape());
        }
        if (body.getShape() instanceof Circle) {
            drawCircleBody(g, body, (Circle) body.getShape());
        }
        if (body.getShape() instanceof Line) {
            drawLineBody(g, body, (Line) body.getShape());
        }
    }

    /**
     * Draw a line into the demo
     */
    protected void drawLineBody(Graphics2D g, Body body, Line line) {
        g.setColor(bodyColor);

        ROVector2f[] verts = line.getVertices();
        g.drawLine(
                (int)verts[0].getX(),
                (int)verts[0].getY(),
                (int)verts[1].getX(),
                (int)verts[1].getY());
    }

    /**
     * Draw a ray.
     */
    protected void drawRay(Graphics2D g, Ray ray) {
        g.setColor(rayColor);

        g.drawLine(
                (int)ray.getStart().getX(),
                (int)ray.getStart().getY(),
                (int)ray.getEnd().getX(),
                (int)ray.getEnd().getY());
    }

    /**
     * Draw a circle.
     */
    protected void drawCircleBody(Graphics2D g, Body body, Circle circle) {
        g.setColor(bodyColor);
        float x = body.getPosition().getX();
        float y = body.getPosition().getY();
        float r = circle.getRadius();

        drawCircle(g, (int)x, (int)y, (int)r);
    }
    
    protected void drawCircle(Graphics2D g, int x, int y, int r) {
        g.drawOval((int)(x - r), (int)(y - r), (int)(r * 2), (int)(r * 2));
    }

    /**
     * Draw a rect.
     */
    protected void drawRectBody(Graphics2D g, Body body, Rect rect) {
        ROVector2f[] v = rect.getVertices();

        ROVector2f v1 = v[0];
        ROVector2f v2 = v[1];
        ROVector2f v3 = v[2];
        ROVector2f v4 = v[3];

        g.setColor(bodyColor);
        g.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
        g.drawLine((int)v2.getX(), (int)v2.getY(), (int)v3.getX(), (int)v3.getY());
        g.drawLine((int)v3.getX(), (int)v3.getY(), (int)v4.getX(), (int)v4.getY());
        g.drawLine((int)v4.getX(), (int)v4.getY(), (int)v1.getX(), (int)v1.getY());
    }

    public final void initDemo() {
        System.out.println("Initialising: " + getTitle());
        init();
    }

    /**
     * Called before everything.
     */
    protected abstract void init();

    /**
     * Called before draw.
     */
    protected void update() {
    }

    protected void draw(Graphics2D g) {
    }
}
