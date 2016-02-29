/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.utils;

import com.idiotdodger.GameSettings;

import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * This class will manage fonts for drawing
 *
 */
public class FontManager {
    Font big, medium, small;
    FontMetrics fmBig, fmMedium, fmSmall;
    
    public FontManager(JFrame frame) {
        big = new Font(GameSettings.FONT_FAMILY, Font.BOLD, 56);
        medium = new Font(GameSettings.FONT_FAMILY, Font.BOLD, 36);
        small = new Font(GameSettings.FONT_FAMILY, Font.BOLD, 16);


        fmBig = frame.getFontMetrics(big);
        fmMedium = frame.getFontMetrics(medium);
        fmSmall = frame.getFontMetrics(small);
    }

    /**************************************************************************
     **************************** Public methods ******************************
     **************************************************************************/

    /**
     * Get the big font
     *
     * @return big Big font
     * */
    public Font getBigFont() {
        return big;
    }
    
    public Font getMediumFont() {
        return medium;
    }
    
    public Font getSmallFont() {
        return small;
    }
    
    public FontMetrics getBigMetrics() {
        return fmBig;
    }
    
    public FontMetrics getMediumMetrics() {
        return fmMedium;
    }
    
    public FontMetrics getSmallMetrics() {
        return fmSmall;
    }
}
