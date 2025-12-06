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

public class RedAlien extends Alien {
    public RedAlien(double x, double y) {
        super(x, y);
    }

    @Override
public void draw(GraphicsContext gc) {
    // Body
    gc.setFill(Color.RED);
    gc.fillOval(x, y, width, height - 10);
    
    // Eyes
    gc.setFill(Color.YELLOW);
    gc.fillOval(x + 5, y + 5, 8, 8);
    gc.fillOval(x + width - 13, y + 5, 8, 8);
    
    // Legs/Mandibles
    gc.setStroke(Color.RED);
    gc.setLineWidth(3);
    gc.strokeLine(x + 5, y + height - 10, x, y + height);
    gc.strokeLine(x + width - 5, y + height - 10, x + width, y + height);
}
}
