/*
 * Corrected SpaceShip.java - No Green Triangle
 */
package com.mygame.spaceinvaderspatterns.patterns.decorator;

import com.mygame.spaceinvaderspatterns.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Concrete Component: The base player ship
 * @author YOUSSEF
 */
public class SpaceShip implements Ship {
    private double x;
    private double y;
    private double speed = 5.0;
    private double width = 50;
    private double height = 40;

    public SpaceShip() {
        this.x = 375; 
        this.y = 550; 
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Engine Flame (Flickering effect)
        double centerX = getX() + 25; // Center of ship (width is 50)
        double engineY = getY() + 40;
        // A. Outer Flame (Orange) - Flickers between length 15 and 25
        double flicker1 = 15 + Math.random() * 10; 
        gc.setFill(Color.ORANGE);
        gc.fillPolygon(
            new double[]{centerX - 6, centerX + 6, centerX}, // Top-Left, Top-Right, Bottom-Tip
            new double[]{engineY, engineY, engineY + flicker1}, 
            3
        );

        // B. Inner Core (Yellow) - Flickers between length 8 and 15
        double flicker2 = 8 + Math.random() * 7;
        gc.setFill(Color.YELLOW);
        gc.fillPolygon(
            new double[]{centerX - 3, centerX + 3, centerX}, 
            new double[]{engineY, engineY, engineY + flicker2}, 
            3
        );
        
        // 2. Wings (Dark Grey) - Drawn first so body goes on top
        gc.setFill(Color.DARKGREY);
        gc.fillPolygon(
            new double[]{getX(), getX() + 50, getX() + 25}, 
            new double[]{getY() + 40, getY() + 40, getY() + 10}, 
            3
        );

        // 3. Main Body (Silver/White)
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(getX() + 20, getY() + 10, 10, 30);
        
        // 4. Cockpit (Blue)
        gc.setFill(Color.CYAN);
        gc.fillOval(getX() + 20, getY() + 15, 10, 15);

        // 5. Cannons (Red tips)
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY() + 20, 5, 15);
        gc.fillRect(getX() + 45, getY() + 20, 5, 15);
        
        // --- DELETED THE OLD GREEN TRIANGLE CODE HERE ---
    }

    @Override
    public void moveLeft() {
        if (x > 0) x -= speed;
    }

    @Override
    public void moveRight() {
        if (x < 750) x += speed; 
    }
    
    @Override
    public void moveUp() {
        if (y > 0) y -= speed; 
    }

    @Override
    public void moveDown() {
        if (y < 560) y += speed; // 560 keeps it above bottom edge
    }
    
    @Override
    public Projectile shoot() {
        return new Projectile(x + 22, y - 10, false);
    }
    
    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }
    
    @Override
    public double getWidth() { return width; }

    @Override
    public double getHeight() { return height; }
}