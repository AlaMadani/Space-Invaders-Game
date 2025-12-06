/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.decorator;

import com.mygame.spaceinvaderspatterns.Projectile;
import javafx.scene.canvas.GraphicsContext;
/**
 *
 * @author YOUSSEF
 */
public interface Ship {
    void draw(GraphicsContext gc);
    void moveLeft();
    void moveRight();
    void moveUp();   // <--- NEW
    void moveDown();
    double getX();
    double getY();
    double getWidth();  // <--- NEW
    double getHeight();
    Projectile shoot();
}
