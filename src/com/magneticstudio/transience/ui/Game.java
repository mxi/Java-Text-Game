package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.Mission;
import com.magneticstudio.transience.util.Cache;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.Font;

/**
 * This class simply extends the BasicGame class
 * from the Slick2D library and provides some utility
 * functions to be able to display things onscreen.
 *
 * @author Max
 */
public class Game extends BasicGame {

    // --- STATIC MEMBERS
    public static Game activeGame = null; // The game currently being played.

    // --- WINDOW/GRAPHICS INFORMATION
    private int resolutionWidth = 1280; // The width of the window.
    private int resolutionHeight = 720; // The height of the window.
    private boolean graphicsSetup = false; // Checks whether the graphics have been set up.

    private Mission mission;

    /**
     * Creates a new Game object with the
     * specified title.
     * @param title The title of this window.
     */
    public Game(String title) throws SlickException {
        super(title);
        if(activeGame == null)
            activeGame = this;
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
     * Functions gets called when the game is ready to begin.
     * @param gc The container of this game.
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        Cache.addFont("resources/fonts/Consolas.ttf", Cache.GENERAL_FONT, Font.PLAIN, 30);

        resolutionWidth = gc.getWidth();
        resolutionHeight = gc.getHeight();

        mission = new Mission();

        GameKeyboard.KEY_COOLDOWN_TIME = 90;
        MenuKeyboard.HOLD_TIME = 500;
        MenuKeyboard.CONSECUTIVE_HOLD_PRESS = 75;
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        GameKeyboard.poll();
        //MenuKeyboard.poll();

        if(GameKeyboard.isTapped(Keyboard.KEY_A)) {
            mission.getTileSet().getPosition().moveX(-1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_D)) {
            mission.getTileSet().getPosition().moveX(1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_W)) {
            mission.getTileSet().getPosition().moveY(-1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_S)) {
            mission.getTileSet().getPosition().moveY(1);
        }

        mission.update(elapsed);
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

        mission.render(graphics);
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {

    }
}