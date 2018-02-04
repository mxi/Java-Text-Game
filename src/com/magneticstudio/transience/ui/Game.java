package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.Tile;
import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.util.Cache;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class simply extends the BasicGame class
 * from the Slick2D library and provides some utility
 * functions to be able to display things onscreen.
 *
 * @author Max
 */
public class Game extends BasicGame {

    // --- GLOBAL GAME OBJECT
    public static Game activeGame = null; // The game currently being played.

    // --- CONTROLS
    private List<Integer> pressedKeys = new ArrayList<>(); // List of pressed keys.
    private Map<Integer, KeyAction> registeredKeys = new HashMap<>(); // Map of registered keys.
    private boolean processingKeyInput = true; // Whether keyboard input is processed.

    // --- GAME ENVIRONMENT
    private TileSet tileSet; // The tile set for this game.
    private int resolutionWidth = 1280; // The width of the window.
    private int resolutionHeight = 720; // The height of the window.
    private boolean graphicsSetup = false; // Checks whether the graphics have been set up.

    private GraphicalElement element;

    /**
     * Creates a new Game object with the
     * specified title.
     * @param title The title of this window.
     */
    public Game(String title) throws SlickException {
        super(title);
        if(activeGame == null)
            activeGame = this;

        tileSet = new TileSet(19, 19); // Must be in constructor because TileSet requires static Game instance.
    }

    /**
     * Gets the resolution width.
     * @return Resolution width.
     */
    public int getResolutionWidth() {
        return resolutionWidth;
    }

    /**
     * Gets the resolution height.
     * @return Resolution height.
     */
    public int getResolutionHeight() {
        return resolutionHeight;
    }

    /**
     * Registers a key to process.
     * @param id The id of the key (Input.<...>)
     * @param action The interface that contains actions for key events.
     */
    public void registerKey(int id, KeyAction action) {
        registeredKeys.putIfAbsent(id, action);
    }

    /**
     * Checks whether this Game object is processing
     * keyboard input.
     * @return Whether keyboard input is processed.
     */
    public boolean isProcessingKeyboardInput() {
        return processingKeyInput;
    }

    /**
     * Sets whether this Game object is processing keyboard
     * input.
     * @param value True if processing; false if not processing.
     */
    public void setProcessingKeyboardInput(boolean value) {
        processingKeyInput = value;
        if(!processingKeyInput)
            pressedKeys.clear();
    }

    /**
     * Functions gets called when the game is ready to begin.
     * @param gc The container of this game.
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        registerKey(Input.KEY_A, new KeyAction() {
            @Override
            public void onPress() {
                tileSet.setHorizontalTileOffset(tileSet.getHorizontalTileOffset() - 1);
            }

            @Override
            public void onRelease() {

            }
        });

        registerKey(Input.KEY_D, new KeyAction() {
            @Override
            public void onPress() {
                tileSet.setHorizontalTileOffset(tileSet.getHorizontalTileOffset() + 1);
            }

            @Override
            public void onRelease() {

            }
        });

        registerKey(Input.KEY_S, new KeyAction() {
            @Override
            public void onPress() {
                tileSet.setVerticalTileOffset(tileSet.getVerticalTileOffset() - 1);
            }

            @Override
            public void onRelease() {

            }
        });

        registerKey(Input.KEY_W, new KeyAction() {
            @Override
            public void onPress() {
                tileSet.setVerticalTileOffset(tileSet.getVerticalTileOffset() + 1);
            }

            @Override
            public void onRelease() {

            }
        });

        // Set up tile set
        tileSet.getTiles().fill(new Tile());
        tileSet.setTileCountHorizontally(33);
        tileSet.setAlpha(.5f);
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        resolutionWidth = gc.getWidth();
        resolutionHeight = gc.getHeight();

        if(processingKeyInput) {
            for(Map.Entry<Integer, KeyAction> entry : registeredKeys.entrySet()) {
                Integer key = entry.getKey(); // needs to be the wrapper or else the list doesn't know if object or index.
                boolean isDown = Keyboard.isKeyDown(key);

                if(isDown && !pressedKeys.contains(key)) {
                    entry.getValue().onPress();
                    pressedKeys.add(key);
                }
                else if(!isDown && pressedKeys.contains(key)) {
                    entry.getValue().onRelease();
                    pressedKeys.remove(key);
                }
            }
        }

        tileSet.update(elapsed);
    }

    /**
     * Function gets called as many times as possible (without VSync)
     * to render all of the elements onto the screen.
     * @param gc The container of this game.
     * @param graphics The graphics object used to draw onto the screen.
     * @throws RuntimeException If a runtime error occurs.
     */
    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException {
        if(!graphicsSetup) {
            setupGraphics();
            graphicsSetup = true;
        }

        element.render(100, 100, true);
        //tileSet.render(graphics, 0, 0, false);
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {
        Cache.addFont("resources/fonts/Consolas.ttf", "Consolas", Font.PLAIN, 25);
        element = new CharacterCell("Consolas", 'l');
    }
}