package com.magneticstudio.transience.ui;

import com.magneticstudio.transience.devkit.CommonKt;
import com.magneticstudio.transience.devkit.Shader;
import com.magneticstudio.transience.devkit.Texture2;
import com.magneticstudio.transience.game.Environment;
import com.magneticstudio.transience.game.TileSet;
import com.magneticstudio.transience.game.TileSetGenerator;
import com.magneticstudio.transience.util.RadialVignetteGenerator;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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
    private Image vignette;
    private float vignetteScale = 1f;
    private float vignetteTarget = 0f;
    private float vignetteScaleMod = 0f;
    private boolean showVignette = true;

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
     * Whether to show the vignette.
     * @param v Whether to show the vignette or not.
     */
    public void setShowVignette(boolean v) {
        showVignette = v;
    }

    /**
     * Sets the scale of the vignette in the specified amount of time.
     * @param scale The new scale.
     * @param milliseconds The time in milliseconds to achieve the scale.
     */
    public void setVignetteScale(float scale, int milliseconds) {
        vignetteTarget = Math.max(Math.min(scale, 3f), 0.25f);
        vignetteScaleMod = (vignetteTarget - vignetteScale) / (float) milliseconds;
        System.out.println(vignetteScaleMod);
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

    private Shader triangleShader;
    private Texture2 woodTexture;

    private int vertexArr;
    private int vertexBuff;
    private int vertexElems;

    /**
     * Functions gets called when the game is ready to begin.
     * @param gc The container of this game.
     * @throws RuntimeException If a runtime exception occurs.
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        triangleShader = Shader.Factory.loadFromJar("test");
        woodTexture = Texture2.Factory.loadFromJar("wood.jpg", "test");

        FloatBuffer vertexData = BufferUtils.createFloatBuffer((3 * 3) + (3 * 2));
        vertexData.put(new float[] {
           // Positions:        // Texture Coords:
           -0.5f, -0.5f, 0.0f,  0.0f, 0.0f,
            0.0f,  0.5f, 0.0f,  0.5f, 1.0f,
            0.5f, -0.5f, 0.0f,  1.0f, 0.0f
        });
        vertexData.flip();

        ByteBuffer vertexElements = BufferUtils.createByteBuffer(3);
        vertexElements.put(new byte[] {
            0, 1, 2
        });
        vertexElements.flip();

        vertexArr = glGenVertexArrays();
        glBindVertexArray(vertexArr);

        vertexBuff = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuff);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        vertexElems = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vertexElems);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, vertexElements, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 5, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 5, Float.BYTES * 3);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

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

        handleNumberAnimationsAndStuff(gc, elapsed);

        background.update();
        tileSet.update(elapsed);
    }

    /**
     * Handles numerical animations for fades
     * and vignette.
     * @param gc The game container.
     * @param elapsed The elapsed time.
     */
    private void handleNumberAnimationsAndStuff(GameContainer gc, int elapsed) {
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

        if(vignetteScaleMod != 0) {
            vignetteScale += vignetteScaleMod * elapsed;
            if((vignetteScaleMod < 0 && vignetteScale < vignetteTarget)
               || (vignetteScaleMod > 0 && vignetteScale > vignetteTarget)) {
                vignetteScale = vignetteTarget;
                vignetteScaleMod = 0f;
            }
        }
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
        graphics.setAntiAlias(true);
        if(!graphicsSetup) {
            setupGraphics();
            graphicsSetup = true;
        }

        background.render(graphics);
        tileSet.render(graphics);

        // Renders fade ins and outs.
        if(opacity != 1) {
            graphics.setColor(new Color(0f, 0f, 0f, 1f - opacity));
            graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        }

        // Renders the main vignette
        if(showVignette) {
            Image vignetteImage = vignetteScale != 1f ? vignette.getScaledCopy(vignetteScale) : vignette;
            int vx = (resolutionWidth / 2) - (vignetteImage.getWidth() / 2);
            int vy = (resolutionHeight / 2) - (vignetteImage.getHeight() / 2);
            graphics.drawImage(
                 vignetteImage,
                 vx,
                 vy
            );
            graphics.setColor(Color.black);
            if(vx > 0) {
                graphics.fillRect(0, 0, vx, resolutionHeight);
                graphics.fillRect(resolutionWidth - vx, 0, vx, resolutionHeight);
            }
            if(vy > 0) {
                graphics.fillRect(vx, 0, resolutionWidth - (vx * 2), vy);
                graphics.fillRect(vx, resolutionHeight - vy, resolutionWidth - (vx * 2), vy);
            }
        }

        if(showFps) {
            fpsNotification.drawString(FPS_SHOW_X, FPS_SHOW_Y, Integer.toString(gc.getFPS()));
        }

        tileSet.postRender(graphics);

        triangleShader.bind();
        woodTexture.bind();

        triangleShader.uniformTex2D("U_Tex", woodTexture);

        glBindVertexArray(vertexArr);
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_BYTE, 0);

        int error = 0;
        while ((error = glGetError()) != GL_NO_ERROR) {
            System.out.println("Err: " + error);
        }

        triangleShader.unbind();
        woodTexture.unbind();
    }

    /**
     * Sets up the graphics portion of the game
     * such as creating the effects on images
     * and such.
     */
    private void setupGraphics() throws SlickException {
        fpsNotification = Res.loadFont("Consolas.ttf", new Color(255, 100, 100, 175), 16, Res.USE_DEFAULT, Res.USE_DEFAULT);

        RadialVignetteGenerator vignetteGenerator = new RadialVignetteGenerator();
        vignetteGenerator.setImageWidth(600);
        vignetteGenerator.setImageWidth(600);
        vignetteGenerator.setSoftness(1f);
        vignetteGenerator.setRadius(300);
        vignetteGenerator.setCenterX(300);
        vignetteGenerator.setCenterY(300);
        vignetteGenerator.setColor(Color.black);
        vignette = vignetteGenerator.generate();

        TileSetGenerator tsGenerator = new TileSetGenerator();
        tsGenerator.setRoomMinWidth(6);
        tsGenerator.setRoomMinHeight(6);
        tsGenerator.setRoomMaxWidth(12);
        tsGenerator.setRoomMaxHeight(12);
        tsGenerator.setRoomClusterSize(TileSetGenerator.ROOM_CLUSTER_SIMPLE);
        tileSet = tsGenerator.generate(30, 30);

        background = new Background(RadialVignetteGenerator.createBackgroundForGame(new Color(00, 120, 120, 150), true));
        background.setMode(Background.Mode.FLOW_POSITION_TRACK);
        background.setInverse(true);
        background.setRoamSpace(1000);

        tileSet.getEntities().getPlayer().getPosition().addListener(background);
    }
}