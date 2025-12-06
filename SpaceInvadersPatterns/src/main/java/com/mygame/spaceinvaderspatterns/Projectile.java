/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YOUSSEF
 */

public class Projectile {
    private double x, y;
    private double speed = 10.0;
    private boolean active = true;
    private boolean isEnemy = false; 

    public Projectile(double x, double y, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
    }

    public void update() {
        if (isEnemy) {
            y += 5.0; 
            if (y > 600) active = false;
        } else {
            y -= 10.0;
            if (y < 0) active = false;
        }
    }

public void draw(GraphicsContext gc) {
    if (isEnemy) {
        // Enemy Laser: Red with White Core
        gc.setFill(Color.RED);
        gc.fillOval(x - 2, y - 2, 12, 12); // Outer Glow
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, 8, 8); // Core
    } else {
        // Player Laser: Blue/Cyan Bolt
        gc.setFill(Color.CYAN);
        gc.fillRect(x, y, 6, 15); // Outer
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 2, y + 2, 2, 11); // Core
    }
}

    public boolean isEnemyShot() { return isEnemy; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return isEnemy ? 8 : 5; }
    public double getHeight() { return isEnemy ? 8 : 15; }
}