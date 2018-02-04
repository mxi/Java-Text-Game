package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.IntDimension;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;

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

    // --- GENERAL
    private List<Integer> pressedKeys = new ArrayList<>(); // List of pressed keys.
    private Map<Integer, Runnable> keyTrackers = new HashMap<>(); // Map of key trackers.

    private boolean processKeyInput = true; // Whether to process input in the update() function.

    // --- GAME RELATED
    private ArrayList2D<Tile> tiles = new ArrayList2D<>(); // The 2d resize-able list of tiles.

    /**
     * Creates a new Game object with the
     * specified title.
     * @param title The title of this window.
     */
    public Game(String title) throws SlickException {
        super(title);
    }

    /**
     * Registers a key to track and call the
     * onPress runnable whenever the key is pressed.
     * @param toTrack The key to track.
     * @param onPress Lambda to run on press of this key.
     * @see org.newdawn.slick.Input List of input ids.
     */
    public void addKeyTracker(int toTrack, Runnable onPress) {
        keyTrackers.putIfAbsent(toTrack, onPress);
    }

    /**
     * Checks whether this Game object processes
     * keyboard input or ignores it.
     * @return Whether keyboard input is ignored or processed.
     */
    public boolean isProcessingKeyboardInput() {
        return processKeyInput;
    }

    /**
     * Sets whether keyboard input is processed or not.
     * @param isProcessing True to process keyboard input; false to ignore it.
     */
    public void setProcessingKeyboardInput(boolean isProcessing) {
        processKeyInput = isProcessing;
    }

    /**
     * Functions gets called when the game is ready to begin.
     * @param gc The container of this game.
     * @throws SlickException If a slick2d error occurs.
     */
    @Override
    public void init(GameContainer gc) throws SlickException {

    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws SlickException If a slick2d error occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        if(processKeyInput) {
            for(Map.Entry<Integer, Runnable> tracker : keyTrackers.entrySet()) {
                Integer key = tracker.getKey(); // Must be the int wrapper so that list.remove() works.
                boolean isPressed = Keyboard.isKeyDown(key);

                if(isPressed && !pressedKeys.contains(key)) {
                    pressedKeys.add(key);
                    tracker.getValue().run();
                }
                else if(!isPressed && pressedKeys.contains(key)) {
                    pressedKeys.remove(key);
                }
            }
        }
    }

    /**
     * Function gets called as many times as possible (without VSync)
     * to render all of the elements onto the screen.
     * @param gc The container of this game.
     * @param graphics The graphics object used to draw onto the screen.
     * @throws SlickException If a slick2d error occurs.
     */
    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException {

    }
}