/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author YOUSSEF
 */

public class ShieldDecorator extends ShipDecorator {
    private static final Logger logger = LogManager.getLogger(ShieldDecorator.class);

    public ShieldDecorator(Ship ship) {
        super(ship);
        logger.info("[DECORATOR] Bouclier activé !");
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        
        gc.setStroke(Color.CYAN);
        gc.setLineWidth(3);
        gc.strokeOval(getX() - 10, getY() - 10, 70, 70);
    }
}