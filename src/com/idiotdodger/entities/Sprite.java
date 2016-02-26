/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.entities;


import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

    // X coordinate
    protected int x;
    // Y coordinate
    protected int y;
    
    // Width of this sprite
    protected int width;
    // Height
    protected int height;
    
    // Half of width and height
    protected int halfWidth;
    protected int halfHeight;
    
    // Visibility of this sprite
    protected boolean visibility;
    
    // Image to use for this sprite
    protected Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visibility = false;
    }
    
    public Sprite(int x, int y, boolean visibility) {
        this.x = x;
        this.y = y;
        
        this.visibility = visibility;
    }
    

    /** 
     * Load the image for this sprite
     * 
     * @param imageName Image name to load
     */
    protected void loadImage(String imageName) {
        ImageIcon ii;
        
        ii = new ImageIcon(getClass().getClassLoader().getResource(imageName));
        image = ii.getImage();

        width = image.getWidth(null);
        height = image.getHeight(null);
        
        halfWidth = width / 2;
        halfHeight = height / 2;
    }
    
    /**************************************************************************
     *************************** Public methods *******************************
     **************************************************************************/

    /**
     * Get image of this sprite
     * 
     * @return image The image of this sprite
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get current x coordinate
     * @return x x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get current y coordinate
     * 
     * @return y y coordinate
     */
    public int getY() {
        return y;
    }
    
    /** 
     * Get the width of this sprite (image)
     * 
     * @return width width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Get the height
     * 
     * @return height height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Get the half of the width
     * 
     * @return halfWidth Half of the width
     */
    public int getHalfWidth() {
        return halfWidth;
    }
    
    /**
     * Get the half of the height
     * 
     * @return halfHeight Half of the height
     */
    public int getHalfHeight() {
        return halfHeight;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Check if this sprite is currently visible
     * 
     * @return visibility
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Set the visibibility of t his sprite
     * 
     * @param visible visibility
     */
    public void setVisible(Boolean visible) {
        this.visibility = visible;
    }

    
    /**
     * Change the bounds of this sprite. For collision.
     * 
     * @return Rectangle Rectangle graphic of this sprite (image)
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}