/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idiotdodger;



import com.idiotdodger.entities.Player;
import com.idiotdodger.entities.Spike;
import com.idiotdodger.input.MouseAdapter;
import com.idiotdodger.utils.FontManager;

import java.awt.*; 
import java.awt.image.BufferedImage; 
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame; 

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/** 
 * Main class for the game 
 */ 
public class Main extends JFrame 
{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    private boolean isRunning, isGameOver;
    private int fps, windowWidth, windowHeight;
    private Integer score, lastScore;
    
    private ArrayList<Spike> spikes;
    private final int[][] spikesPositions = {
        {210, -520}, {350, -120}, {130, -210},
        {430, -30}, {30, -45}, {280, -60}
    };
        
        
    private BufferedImage backBuffer; 
    private Insets insets;
    private Random random;
    
    private MouseAdapter mouseAdapter;
    private Player player;
    private FontManager fontManager;


    /**
     * Starting point
     * 
     * @param args String arguments passed in command line 
     */
    public static void main(String[] args) 
    {
        LOGGER.setLevel(GameSettings.LOGGER_LEVEL);
        LOGGER.info("Initializing game");
        
        
        
        // Start the game
        Main game = new Main(); 
        game.run(); 
        
        
        System.exit(0);
    } 
        
    /**********************************************************************
     ************************** Private methods ***************************
     **********************************************************************/
        
    /** 
     * This method starts the game and runs it in a loop 
     */ 
    public void run() 
    { 
        
        initializeSettings(); 
                
        while(isRunning) 
            { 
                long time = System.currentTimeMillis(); 
                
                if (isGameOver) 
                    initializeGame();
                
                
                update(); 
                draw(); 
                        
                //  delay for each frame  -   time it took for one frame 
                time = (1000 / fps) - (System.currentTimeMillis() - time); 
                        
                if (time > 0)
                { 
                    try 
                    { 
                        Thread.sleep(time); 
                    } 
                        catch(Exception e)
                        {
                        } 
                    } 
            } 
                
        setVisible(false); 
    } 
        
    /** 
     * This method will set up everything need for the game to run 
     */ 
    private void initializeSettings() 
    { 
        
        String title;
        
        // Get GameSettings config
        fps = GameSettings.FPS;
        windowWidth = GameSettings.WINDOW_WIDTH;
        windowHeight = GameSettings.WINDOW_HEIGHT;
        title = GameSettings.TITLE;
        
 
        setTitle(title);
        setSize(windowWidth, windowHeight);
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setVisible(true); 
        
        insets = getInsets();
        windowWidth = insets.left + windowWidth + insets.right;
        windowHeight = insets.top + windowHeight + insets.bottom;
        setSize(windowWidth, windowHeight);
                
        backBuffer = new BufferedImage(windowWidth, windowHeight,
                BufferedImage.TYPE_INT_RGB);
        
        
        // Update the window width and height with insets
        GameSettings.setWindowWidth(windowWidth);
        GameSettings.setWindowHeight(windowHeight);
        
        // Score counter
        score = 0;
        lastScore = 0;
        
        // Start the big loop
        isRunning = true;
        
        // Add mouse listener
        mouseAdapter = new MouseAdapter();
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        // Set fonts
        fontManager = new FontManager(this);
        
        // Random speed in spikes
        random = new Random();
        
        initializeGame();
    } 
    
    private void initializeGame() {
        // Reset the score
        score = 0;
        
        // Initialize the sprites
        spikes = new ArrayList<>();
        
        // Player sprite
        addPlayer(mouseAdapter.getX(), mouseAdapter.getY());
        
        // Spikes sprite
        addSpikes(spikesPositions);
    }
    
    /**
     * Initialize the player sprite
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void addPlayer(int x, int y) {
        player = new Player(x, y);
    }
    
    /**
     * Initialize our sprites of spike
     * 
     * @param positions positions to spawn spikes
     * 
     */
    private void addSpikes(int[][] positions) {
        for (int[] position : positions) {
            Spike spike;
            int speed;
            
            speed = random.nextInt(5);
            spike = new Spike(position[0], position[1], true);
            
            if (speed == 0)
                spike.setSpeed(5);
            else if (speed == 1)
                spike.setSpeed(6);
            else if (speed == 2)
                spike.setSpeed(7);
            else if (speed == 3)
                spike.setSpeed(8);
            else if (speed == 4)
                spike.setSpeed(9);
            else if (speed == 5)
                spike.setSpeed(10);
            
           
            spikes.add(spike);
            LOGGER.log(Level.FINER, "Spike added: X:{0} Y:{1}", 
                new Object[]{position[0], position[1]});
        }
       
        LOGGER.log(Level.FINER, "Spikes count: {0}", spikes.size());
    }
    
    /**
     * Update coordinates of our player
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void updatePlayer(int x, int y) {
        if (mouseAdapter.isMouseDragging()) {
            LOGGER.log(Level.FINER, "Mouse is dragging: X:{0} Y:{1}", 
                    new Object[]{x, y});
            
            player.setVisible(true);
            player.move(x, y);
            
            if (isGameOver)
                isGameOver = false;
        } else if (mouseAdapter.isMouseMoving()) {
            LOGGER.log(Level.FINER, "Mouse is moving: X:{0} Y:{1}", 
                    new Object[]{x, y});
            
            player.setVisible(false);
            
            if (isGameOver != true)
                isGameOver = true;
        } else if (mouseAdapter.isMouseClicked()) {
            LOGGER.log(Level.FINER, "Mouse is clicked: X:{0} Y:{1}", 
                    new Object[]{x, y});
            
            player.setVisible(false);
            
            if (isGameOver != true)
                isGameOver = true;
        } else if (mouseAdapter.isMouseReleased()) {
            LOGGER.log(Level.FINER, "Mouse is released: X:{0} Y:{1}", 
                    new Object[]{x, y});
            
            player.setVisible(false);
            
            if (isGameOver != true)
                isGameOver = true;
        }
     
    }
    
    /**
     * Update spikes
     */
    private void updateSpikes() {
        
        // NOTE: Fix this
        if (spikes.isEmpty()) {
            LOGGER.info("No more spikes!");
            isGameOver = true;
        }
        
        if (isGameOver == false) {
            for (int i=0; i < spikes.size(); i++) {
                Spike spike = spikes.get(i);
                if (spike.isVisible())
                    spike.move();
                else {
                    spikes.remove(spike);
                    score++;
                }
            } 
        }  
    }
        
    /** 
     * This method will check for input, move things 
     * around and check for win conditions, etc 
     */ 
    private void update() 
    {        
        updatePlayer(mouseAdapter.getX(), mouseAdapter.getY());
        updateSpikes();
        
        checkCollisions();
    }
    
    private void checkCollisions() {
        Rectangle playerBounds;
        
        playerBounds = player.getBounds();
        
        for (Spike spike: spikes) {
            Rectangle spikeBounds;
            
            spikeBounds = spike.getBounds();
            
            if (playerBounds.intersects(spikeBounds))
                isGameOver = true;
            
        }
        
        // Give users time to react
        if (isGameOver) {
            try {
                Thread.sleep(500);
            } catch (Exception e)
            {
            }
        }
    }
        
        
    /**
     * Draw player sprite to frame
     * 
     * @param graphic The Graphics instance
     */
    private void drawPlayer(Graphics graphic) {
        int playerX, playerY;
        
        if (player.isVisible()) {
            playerX = player.getX() - (player.getWidth() / 2);
            playerY = player.getY() - (player.getHeight() / 2);
            graphic.drawImage(player
                    .getImage(), playerX, playerY, this);
        }
    }
    
    /**
     * Draw spikes to frame
     * 
     * @param graphic Where we paint
     */
    private void drawSpikes(Graphics graphic) {
        for (Spike spike: spikes)
            if (spike.isVisible())
                graphic.drawImage(spike.getImage(), spike.getX(), spike.getY(),
                        this);
    }
    
    /**
     * Draw score in top left of screen
     * 
     * @param graphic Kung saan mag pipinta si pintado
     */
    private void drawScore(Graphics graphic) {
        String scoreMsg = score.toString();
        
        graphic.setColor(Color.BLACK);
        graphic.setFont(fontManager.getBigFont());
        graphic.drawString(scoreMsg, 20, 50);
        
    }
    
    /** 
     * Draw game over screen
     * 
     * @param graphic The instance of Graphics class
     */
    private void drawGameOver(Graphics graphic) {
        String gameOverMsg = "Game over!";
        String playAgainMsg = "Press and hold anywhere to play again";
        String scoreMsg = lastScore.toString();
        
        graphic.setColor(Color.BLACK);
        graphic.setFont(fontManager.getBigFont());
        graphic.drawString(scoreMsg,
                (windowWidth - 
                        fontManager.getBigMetrics().stringWidth(scoreMsg)) / 2,
                windowHeight / 2);
        
        graphic.setColor(Color.BLACK);
        graphic.setFont(fontManager.getMediumFont());
        graphic.drawString(gameOverMsg, 
                (windowWidth - 
                    fontManager.getMediumMetrics().stringWidth(gameOverMsg)) / 2,
                (windowHeight / 2) + (50 * 1));
        
        graphic.setColor(Color.red);
        graphic.setFont(fontManager.getSmallFont());
        graphic.drawString(playAgainMsg,
                (windowWidth - 
                    fontManager.getSmallMetrics().stringWidth(playAgainMsg)) / 2,
                (windowHeight / 2) + (50 * 2));
    }
        
    /** 
     * This method will draw everything 
     */ 
    private void draw() 
    {       
        Graphics graphic = getGraphics();
        Graphics bufferGraphic = backBuffer.getGraphics();
        
        bufferGraphic.setColor(Color.WHITE); 
        bufferGraphic.fillRect(0, 0, windowWidth, windowHeight);
        
        if (isGameOver) {
            LOGGER.log(Level.FINER, "Game over!");
            drawGameOver(bufferGraphic);
        } else {
            drawPlayer(bufferGraphic);
            drawSpikes(bufferGraphic);
            drawScore(bufferGraphic);
        }
        
        
        
        graphic.drawImage(backBuffer, insets.left, insets.top, this); 
    }
    
    

}