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
import com.idiotdodger.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Main class for the game 
 */ 
public class Main extends JFrame 
{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    private boolean isRunning, isGameOver, isInMenu;
    private int fps, windowWidth, windowHeight, lastSpawnPosition;
    private long lastSpawnTime;
    private Integer score, lastScore;
    
    private ArrayList<Spike> spikes;

        
    private BufferedImage backBuffer; 
    private Insets insets;
    private Random random;
    private Image backgroundImage, titleImage;

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
        // Setup logger
        // Create handler for logger
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(GameSettings.LOGGER_LEVEL);

        // Set level and use the created handler
        LOGGER.setLevel(GameSettings.LOGGER_LEVEL);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setUseParentHandlers(false);


        // Start the game
        LOGGER.info("Starting game");
        Main game = new Main();
        game.run(); 
        
        
        System.exit(0);
    }


    /**********************************************************************
     *************************** Public methods ***************************
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
                catch(Exception ignored)
                {
                }
            }
        }

        setVisible(false);
    }



    /**********************************************************************
     ************************** Private methods ***************************
     **********************************************************************/
        

    /** 
     * This method will set up everything need for the game to run 
     */ 
    private void initializeSettings() 
    {
        LOGGER.info("Setting up game setttings");
        
        String title;
        
        // Get GameSettings config
        fps = GameSettings.FPS;
        windowWidth = GameSettings.WINDOW_WIDTH;
        windowHeight = GameSettings.WINDOW_HEIGHT;
        title = GameSettings.TITLE;

        // Set the appropriate settings for the frame
        setTitle(title);
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        // Update the windowWidth and windowHeight with insets of the frame
        insets = getInsets();

        windowWidth = insets.left + windowWidth + insets.right;
        windowHeight = insets.top + windowHeight + insets.bottom;

        setSize(windowWidth, windowHeight);

        GameSettings.setWindowWidth(windowWidth);
        GameSettings.setWindowHeight(windowHeight);


        backBuffer = new BufferedImage(windowWidth, windowHeight,
                BufferedImage.TYPE_INT_RGB);

        // Score counter
        score = 0;
        lastScore = 0;
        
        // Start the big loop
        isRunning = true;

        isGameOver = false;

        isInMenu = true;
        
        // Add mouse listener
        mouseAdapter = new MouseAdapter();
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        // Fonts to use in drawString, etc
        fontManager = new FontManager(this);

        // Initialize sound manager for sound effects
        SoundManager.init();
        SoundManager.volume = SoundManager.Volume.HIGH;

        // Random speed in spikes
        random = new Random();

        // Initialize images to use
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(
                GameSettings.BACKGROUND_IMAGE
        )).getImage();
        titleImage = new ImageIcon(getClass().getClassLoader().getResource(
                GameSettings.TITLE_IMAGE
        )).getImage();
        
        initializeGame();
    } 

    /**
     * Reset the settings to restart the game
     * */
    private void initializeGame() {
        // Reset the score
        score = 0;
        
        // Initialize the sprites
        spikes = new ArrayList<>();
        
        // Player sprite
        if (player == null) {
            LOGGER.fine("Adding player");
            addPlayer(mouseAdapter.getX(), mouseAdapter.getY());
        }

        if (!SoundManager.MENU.isRunning())
            SoundManager.MENU.playForever();

        spawnSpike();
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
     * Spawn spike at random position at a constant time
     *
     * */
    private void spawnSpike() {
        if (isGameOver)
            return;

        int randomX, randomSpeed, diffInPosition;
        Spike spike;

        randomSpeed = random.nextInt(10) + 5;
        randomX = random.nextInt(windowWidth - 100);
        diffInPosition = randomX - lastSpawnPosition;

        if (diffInPosition <= 100 && diffInPosition >= -100) {
            if (randomX < 0)
                randomX = 0;
            else if (randomX > 0) {
                randomX += 50;
            }
        }


        spike = new Spike(randomX, -100, true);
        spike.setSpeed(randomSpeed);

        spikes.add(spike);

        lastSpawnPosition = randomX;
        lastSpawnTime = System.currentTimeMillis();
    }



    /**
     * Check collisions between all the objects in the frame
     * */
    private void checkCollisions() {
        LOGGER.finer("Checking collisions");

        Rectangle playerBounds;

        playerBounds = new Rectangle(
                player.getX() - (player.getWidth() / 2),
                player.getY() - ((player.getHeight() / 2) + 10),
                player.getWidth(),
                player.getHeight()
        );

        // Check collisions
        for (Spike spike: spikes) {
            Rectangle spikeBounds;

            spikeBounds = spike.getBounds();

            if (playerBounds.intersects(spikeBounds)) {
                isGameOver = true;

                if (!SoundManager.DEAD.isRunning())
                    SoundManager.DEAD.play();
            }
        }

        // Give users time to react
        if (isGameOver) {
            try {
                Thread.sleep(800);
            } catch (Exception ignored)
            {
            }
        }
    }



    /**************************************************************************
     ****************************** Update methods ******************************
     **************************************************************************/


    /**
     * This method will check for input, move things
     * around and check for win conditions, etc
     */
    private void update()
    {
        LOGGER.fine("Updating");

        updatePlayer(mouseAdapter.getX(), mouseAdapter.getY());
        updateSpikes();

        checkCollisions();
    }

    
    /**
     * Update coordinates of our player
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void updatePlayer(int x, int y) {
        LOGGER.finer("Updating player");

        if (mouseAdapter.isMouseDragging()) {
            LOGGER.log(Level.FINE, "Mouse is dragging: X:{0} Y:{1}",
                    new Object[]{x, y});

            if (!player.isVisible())
                player.setVisible(true);

            player.move(x, y);

            // Stop the menu sound
            if (SoundManager.MENU.isRunning())
                SoundManager.MENU.stop();

            // Play background music
            if (!SoundManager.ONGAME.isRunning())
                SoundManager.ONGAME.playForever();

            // Set last score to show in game over screen
            if (!score.equals(lastScore))
                lastScore = score;

            if (isInMenu)
                isInMenu = false;

            if (isGameOver)
                isGameOver = false;
        } else if (mouseAdapter.isMouseMoving()) {
            LOGGER.log(Level.FINE, "Mouse is moving: X:{0} Y:{1}",
                    new Object[]{x, y});

            // Change the screen to menu screen and play sound
            if (!SoundManager.MENU.isRunning())
                SoundManager.MENU.playForever();

            if (player.isVisible())
                player.setVisible(false);

            if (!isGameOver)
                isGameOver = true;
        } else if (mouseAdapter.isMouseClicked()) {
            LOGGER.log(Level.FINE, "Mouse is clicked: X:{0} Y:{1}",
                    new Object[]{x, y});

            SoundManager.CLICK.play();

            if (player.isVisible())
                player.setVisible(false);


            if (!isInMenu)
                isInMenu = true;

            if (!isGameOver)
                isGameOver = true;
        } else if (mouseAdapter.isMouseReleased()) {
            LOGGER.log(Level.FINE, "Mouse is released: X:{0} Y:{1}",
                    new Object[]{x, y});

            if (!SoundManager.DEAD.isRunning())
                SoundManager.DEAD.play();

            SoundManager.ONGAME.stop();

            if (player.isVisible())
                player.setVisible(false);

            if (!isGameOver)
                isGameOver = true;
        }
     
    }


    /**
     * Update spikes
     */
    private void updateSpikes() {
        LOGGER.finer("Updating spikes");

        // Add spike
        if (System.currentTimeMillis() - lastSpawnTime >= 1000) {
            LOGGER.finer("Spawning spike");
            spawnSpike();
        }

        if (spikes.isEmpty()) {
            LOGGER.finer("No more spikes!");
            isGameOver = true;
        }
        
        if (!isGameOver) {
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





    /**************************************************************************
     ****************************** Draw methods ******************************
     **************************************************************************/


    /**
     * This method will draw everything
     */
    private void draw()
    {
        LOGGER.fine("Drawing");
        Graphics graphic = getGraphics();
        Graphics bufferGraphic = backBuffer.getGraphics();

        bufferGraphic.drawImage(backgroundImage, 0, 0, this);

        if (isInMenu) {
            LOGGER.finer("Menu screen");
            drawMenu(bufferGraphic);
        }
        else if (isGameOver) {
            LOGGER.finer("Game over!");
            drawGameOver(bufferGraphic);
        } else {
            drawPlayer(bufferGraphic);
            drawSpikes(bufferGraphic);
            drawScore(bufferGraphic);
        }


        graphic.drawImage(backBuffer, insets.left, insets.top, this);
    }


    /**
     * Draw player sprite to frame
     * 
     * @param graphic The Graphics instance
     */
    private void drawPlayer(Graphics graphic) {
        LOGGER.finer("Drawing player");

        int playerX, playerY;
        
        if (player.isVisible()) {
            playerX = player.getX() - (player.getWidth() / 2);
            playerY = player.getY() - ((player.getHeight() / 2) + 15);
            graphic.drawImage(player.getImage(), playerX, playerY, this);
        }
    }
    
    /**
     * Draw all the visible spikes in to frame
     * 
     * @param graphic Where we paint
     */
    private void drawSpikes(Graphics graphic) {
        LOGGER.finer("Drawing spikes");

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
        LOGGER.finer("Drawing score");

        String scoreMsg = score.toString();
        
        graphic.setColor(Color.WHITE);
        graphic.setFont(fontManager.getMediumFont());
        graphic.drawString(scoreMsg, 20, 50);
    }


    /** 
     * Draw game over screen
     * 
     * @param graphic The instance of Graphics class
     */
    private void drawGameOver(Graphics graphic) {
        LOGGER.finer("Drawing game over");

        String gameOverMsg = "Game over!";
        String playAgainMsg = "Click and hold anywhere to play again";
        String scoreMsg = lastScore.toString();
        
        graphic.setColor(Color.WHITE);
        graphic.setFont(fontManager.getBigFont());
        graphic.drawString(scoreMsg,
                (windowWidth - 
                        fontManager.getBigMetrics().stringWidth(scoreMsg)) / 2,
                windowHeight / 2 - 50);
        
        graphic.setColor(Color.WHITE);
        graphic.setFont(fontManager.getMediumFont());
        graphic.drawString(gameOverMsg, 
                (windowWidth -
                    fontManager.getMediumMetrics().stringWidth(gameOverMsg)) / 2,
                windowHeight / 2 );
        
        graphic.setColor(new Color(201, 16, 52));
        graphic.setFont(fontManager.getSmallFont());
        graphic.drawString(playAgainMsg,
                (windowWidth - 
                    fontManager.getSmallMetrics().stringWidth(playAgainMsg)) / 2,
                (windowHeight / 2) + 50);
    }


    /**
     * Draw menu screen
     *
     * @param graphic Graphics instance
     * */
    private void drawMenu(Graphics graphic) {
        String playAgainMsg = "Click and hold anywhere to play again";

        graphic.drawImage(titleImage,
                (windowWidth / 2) - (titleImage.getWidth(null) / 2),
                (windowHeight / 2) - 100,
                this);


        graphic.setColor(new Color(201, 16, 52));
        graphic.setFont(fontManager.getSmallFont());
        graphic.drawString(playAgainMsg,
                (windowWidth -
                        fontManager.getSmallMetrics().stringWidth(playAgainMsg)) / 2,
                (windowHeight / 2) + 50);
    }
}