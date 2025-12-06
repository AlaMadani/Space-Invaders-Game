package com.mygame.spaceinvaderspatterns.patterns.state;

import com.mygame.spaceinvaderspatterns.patterns.singleton.GameEngine;
import com.mygame.spaceinvaderspatterns.patterns.observer.ScoreManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PausedState implements GameState {

    private GameState returnState; // The state we paused from

    public PausedState(GameState stateToKeep) {
        this.returnState = stateToKeep;
    }

    @Override
    public void update() {
        // Game logic is frozen, do nothing
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Draw the frozen game behind the menu
        returnState.draw(gc);

        // 2. Draw a semi-transparent dark overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.7)); 
        gc.fillRect(0, 0, 800, 600);

        // 3. Draw Pause Text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 60));
        gc.fillText("PAUSED", 300, 250);

        // 4. Draw Instructions
        gc.setFont(Font.font("Consolas", 20));
        gc.setFill(Color.YELLOW);
        gc.fillText("[P] Resume", 350, 320);
        gc.fillText("[R] Restart Level", 350, 360);
        gc.fillText("[M] Main Menu", 350, 400);
    }

    @Override
    public void handleInput(KeyEvent event) {
       if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            
            if (event.getCode() == KeyCode.P) {
                // Resume
                GameEngine.getInstance().setState(returnState);
            } 
            else if (event.getCode() == KeyCode.M) {
                // Return to Main Menu
                GameEngine.getInstance().setState(new MenuState());
            }
            else if (event.getCode() == KeyCode.R) {
                // --- RESTART LOGIC ---
                ScoreManager.getInstance().reset(); // Reset Score
                GameEngine.getInstance().setState(new PlayingState()); // Create FRESH game
            }
        }
    }
}