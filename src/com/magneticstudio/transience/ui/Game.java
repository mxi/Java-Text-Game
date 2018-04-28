package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.Environment;
import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.game.TileSetGenerator;
import com.magneticstudio.transience.util.RadialVignetteGenerator;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * This class simply extends the BasicGame class
 * from the Slick2D library and provides some utility
 * functions to be able to display things onscreen.
 *
 * @author Max
 */
public class Game extends BasicGame {

    // --- STATIC MEMBERS
    public static Random rng = new Random();
    public static Game activeGame = null; // The game currently being played.

    // --- WINDOW/GRAPHICS INFORMATION
    private UnicodeFont fpsNotification;
    private boolean showFps = true; // Whether to show the fps count to the user.
    private int resolutionWidth = 1280; // The width of the window.
    private int resolutionHeight = 720; // The height of the window.
    private boolean graphicsSetup = false; // Tells whether the graphics have been setup.

    private boolean disableInputWhileFading = false; // Whether to disable input while fading.
    private float opacity = 1f; // The opacity of the screen.
    private float modOpacity = 0f; // The opacity modifier.
    private Runnable onFadedOut; // Function that runs when everything has faded out.
    private Runnable onFadedIn; // Function that runs when everything has faded in.

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

    private static final int FADE_TIME_MIN = 50;
    private static final int FADE_TIME_MAX = 5000;

    /**
     * Sets whether to disable input while fading.
     * @param value True to cut input from the user when fading.
     */
    public void disableInputUpdatesWhileFading(boolean value) {
        disableInputWhileFading = value;
    }

    /**
     * Sets a "one time run" function that runs when the game has faded in.
     * @param runnable The runnable to run actions when faded in.
     */
    public void setOnFadedIn(Runnable runnable) {
        onFadedIn = runnable;
    }

    /**
     * Sets a "one time run" function that runs when the game has faded out.
     * @param runnable The runnable to run actions when faded out.
     */
    public void setOnFadedOut(Runnable runnable) {
        onFadedOut = runnable;
    }

    /**
     * Fades out in the given amount of time.
     * @param milliseconds The amount of time given to fade out (change opacity to 0).
     */
    public void fadeOut(int milliseconds) {
        if(modOpacity != 0) // Currently fading in or out.
            return;

        modOpacity = -1f / (float) Math.max(Math.min(FADE_TIME_MAX, milliseconds), FADE_TIME_MIN);
    }

    /**
     * Fades in in the given amount of time.
     * @param milliseconds The amount of time given to fade in (change opacity to 1).
     */
    public void fadeIn(int milliseconds) {
        if(modOpacity != 0) // Currently fading in or out.
            return;

        modOpacity = 1f / (float) Math.max(Math.min(FADE_TIME_MAX, milliseconds), FADE_TIME_MIN);
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

        Environment.loadAssets();
        MenuKeyboard.HOLD_TIME = 500;
        MenuKeyboard.CONSECUTIVE_HOLD_PRESS = 25;
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        if(!graphicsSetup)
            return;

        if(modOpacity != 0) {
            opacity += modOpacity * elapsed;
            if(opacity <= 0) {
                modOpacity = 0;
                opacity = 0;
                if(onFadedOut != null)
                    onFadedOut.run();
                onFadedOut = null;
            }
            else if(opacity >= 1f) {
                modOpacity = 0;
                opacity = 1f;
                if(onFadedIn != null)
                    onFadedIn.run();
                onFadedIn = null;
            }

            if(!disableInputWhileFading) {
                GameKeyboard.poll();
                MenuKeyboard.poll();
                MenuMouse.poll();
            }
        }
        else {
            GameKeyboard.poll();
            MenuKeyboard.poll();
            MenuMouse.poll();
        }

        background.update();
        tileSet.update(elapsed);
    }

    private static final int FPS_SHOW_X = 5; // The X location of the fps counter.
    private static final int FPS_SHOW_Y = 5; // The Y location of the fps counter.

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
        tileSet.render(graphics);

        if(showFps) {
            fpsNotification.drawString(FPS_SHOW_X, FPS_SHOW_Y, Integer.toString(gc.getFPS()));
        }

        // Renders fade ins and outs.
        if(opacity != 1) {
            graphics.setColor(new Color(0f, 0f, 0f, 1f - opacity));
            graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        }

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
        fpsNotification = Res.loadFont("Consolas.ttf", new Color(255, 100, 100, 175), 16, Res.USE_DEFAULT, Res.USE_DEFAULT);

        TileSetGenerator tsGenerator = new TileSetGenerator();
        tsGenerator.setRoomMinWidth(6);
        tsGenerator.setRoomMinHeight(6);
        tsGenerator.setRoomMaxWidth(12);
        tsGenerator.setRoomMaxHeight(12);
        tsGenerator.setRoomClusterSize(TileSetGenerator.ROOM_CLUSTER_SIMPLE);
        tileSet = tsGenerator.generate(20, 20);

        background = new Background(RadialVignetteGenerator.createBackgroundForGame(new Color(00, 120, 120, 150), true));
        background.setMode(Background.Mode.FLOW_POSITION_TRACK);
        background.setInverse(true);
        background.setRoamSpace(1000);

        tileSet.getEntities().getPlayer().getPosition().addListener(background);
    }
}