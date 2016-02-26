/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.entities;

import com.idiotdodger.GameSettings;

/**
 * Sprite for the spike. Enemies of the player
 * 
 * @author root
 */
public class Spike extends Sprite {
    private int speed;
    
    public Spike(int x, int y) {
        super(x, y);
        
        this.speed = 1;
        
        initialize();
    }
    
    public Spike(int x, int y, int speed) {
        super(x, y);
        
        this.speed = speed;
        
        initialize();
    }
    
    public Spike(int x, int y, boolean visibility) {
        super(x, y, visibility);
        
        this.speed = 1;
        
        initialize();
    }
    
    public Spike(int x, int y, boolean visibility, int speed) {
        super(x, y, visibility);
        
        this.speed = speed;
        
        initialize();
    }
    
    /**
     * Setup all things needed here
     */
    private void initialize() {
        loadImage(GameSettings.SpikeImage);
    }
    
    /**************************************************************************
     **************************** Public methods ******************************
     **************************************************************************/
    
    /**
     * Spikes are going down. As always.
     * 
     */
    public void move() {
        y += speed;
        
        if (y > GameSettings.WINDOW_HEIGHT)
            setVisible(false);
    }

    
    /** 
     * Get speed
     * 
     * @return speed Speed of this sprite
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Set speed
     * 
     * @param speed New speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
