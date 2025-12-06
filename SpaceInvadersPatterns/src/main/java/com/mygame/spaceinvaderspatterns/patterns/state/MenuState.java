package com.mygame.spaceinvaderspatterns.patterns.state;

import com.mygame.spaceinvaderspatterns.StarField; // Import your new StarField
import com.mygame.spaceinvaderspatterns.patterns.singleton.GameEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MenuState implements GameState {

    private StarField starField; // Background animation
    private double scale = 1.0;
    private boolean growing = true;

    public MenuState() {
        this.starField = new StarField();
    }

    @Override
    public void update() {
        starField.update(); // Move stars
        
        // Pulse animation for title
        if (growing) {
            scale += 0.005;
            if (scale > 1.1) growing = false;
        } else {
            scale -= 0.005;
            if (scale < 0.9) growing = true;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Draw Moving Starfield
        starField.draw(gc);

        // 2. Draw Title with Neon Effect
        gc.save();
        gc.translate(400, 200);
        gc.scale(scale, scale);
        gc.translate(-400, -200);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        
        // Shadow
        gc.setFill(Color.DARKBLUE);
        gc.fillText("SPACE INVADERS", 405, 205);
        
        // Main Text
        gc.setFill(Color.CYAN);
        gc.fillText("SPACE INVADERS", 400, 200);
        
        gc.restore();

        // 3. Draw Subtext
        gc.setFont(Font.font("Consolas", 20));
        gc.setFill(Color.WHITE);
        gc.fillText("PRESS [ENTER] TO START", 400, 400);
        
        gc.setFill(Color.GREY);
        gc.setFont(Font.font("Arial", 12));
        gc.fillText("v2.0 - Pattern Edition", 400, 550);
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            GameEngine.getInstance().setState(new PlayingState());
        }
    }
}