/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.decorator;

import com.mygame.spaceinvaderspatterns.Projectile;
import com.mygame.spaceinvaderspatterns.patterns.decorator.Ship;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YOUSSEF
 */

public class SpaceShip implements Ship {
    private double x;
    private double y;
    private double speed = 5.0;

    public SpaceShip() {
        this.x = 375; 
        this.y = 550; 
    }

   @Override
public void draw(GraphicsContext gc) {
    // 1. Engine Flame (Flickering)
    if (Math.random() > 0.5) {
        gc.setFill(Color.ORANGE);
        gc.fillOval(getX() + 20, getY() + 45, 10, 15);
    }
    
    // 2. Wings (Dark Grey)
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
    public Projectile shoot() {
        return new Projectile(x + 22, y - 10, false);
    }
    
    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }
}