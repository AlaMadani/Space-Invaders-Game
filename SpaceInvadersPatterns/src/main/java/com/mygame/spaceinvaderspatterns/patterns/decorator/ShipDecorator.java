/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.decorator;
import com.mygame.spaceinvaderspatterns.Projectile;
import com.mygame.spaceinvaderspatterns.patterns.decorator.Ship;
import javafx.scene.canvas.GraphicsContext;
/**
 *
 * @author YOUSSEF
 */

public abstract class ShipDecorator implements Ship {
    protected Ship decoratedShip;

    public ShipDecorator(Ship ship) {
        this.decoratedShip = ship;
    }

    public Ship getDecoratedShip() {
        return decoratedShip;
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShip.draw(gc); 
    }

    @Override
    public void moveLeft() {
        decoratedShip.moveLeft();
    }

    @Override
    public void moveRight() {
        decoratedShip.moveRight();
    }
    
    @Override
    public Projectile shoot() {
        return decoratedShip.shoot(); 
    }

    @Override
    public double getX() { return decoratedShip.getX(); }

    @Override
    public double getY() { return decoratedShip.getY(); }
}