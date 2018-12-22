package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Sprite;
import com.magneticstudio.transience.util.IntPoint;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle environmental
 * actions in the game such as particle
 * effects and explosions.
 *
 * @author Max
 */
public class Environment {

    private static Sprite HEAVY_EXPLOSION; // The explosion sprite.

    /**
     * Loads the assets during the
     * init function in the Game class.
     * @throws SlickException In case of an exception during resource loading.
     */
    public static void loadAssets() throws SlickException {
        HEAVY_EXPLOSION = new Sprite(new Image("/resources/textures/fx/heavy-explosion.png"), 100, 100, 120);
        HEAVY_EXPLOSION.setDimensions(250, 250);
    }

    private List<Effect> effects = new ArrayList<>(); // The collection of animations currently running.

    /**
     * Creates an environment object.
     */
    public Environment() {

    }

    /**
     * Creates an explosion effect.
     * @param t The tile set to create the explosion on.
     * @param x The x location of the tile this explosion is located on.
     * @param y The y location of the tile this explosion is located on.
     */
    public void createHeavyExplosion(TileSet t, int x, int y) {
        Effect explosion = new Effect();
        explosion.animation = HEAVY_EXPLOSION.clone();
        explosion.tileLocation = new IntPoint(x, y);
        effects.add(explosion);
    }

    /**
     * Renders all of the effects in
     * the effects list.
     * @param ts The tile set to render on.
     * @param g The graphics to render with.
     */
    public void render(TileSet ts, Graphics g) {
        for(int i = 0; i < effects.size(); i++) {
            Effect e = effects.get(i);
            if(e.animation.isFinished()) {
                effects.remove(i);
                i--;
                continue;
            }
            IntPoint loc = e.tileLocation;
            e.animation.setRenderScale(ts.getScale());
            e.animation.render(g, ts.tileToDisplayLocationX(loc.x), ts.tileToDisplayLocationY(loc.y), true);
        }
    }

    /**
     * This class acts as a struct to provide
     * the location of the animation alongside
     * the animation sprite.
     */
    private static final class Effect {
        private Sprite animation;
        private IntPoint tileLocation;
    }
}
