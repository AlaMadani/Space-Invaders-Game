/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns.patterns.composite;

import com.mygame.spaceinvaderspatterns.Projectile;
import com.mygame.spaceinvaderspatterns.patterns.composite.Alien;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
/**
 *
 * @author YOUSSEF
 */

public class AlienSquad extends Alien {
    private List<Alien> members = new ArrayList<>();
    private Random random = new Random(); // Random generator
    private double currentSpeedFlux = 0;
    public AlienSquad(double x, double y) {
        super(x, y);
    }

    @Override
    public void add(Alien alien) {
        members.add(alien);
    }

    @Override
    public void remove(Alien alien) {
        members.remove(alien);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Alien member : members) {
            member.draw(gc);
        }
    }

    @Override
    public void update() {
        if (random.nextDouble() < 0.03) {
            // Generates a random speed modifier between -1.5 and +1.5
            currentSpeedFlux = (random.nextDouble() * 3.0) - 1.5;
        }
        for (Alien member : members) {
            double effectiveDx = (this.dx > 0) ? (this.dx + currentSpeedFlux) : (this.dx - currentSpeedFlux);
            
            // Ensure they don't stop completely
            if (Math.abs(effectiveDx) < 0.5) effectiveDx = (this.dx > 0) ? 1 : -1;

            // We manually update X here to override the simple 'update' if we want custom logic
            // OR we can set the member's dx.
            
            member.dx = effectiveDx; // Sync speed
            member.update();
        }
    }

    public void checkWallCollision() {
        boolean hitWall = false;
        List<Alien> allAliens = getAllAliens();

        for (Alien a : allAliens) {
            if (a.getX() + a.getWidth() >= 800 && a.dx > 0) {
                hitWall = true;
                break;
            }
            if (a.getX() <= 0 && a.dx < 0) {
                hitWall = true;
                break;
            }
        }

        if (hitWall) {
            this.dx = -this.dx; // Reverse Squad direction
            currentSpeedFlux = 0;
            for (Alien a : allAliens) {
                a.descend();
                a.dx = this.dx;
            }
        }
    }
    
    public boolean isEmpty() { return members.isEmpty(); }
    
    public List<Alien> getMembers() { return members; }

    private List<Alien> getAllAliens() {
        List<Alien> flatList = new ArrayList<>();
        for (Alien member : members) {
            if (member instanceof AlienSquad) {
                flatList.addAll(((AlienSquad) member).getAllAliens());
            } else {
                flatList.add(member);
            }
        }
        return flatList;
    }

    @Override
    public boolean checkCollision(Projectile p) {
        for (Alien member : members) {
            if (member.checkCollision(p)) return true;
        }
        return false;
    }

    public void removeDead() {
        members.removeIf(alien -> !alien.isAlive());
        for (Alien member : members) {
            if (member instanceof AlienSquad) ((AlienSquad) member).removeDead();
        }
        members.removeIf(alien -> (alien instanceof AlienSquad) && ((AlienSquad) alien).isEmpty());
    }
}
