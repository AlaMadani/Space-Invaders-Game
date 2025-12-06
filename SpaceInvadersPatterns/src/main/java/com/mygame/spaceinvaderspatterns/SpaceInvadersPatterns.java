/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mygame.spaceinvaderspatterns;

import com.mygame.spaceinvaderspatterns.patterns.state.MenuState;
import com.mygame.spaceinvaderspatterns.patterns.singleton.GameEngine;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author YOUSSEF
 */
public class SpaceInvadersPatterns extends Application {

    private static final Logger logger = LogManager.getLogger(SpaceInvadersPatterns.class);

    @Override
    public void start(Stage primaryStage) {
        logger.info("[INFO] Game started"); 

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        GameEngine engine = GameEngine.getInstance();
        engine.setState(new MenuState()); 

        scene.setOnKeyPressed(event -> engine.handleInput(event));
        scene.setOnKeyReleased(event -> engine.handleInput(event));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                engine.update();
                engine.draw(gc);
            }
        }.start();

        primaryStage.setTitle("Space Invaders Patterns");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
