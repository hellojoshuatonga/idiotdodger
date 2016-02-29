/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger.input;


import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author mineski
 */
public class MouseAdapter extends MouseInputAdapter {
    private int x, y;
    private boolean isDragging, isReleased, isClicked, isMoving;
    

    
    public MouseAdapter() {
        isClicked = false;
        isDragging = false;
        isReleased = false;
        isMoving = false;
    }
    
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    private void updateCoordinates(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    
    public boolean isMouseClicked() {
        if (isClicked) {
            isClicked = false;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isMouseReleased() {
        if (isReleased) {
            isReleased = false;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isMouseDragging() {
        return isDragging;
    }
    
    public boolean isMouseMoving() {
        return isMoving;
    }
    
    @Override
    public void mouseClicked(MouseEvent event) {
        updateCoordinates(event);
        
        
        if (!isClicked)
            isClicked = true;
        
        if (isDragging)
            isDragging = false;
        
        if (isMoving)
            isMoving = false;
        
        if (isReleased)
            isReleased = false;
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
        updateCoordinates(event);
        

        if (!isDragging)
            isDragging = true;
        
        if (isClicked)
            isClicked = false;
        
        if (isMoving)
            isMoving = false;
        
        if (isReleased)
            isReleased = false;

    }
    
    @Override
    public void mouseMoved(MouseEvent event) {
        updateCoordinates(event);
        
        if (!isMoving)
            isMoving = true;
        
        if (isClicked)
            isClicked = false;
        
        if (isDragging)
            isDragging = false;
        
        if (isReleased)
            isReleased = false;
    }

    /**
     *
     * @param event Mouse event
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        updateCoordinates(event);
        
        if (!isReleased)
            isReleased = true;
        
        if (isClicked)
            isClicked = false;
        
        if (isMoving)
            isMoving = false;
        
        if (isDragging)
            isDragging = false;
        
    }
}
