package com.mygame.spaceinvaderspatterns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class StarField {
    private double[] starsX;
    private double[] starsY;
    private double[] speeds;
    private int count = 100;
    private Random rand = new Random();

    public StarField() {
        starsX = new double[count];
        starsY = new double[count];
        speeds = new double[count];

        for (int i = 0; i < count; i++) {
            starsX[i] = rand.nextDouble() * 800;
            starsY[i] = rand.nextDouble() * 600;
            speeds[i] = 0.5 + rand.nextDouble() * 2.0; // Parallax speed
        }
    }

    public void update() {
        for (int i = 0; i < count; i++) {
            starsY[i] += speeds[i]; // Move stars down
            if (starsY[i] > 600) {  // Reset to top
                starsY[i] = 0;
                starsX[i] = rand.nextDouble() * 800;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        // Clear background with deep space blue-black
        gc.setFill(Color.rgb(10, 10, 30)); 
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.WHITE);
        for (int i = 0; i < count; i++) {
            double size = speeds[i]; // Faster stars are bigger
            gc.fillOval(starsX[i], starsY[i], size, size);
        }
    }
}