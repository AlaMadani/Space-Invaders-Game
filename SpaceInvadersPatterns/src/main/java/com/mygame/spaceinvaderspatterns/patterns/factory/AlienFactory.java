/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.factory;

import com.mygame.spaceinvaderspatterns.patterns.composite.GreenAlien;
import com.mygame.spaceinvaderspatterns.patterns.composite.RedAlien;
import com.mygame.spaceinvaderspatterns.patterns.composite.Alien;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author YOUSSEF
 */

public class AlienFactory {
    private static final Logger logger = LogManager.getLogger(AlienFactory.class);

    public static Alien createAlien(String type, double x, double y) {
        Alien alien = null;
        
        switch (type.toUpperCase()) {
            case "RED":
                alien = new RedAlien(x, y);
                break;
            case "GREEN":
                alien = new GreenAlien(x, y);
                break;
            default:
                logger.warn("[FACTORY] Type d'alien inconnu : " + type);
                return null; 
        }

        logger.info("[FACTORY] Created alien: " + type + " at (" + x + ", " + y + ")");
        return alien;
    }
}
