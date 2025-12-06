/*
 * Fully Corrected PlayingState.java
 */
package com.mygame.spaceinvaderspatterns.patterns.state;

import com.mygame.spaceinvaderspatterns.Projectile;
import com.mygame.spaceinvaderspatterns.StarField;
import com.mygame.spaceinvaderspatterns.patterns.observer.ScoreManager;
import com.mygame.spaceinvaderspatterns.patterns.decorator.ShieldDecorator;
import com.mygame.spaceinvaderspatterns.patterns.decorator.Ship;
import com.mygame.spaceinvaderspatterns.patterns.decorator.SpaceShip;
import com.mygame.spaceinvaderspatterns.patterns.decorator.ShipDecorator; // <--- NEED THIS IMPORT
import com.mygame.spaceinvaderspatterns.patterns.singleton.GameEngine;
import com.mygame.spaceinvaderspatterns.patterns.observer.GameObserver;
import com.mygame.spaceinvaderspatterns.patterns.composite.AlienSquad;
import com.mygame.spaceinvaderspatterns.patterns.factory.AlienFactory;
import com.mygame.spaceinvaderspatterns.patterns.composite.Alien;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author YOUSSEF
 */
public class PlayingState implements GameState, GameObserver {

    private static final Logger logger = LogManager.getLogger(PlayingState.class);
    
    private Ship player;
    private AlienSquad fleet;
    private int currentScore = 0;
    private int level = 1;
    private Random random = new Random();
    private StarField starField;
    private Set<KeyCode> activeKeys = new HashSet<>();
    private long lastShotTime = 0;
    private static final long SHOOT_COOLDOWN = 300_000_000;
    
    // --- SHIELD SETTINGS ---
    // I set this to 200 for testing. Change back to 1000 when happy!
    private int nextShieldThreshold = 1000; 
    private long shieldEndTime = 0;         
    private boolean shieldActive = false;
    private int shieldsAvailable = 0;
    // -----------------------
    
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Bunker> bunkers = new ArrayList<>();

    public PlayingState() {
        this.starField = new StarField();
        this.player = new SpaceShip();
        this.fleet = new AlienSquad(0, 0);
        initBunkers();
        initLevel();
        
        ScoreManager.getInstance().addObserver(this);
        this.currentScore = ScoreManager.getInstance().getScore();
        
        // Check if we start with high score (restarted game)
        checkShieldActivation(this.currentScore);
    }

    private void checkShieldActivation(int score) {
        // While loop in case you get many points at once
        while (score >= nextShieldThreshold) {
            shieldsAvailable++; // Earn a charge
            nextShieldThreshold += 1000; // Increment threshold (1000, 2000...)
            logger.info("SHIELD CHARGE EARNED! Total: " + shieldsAvailable);
        }
    }

    private void activateShield() {
        if (!shieldActive) {
            if (!(player instanceof ShieldDecorator)) {
                player = new ShieldDecorator(player);
            }
            shieldActive = true;
            logger.info("SHIELD ACTIVATED! (10 Seconds)");
        }
        // Reset the timer to 10 seconds from NOW
        shieldEndTime = System.nanoTime() + 10_000_000_000L;
    }
    
    // --- MISSING METHOD ADDED ---
    private void deactivateShield() {
        if (shieldActive && (player instanceof ShipDecorator)) {
            // Remove Decorator (Unwrap)
            player = ((ShipDecorator) player).getDecoratedShip();
            shieldActive = false;
            logger.info("SHIELD EXPIRED.");
        }
    }
    // ----------------------------
    
    private void initBunkers() {
        bunkers.clear();
        double startX = 70;  
        double spacing = 200; 
        double y = 450;      

        for (int i = 0; i < 4; i++) {
            bunkers.add(new Bunker(startX + (i * spacing), y));
        }
    }
    
    private void initLevel() {
        fleet = new AlienSquad(0, 0); 
        int rows = Math.min(3 + (level - 1), 5); 
        int cols = Math.min(5 + (level - 1), 10);

        for (int row = 0; row < rows; row++) {
            AlienSquad rowSquad = new AlienSquad(0, 0);
            String type = (row % 2 == 0) ? "RED" : "GREEN";

            for (int col = 0; col < cols; col++) {
                double startX = (800 - (cols * 60)) / 2;
                double x = startX + (col * 60);
                double y = 50 + (row * 50);
                
                Alien alien = AlienFactory.createAlien(type, x, y);
                if (alien != null) rowSquad.add(alien);
            }
            fleet.add(rowSquad);
        }
        projectiles.clear();
        logger.info("Niveau " + level + " démarré avec " + rows + " lignes.");
    }

    @Override
    public void onScoreChanged(int newScore) {
        this.currentScore = newScore;
        checkShieldActivation(newScore);
    }

    @Override
    public void update() {
        starField.update();
        processInput();
        
        // --- CHECK SHIELD TIMER ---
        if (shieldActive) {
            if (System.nanoTime() > shieldEndTime) {
                deactivateShield(); // Turn it off!
            }
        }
        // --------------------------

        if (fleet.isEmpty()) {
            level++; 
            ScoreManager.getInstance().addPoints(500); 
            initLevel(); 
            return; 
        }
        
        fleet.checkWallCollision();
        fleet.update();
        
        if (checkInvasion()) {
            logger.info("INVASION RÉUSSIE ! Game Over.");
            GameEngine.getInstance().setState(new GameOverState());
            return;
        }

        if (random.nextDouble() < 0.02) { 
             double shootX = 50 + random.nextInt(700);
             double shootY = 100; 
             projectiles.add(new Projectile(shootX, shootY, true));
        }

        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();
            
            boolean hitSomething = false;
            
            for (Bunker b : bunkers) {
                if (b.checkCollision(p)) {
                    hitSomething = true;
                    break;
                }
            }
            
            if (hitSomething) {
                if (!p.isActive()) it.remove();
                continue; 
            }

            if (p.isEnemyShot()) {
                if (checkPlayerHit(p)) return;
            } else {
                fleet.checkCollision(p);
            }

            if (!p.isActive()) it.remove();
        }
        fleet.removeDead();
    }
    
    private void processInput() {
        if (activeKeys.contains(KeyCode.LEFT)) player.moveLeft();
        if (activeKeys.contains(KeyCode.RIGHT)) player.moveRight();
        
        if (activeKeys.contains(KeyCode.SPACE)) {
            long now = System.nanoTime();
            if (now - lastShotTime > SHOOT_COOLDOWN) {
                projectiles.add(player.shoot());
                lastShotTime = now;
            }
        }
    }
    
    private boolean checkPlayerHit(Projectile p) {
         if (p.getX() < player.getX() + 50 && p.getX() + p.getWidth() > player.getX() &&
             p.getY() < player.getY() + 30 && p.getY() + p.getHeight() > player.getY()) {
             
             // --- CHECK FOR SHIELD ---
             if (player instanceof ShieldDecorator) {
                 // We are protected! 
                 p.deactivate(); 
                 return false;   
             }
             // ------------------------

             GameEngine.getInstance().setState(new GameOverState());
             return true;
         }
         return false;
    }
    
    private boolean checkInvasion() {
        return isAlienTooLow(fleet);
    }
    
    private boolean isAlienTooLow(Alien alien) {
        if (alien instanceof AlienSquad) {
            for (Alien member : ((AlienSquad) alien).getMembers()) {
                if (isAlienTooLow(member)) return true;
            }
        } else {
            if (alien.getY() > 500) return true;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        starField.draw(gc);
        
        // REMOVED: gc.fillRect(0, 0, 800, 600); <-- This was hiding the stars!

        player.draw(gc);
        fleet.draw(gc);
        
        for (Bunker b : bunkers) b.draw(gc);
        for (Projectile p : projectiles) p.draw(gc);
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Consolas", 20));
        gc.fillText("SCORE: " + currentScore, 20, 30);
        gc.fillText("Vies: 1", 20, 60);
        gc.fillText("NIVEAU: " + level, 20, 90);
        
        // Show Shield Status
        if (shieldActive) {
            long remaining = (shieldEndTime - System.nanoTime()) / 1_000_000_000L;
            gc.setFill(Color.CYAN);
            gc.fillText("SHIELD ACTIVE: " + remaining + "s", 300, 30); // Moved to top center
        } else {
            if (shieldsAvailable > 0) {
                gc.setFill(Color.LIME);
                gc.fillText("SHIELDS READY: " + shieldsAvailable + " [PRESS B]", 300, 30);
            } else {
                gc.setFill(Color.GRAY);
                gc.fillText("NEXT SHIELD: " + nextShieldThreshold, 300, 30);
            }
        }
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            activeKeys.add(event.getCode());
            
            if (event.getCode() == KeyCode.P) {
                GameEngine.getInstance().setState(new PausedState(this)); 
            }
            else if (event.getCode() == KeyCode.B) {
                if (shieldsAvailable > 0) {
                    activateShield();
                    shieldsAvailable--;
                } else {
                    logger.info("No shields available! Reach " + nextShieldThreshold + " points.");
                }
            }
        } 
        else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            activeKeys.remove(event.getCode());
        }
    }
}