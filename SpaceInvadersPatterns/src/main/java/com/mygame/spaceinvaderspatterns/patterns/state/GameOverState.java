/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.state;
import com.mygame.spaceinvaderspatterns.patterns.observer.ScoreManager;
import com.mygame.spaceinvaderspatterns.patterns.singleton.GameEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 *
 * @author YOUSSEF
 */
public class GameOverState implements GameState {

    @Override
    public void update() {}

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);
        
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.fillText("GAME OVER", 250, 250);
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score Final: " + ScoreManager.getInstance().getScore(), 320, 320);
        gc.fillText("Appuyez sur ENTER pour rejouer", 280, 400);
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            ScoreManager.getInstance().reset();
            GameEngine.getInstance().setState(new PlayingState());
        }
    }
}