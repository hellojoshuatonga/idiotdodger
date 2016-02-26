/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JFrame;

/**
 *
 * @author root
 */
public class FontManager {
    Font big, medium, small;
    FontMetrics fmBig, fmMedium, fmSmall;
    
    public FontManager(JFrame frame) {
        big = new Font("Helvetica", Font.BOLD, 42);
        medium = new Font("Helvetica", Font.BOLD, 26);
        small = new Font("Helvetica", Font.BOLD, 16);
        
        fmBig = frame.getFontMetrics(big);
        fmMedium = frame.getFontMetrics(medium);
        fmSmall = frame.getFontMetrics(small);
    }
    
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
