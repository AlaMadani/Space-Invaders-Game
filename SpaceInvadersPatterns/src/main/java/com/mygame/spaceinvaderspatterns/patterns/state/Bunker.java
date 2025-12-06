/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.state;

import com.mygame.spaceinvaderspatterns.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YOUSSEF
 */
public class Bunker {
    private double x, y;
    private double width = 80;
    private double height = 50;
    private int health = 10; 
    
    public Bunker(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext gc) {
        if (health <= 0) return; 

        if (health > 7) gc.setFill(Color.GREEN);       
        else if (health > 4) gc.setFill(Color.YELLOW); 
        else gc.setFill(Color.RED);                    

        gc.fillRect(x, y, width, height - 15); // Haut
        gc.fillRect(x, y + height - 15, 15, 15); // Pied gauche
        gc.fillRect(x + width - 15, y + height - 15, 15, 15); // Pied droit
    }

    public boolean checkCollision(Projectile p) {
        if (health <= 0 || !p.isActive()) return false;

        if (p.getX() < x + width && p.getX() + p.getWidth() > x &&
            p.getY() < y + height && p.getY() + p.getHeight() > y) {
            
            health--; 
            p.deactivate(); 
            return true;
        }
        return false;
    }
    
    public boolean isDestroyed() {
        return health <= 0;
    }
}