/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.singleton;

import com.mygame.spaceinvaderspatterns.patterns.state.GameState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author YOUSSEF
 */

public class GameEngine {
    private static final Logger logger = LogManager.getLogger(GameEngine.class);
    
    // Singleton simple
    private static GameEngine instance;
    
    private GameState currentState;
    private boolean isRunning = true;

    private GameEngine() {
        logger.info("[INFO] Game engine initialized");
    }

    public static GameEngine getInstance() {
        if (instance == null) instance = new GameEngine();
        return instance;
    }

    public void setState(GameState newState) {
        // Logging de la transition d'état obligatoire [cite: 78, 97]
        String oldStateName = (currentState != null) ? currentState.getClass().getSimpleName() : "None";
        String newStateName = newState.getClass().getSimpleName();
        
        logger.info("[STATE] Game: " + oldStateName + " -> " + newStateName);
        
        this.currentState = newState;
    }

    public void update() {
        if (currentState != null) currentState.update();
    }

    public void draw(GraphicsContext gc) {
        if (currentState != null) currentState.draw(gc);
    }

    public void handleInput(KeyEvent event) {
        if (currentState != null) currentState.handleInput(event);
    }
}
