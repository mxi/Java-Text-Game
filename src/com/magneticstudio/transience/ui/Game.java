package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.Tile;
import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.util.Cache;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
    private Map<Integer, KeyTiming> pressedKeys = new HashMap<>(); // List of pressed keys and the time they're pressed.
    private Map<KeyAction, Method> registeredKeys = new HashMap<>(); // Map of registered keys.
    private boolean processingKeyInput = true; // Whether keyboard input is processed.

    // --- GAME ENVIRONMENT
    private TileSet tileSet; // The tile set for this game.
    private int resolutionWidth = 1280; // The width of the window.
    private int resolutionHeight = 720; // The height of the window.
    private boolean graphicsSetup = false; // Checks whether the graphics have been set up.

    /**
     * Creates a new Game object with the
     * specified title.
     * @param title The title of this window.
     */
    public Game(String title) throws SlickException {
        super(title);
        if(activeGame == null)
            activeGame = this;

        tileSet = new TileSet(60, 60); // Must be in constructor because TileSet requires static Game instance.
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
        resolutionWidth = gc.getWidth();
        resolutionHeight = gc.getHeight();

        /*
         * Registering the KeyActionFunctions
         * to the registered keys for processing.
         */
        for(Method m : this.getClass().getMethods()) {
            KeyAction function = m.getAnnotation(KeyAction.class);
            if(function != null && !registeredKeys.containsKey(function)) {
                registeredKeys.put(function, m);
            }
        }

        // Set up tile set
        tileSet.getTiles().fill(new Tile());
        tileSet.setPixelsPerTile(32);
        tileSet.setAlpha(.5f);
        tileSet.setPixelPerfect();
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        if(processingKeyInput) {
            for(Map.Entry<KeyAction, Method> entry : registeredKeys.entrySet()) {
                Integer key = entry.getKey().key(); // needs to be the wrapper or else the list doesn't know if object or index.
                boolean isDown = Keyboard.isKeyDown(key);

                try {
                    // When the key is first pressed:
                    if(isDown && !pressedKeys.containsKey(key)) {
                        entry.getValue().invoke(this, KeyActionType.PRESS);
                        pressedKeys.put(key, new KeyTiming());
                    }
                    // When the key is held down
                    else if(isDown
                            && pressedKeys.containsKey(key)
                            && System.currentTimeMillis() - pressedKeys.get(key).pressTime > entry.getKey().untilHeld()
                            && System.currentTimeMillis() - pressedKeys.get(key).lastHoldTime > entry.getKey().hold()) {
                        entry.getValue().invoke(this, KeyActionType.HOLD);
                        pressedKeys.get(key).lastHoldTime = System.currentTimeMillis();
                    }
                    // When the key is released.
                    else if(!isDown && pressedKeys.containsKey(key)) {
                        entry.getValue().invoke(this, KeyActionType.RELEASE);
                        pressedKeys.remove(key);
                    }
                }
                catch(IllegalAccessException e) {
                    System.out.println("Illegal access exception: " + e.getMessage());
                }
                catch(InvocationTargetException e) {
                    System.out.println("Invocation target exception: " + e.getMessage());
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

        tileSet.render(graphics, 0, 0, false);
        //graphics.setColor(Color.cyan);
        //graphics.drawLine(resolutionWidth / 2, 0, resolutionWidth / 2, resolutionHeight);
        //graphics.drawLine(0, resolutionHeight / 2, resolutionWidth, resolutionHeight / 2);
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {
        Cache.addFont("resources/fonts/Consolas.ttf", "Map Font", Font.PLAIN, 30);
    }

    /**
     * The function that processes the key
     * action for the 'A' key.
     */
    @KeyAction(key = Input.KEY_A, hold = 250)
    public void aKeyPress(KeyActionType type) {
        if(type != KeyActionType.RELEASE)
            tileSet.getPosition().setTargetX(tileSet.getPosition().getTargetX() - 1);
    }

    /**
     * The function that processes the key
     * action for the 'D' key.
     */
    @KeyAction(key = Input.KEY_D, hold = 250)
    public void dKeyPress(KeyActionType type) {
        if(type != KeyActionType.RELEASE)
            tileSet.getPosition().setTargetX(tileSet.getPosition().getTargetX() + 1);
    }

    /**
     * The function that processes the key
     * action for the 'W' key.
     */
    @KeyAction(key = Input.KEY_W, hold = 250)
    public void wKeyPress(KeyActionType type) {
        if(type != KeyActionType.RELEASE)
            tileSet.getPosition().setTargetY(tileSet.getPosition().getTargetY() - 1);
    }

    /**
     * The function that processes the key
     * action for the 'S' key.
     */
    @KeyAction(key = Input.KEY_S, hold = 250)
    public void sKeyPress(KeyActionType type) {
        if(type != KeyActionType.RELEASE)

            tileSet.getPosition().setTargetY(tileSet.getPosition().getTargetY() + 1);
    }

    /**
     * This class (struct) simply keeps track of when
     * a key was pressed and when the last "hold"
     * operation was performed.
     */
    private static final class KeyTiming {

        private long pressTime = System.currentTimeMillis(); // The time at which the key was pressed.
        private long lastHoldTime = System.currentTimeMillis(); // The last time in milliseconds when
                                                                // the hold operation was performed.
    }
}