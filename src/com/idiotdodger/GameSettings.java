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
    

    // Level of our logger module
    public static final Level LOGGER_LEVEL = Level.INFO;

    // Font family
    public static String FONT_FAMILY = "Helvetica";

    /**************************************************************************
     ********************************* Resources ******************************
     **************************************************************************/

    public static final String BACKGROUND_IMAGE = "resources/game/spacebackground3.png";

    public static final String TITLE_IMAGE = "resources/game/idiotdodgertitle.png";

    // 50x50 Image of player
    public static final String PLAYER_IMAGE_HAPPY = "resources/player/" +
            "shipgreen-50x50.png";

    // 50x100 Image for spikes
    public static final String SPIKE_IMAGE = "resources/spike/spike-50x100.png";

    // Sound effects
    public static final String SOUND_ONGAME = "resources/sounds/ongame.wav";
    public static final String SOUND_MENU = "resources/sounds/menu.wav";
    public static final String SOUND_DEAD = "resources/sounds/dead.wav";
    public static final String SOUND_CLICK = "resources/sounds/click.wav";


    /**************************************************************************
     **************************** Public methods ******************************
     **************************************************************************/

    /**
     * Set window width globally
     *
     * @param width New width
     * */
    public static void setWindowWidth(int width) {
        WINDOW_WIDTH = width;
    }


    /**
     * Set window height globally
     *
     * @param height New height
     * */
    public static void setWindowHeight(int height) {
        WINDOW_HEIGHT = height;
    }
    
}
