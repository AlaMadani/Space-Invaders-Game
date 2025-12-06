/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.composite;

import com.mygame.spaceinvaderspatterns.patterns.composite.Alien;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YOUSSEF
 */

public class GreenAlien extends Alien {
    public GreenAlien(double x, double y) {
        super(x, y);
    }

  @Override
public void draw(GraphicsContext gc) {
    // Head (Triangle down)
    gc.setFill(Color.LIME);
    gc.fillPolygon(
        new double[]{x, x + width, x + width / 2}, 
        new double[]{y, y, y + height}, 
        3
    );
    
    // Glowing Eye
    gc.setFill(Color.BLACK);
    gc.fillOval(x + width/2 - 5, y + 5, 10, 10);
    gc.setFill(Color.RED); // Evil red pupil
    gc.fillOval(x + width/2 - 2, y + 8, 4, 4);
}
}
