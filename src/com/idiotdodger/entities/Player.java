/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.entities;

import com.idiotdodger.GameSettings;

/**
 *
 * @author root
 */
public class Player extends Sprite {

    /**
     * Initialize player with x and y coordinates
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Player(int x, int y) {
        super(x, y);
        
        initialize();
    }
    
    /**
     * Setup all things needed
     */
    private void initialize() {
        loadImage(GameSettings.PlayerImageHappy);
    }
    
    
    /**************************************************************************
     **************************** Public methods ******************************
     **************************************************************************/
    
    /**
     * Get the new coordinates when the mouse is currently being dragged
     * 
     * @param newX X coordinate
     * @param newY Y coordinate
     */
    public void move(int newX, int newY) {     
        x = newX;
        y = newY;
        
        if (newX < halfWidth)
            x = halfWidth;
        
        if (newX >= GameSettings.WINDOW_WIDTH - halfWidth)
            x = GameSettings.WINDOW_WIDTH - halfWidth;
        
        if (newY < halfHeight)
            y = halfHeight;
        
        if (newY >= GameSettings.WINDOW_HEIGHT - height)
            y = GameSettings.WINDOW_HEIGHT - height;

    }
}
