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
        return isClicked;
    }
    
    public boolean isMouseReleased() {
        return isReleased;
    }
    
    public boolean isMouseDragging() {
        return isDragging;
    }
    
    public boolean isMouseMoving() {
        return isMoving;
    }
    
    @Override
    public void mouseClicked(MouseEvent event) {
        // log("Mouse clicked", event);
        
        updateCoordinates(event);
        
        
        if (isClicked != true)
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
//        log("Mouse dragged", e);
        
        updateCoordinates(event);
        
        

        if (isDragging != true)
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
//        log("Mouse moved", e);
        
        updateCoordinates(event);
        
        if (isMoving != true)
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
//        log("Mouse released", e);
        
        updateCoordinates(event);
        

        
        if (isReleased != true)
            isReleased = true;
        
        if (isClicked)
            isClicked = false;
        
        if (isMoving)
            isMoving = false;
        
        if (isDragging)
            isDragging = false;
        
    }
    
    void log(String eventDescription, MouseEvent event) {
        System.out.println(eventDescription 
                        + " (" + event.getX() + "," + event.getY() + ")"
                        + " detected on "
                        + event.getComponent().getClass().getName());
    }
}
