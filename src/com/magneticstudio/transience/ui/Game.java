package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.game.Player;
import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.game.TileSetGenerator;
import com.magneticstudio.transience.util.RadialVignetteGenerator;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
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
    }

    /**
     * Function gets called every 1000 milliseconds to update the game.
     * @param gc The container of this game.
     * @param elapsed The elapsed time in milliseconds
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void update(GameContainer gc, int elapsed) throws SlickException {
        if(!graphicsSetup) // Don't update anything until the graphics are setup.
            return;

        GameKeyboard.poll();

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
        tsGenerator.setSize(TileSet.MEDIUM);
        tsGenerator.setRoomMinWidth(6);
        tsGenerator.setRoomMinHeight(6);
        tsGenerator.setRoomMaxWidth(12);
        tsGenerator.setRoomMaxHeight(12);
        tsGenerator.setRoomClusterSize(TileSetGenerator.ROOM_CLUSTER_SIMPLE);
        tileSet = tsGenerator.generate(30, 30);

        background = new Background(RadialVignetteGenerator.createBackgroundForGame(new Color(125, 60, 60, 150), true));
        background.setMode(Background.Mode.FLOW_POSITION_TRACK);
        background.setInversed(true);
        background.setRoamSpace(500);

        tileSet.getEntities().getPlayer().getPosition().addListener(background);
    }
}