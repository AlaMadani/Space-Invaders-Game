/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.observer;

import com.mygame.spaceinvaderspatterns.patterns.observer.GameObserver;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author YOUSSEF
 */
public class ScoreManager {
    private static final Logger logger = LogManager.getLogger(ScoreManager.class);
    private static ScoreManager instance;
    private int score = 0;
    private List<GameObserver> observers = new ArrayList<>();

    private ScoreManager() {}

    public static ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void addPoints(int points) {
        this.score += points;
        notifyObservers();
    }
    
    public int getScore() { return score; }
    public void reset() {
        score = 0; 
        notifyObservers();
        logger.info("Score reset.");
    }

    private void notifyObservers() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(score);
        }
    }
}