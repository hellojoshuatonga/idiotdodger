/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger;

import java.util.logging.Level;

/**
 *
 * @author root
 */
public class GameSettings {
    
    // Title of the game (Title in jFrame)
    public static final String TITLE = "Idiot Dodger";
    
    // Frame per second
    public static final int FPS = 30;
    
    // Width of window (jFrame)
    public static int WINDOW_WIDTH = 500;
    
    // Height of window (jFrame)
    public static int WINDOW_HEIGHT = 540;
    
    // 50x50 Image of player
    public static String PlayerImageHappy = "resources/player/happy-50x50.png";
    
    // 50x100 Image for spikes
    public static String SpikeImage = "resources/spike/spike-50x100.png";
    
    // Level of our logger module
    public static final Level LOGGER_LEVEL = Level.ALL;
    
    public static void setWindowWidth(int width) {
        WINDOW_WIDTH = width;
    }
    
    public static void setWindowHeight(int height) {
        WINDOW_HEIGHT = height;
    }
    
}
