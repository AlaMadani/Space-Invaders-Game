/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.composite;

import com.mygame.spaceinvaderspatterns.Projectile;
import com.mygame.spaceinvaderspatterns.patterns.observer.ScoreManager;
import javafx.scene.canvas.GraphicsContext;
/**
 *
 * @author YOUSSEF
 */

public abstract class Alien {
    
    protected double x, y;
    protected double width = 30;
    protected double height = 30;
    
    protected double dx = 2.0;
    protected double dy = 0;

    protected boolean alive = true;
    
    public Alien(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean isAlive() { return alive; }

    public abstract void draw(GraphicsContext gc);

    public boolean checkCollision(Projectile p) {
        if (!alive || !p.isActive()) return false;
        
        if (p.isEnemyShot()) return false;

        if (p.getX() < x + width && p.getX() + p.getWidth() > x &&
            p.getY() < y + height && p.getY() + p.getHeight() > y) {
            
            this.alive = false; 
            p.deactivate(); 
            ScoreManager.getInstance().addPoints(100);
            return true;         
        }
        return false;
    }
    
    public void update() {
        x += dx;
        y += dy;
    }
    
    public void add(Alien alien) { 
        throw new UnsupportedOperationException("Impossible d'ajouter à un alien simple"); 
    }
    public void remove(Alien alien) { 
        throw new UnsupportedOperationException("Impossible de supprimer d'un alien simple"); 
    }
    
    public Projectile shoot() {
        return new Projectile(x + width / 2, y + height, true);
    }
    
    public void reverseDirection() {
        this.dx = -this.dx; 
    }

    public void descend() {
        this.y += 20; 
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
