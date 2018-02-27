package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.util.RadialVignetteGenerator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.GameState;

import java.util.ArrayList;

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

    private TileSet tileSet;
    private Background background;

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
        resolutionWidth = gc.getWidth();
        resolutionHeight = gc.getHeight();

        MenuKeyboard.HOLD_TIME = 500;
        MenuKeyboard.CONSECUTIVE_HOLD_PRESS = 25;

        GameKeyboard.KEY_COOLDOWN_TIME = 105;
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

        if(GameKeyboard.isTapped(Keyboard.KEY_A)) {
            tileSet.getPosition().moveX(-1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_D)) {
            tileSet.getPosition().moveX(1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_W)) {
            tileSet.getPosition().moveY(-1);
        }
        else if(GameKeyboard.isTapped(Keyboard.KEY_S)) {
            tileSet.getPosition().moveY(1);
        }

        background.update();
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

        background.render(graphics);
        tileSet.render(new ArrayList<>(), graphics);
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {
        RadialVignetteGenerator generator = new RadialVignetteGenerator();
        generator.setCenterX(1280 / 2);
        generator.setCenterY(720 / 2);
        generator.setImageWidth(1280);
        generator.setImageHeight(720);
        generator.setRadius(1280);
        generator.setSoftness(1f);
        generator.setInverted(true);
        generator.setColor(new Color(120, 120, 120, 255));

        background = new Background(generator.generate());
        tileSet = new TileSet(30, 30);
        tileSet.getPosition().setTransitionTime(100);
    }
}