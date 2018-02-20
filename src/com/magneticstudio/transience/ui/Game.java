package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.util.Cache;
import com.magneticstudio.transience.util.RadialVignetteGenerator;
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

    private Background background;
    private UIWelcomeMenu welcome;

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
        Cache.addFont(Cache.FONT_RESOURCE_FOLDER + "Consolas.ttf", Cache.DEFAULT_FONT, Font.PLAIN, 16);

        resolutionWidth = gc.getWidth();
        resolutionHeight = gc.getHeight();

        MenuKeyboard.HOLD_TIME = 500;
        MenuKeyboard.CONSECUTIVE_HOLD_PRESS = 75;

        gc.setShowFPS(false);
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        MenuKeyboard.poll();
        MenuMouse.poll();

        background.update();
        welcome.update();
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
        welcome.render(graphics);
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {
        Image image = new Image(1280, 720);
        Graphics g = image.getGraphics();

        RadialVignetteGenerator generator = new RadialVignetteGenerator();
        generator.setCenterX(1280 / 2);
        generator.setCenterY(720 / 2);
        generator.setImageWidth(1280);
        generator.setImageHeight(720);
        generator.setRadius(1280 * 2);
        generator.setSoftness(1f);
        generator.setInverted(true);
        generator.setColor(new Color(79, 120, 140, 255));

        Image vignette = generator.generate();

        g.setColor(new Color(50, 50, 50));
        g.drawRect(0, 0, 1280, 720);
        g.drawImage(vignette, 0, 0);

        background = new Background(image);
        welcome = new UIWelcomeMenu();
    }
}