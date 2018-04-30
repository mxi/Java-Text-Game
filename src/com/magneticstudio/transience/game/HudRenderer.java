package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.ui.Res;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Curve;

/**
 * This class provides static utility functions
 * to render the heads up display.
 *
 * @author Max
 */
public class HudRenderer {

    private static final Color HEALTH_COLOR = new Color(220, 0, 0);
    private static final Color HEALTH_COLOR_BG = new Color(200, 0, 0, 125);
    private static final UnicodeFont HEALTH_FONT = Res.loadFont("Consolas.ttf", HEALTH_COLOR, 24, Res.TRUE, Res.FALSE);
    private static final int HEALTH_HUD_PADDING = 100;
    private static final int HEALTH_BAR_WIDTH = 200;
    private static final int HEALTH_BAR_HEIGHT = 10;
    private static final int HEALTH_BAR_TEXT_KERNING = 5;

    /**
     * Renders a health "pie chart" representing
     * the health of something (usually the player).
     * @param graphics The graphics used to render the the health element.
     * @param health The current health of something.
     * @param maxHealth The maximum health of something.
     */
    public static void renderHealth(Graphics graphics, int health, int maxHealth) {
        graphics.setFont(HEALTH_FONT);
        graphics.setColor(HEALTH_COLOR);
        boolean tempAntiAlias = graphics.isAntiAlias();
        int gameResolutionWidth = Game.activeGame.getResolutionWidth();

        graphics.setColor(HEALTH_COLOR);
        String strHealthNum = Integer.toString(health);
        int strWidth = HEALTH_FONT.getWidth(strHealthNum);
        int strHeight = HEALTH_FONT.getHeight(strHealthNum);
        int textLocationX = gameResolutionWidth - strWidth - HEALTH_HUD_PADDING;
        int textLocationY = HEALTH_HUD_PADDING;
        graphics.drawString(strHealthNum, textLocationX, textLocationY);

        graphics.setAntiAlias(false);
        int healthBarWidth = (HEALTH_BAR_WIDTH * health) / maxHealth;
        int healthBarLocationX = gameResolutionWidth - healthBarWidth - HEALTH_HUD_PADDING;
        int healthBarLocationY = HEALTH_HUD_PADDING + strHeight + HEALTH_BAR_TEXT_KERNING;
        graphics.fillRoundRect(healthBarLocationX, healthBarLocationY, healthBarWidth, HEALTH_BAR_HEIGHT, 2);

        graphics.setColor(HEALTH_COLOR_BG);
        graphics.fillRoundRect(
            healthBarLocationX - (HEALTH_BAR_WIDTH - healthBarWidth),
            healthBarLocationY,
            200 - healthBarWidth,
            HEALTH_BAR_HEIGHT,
            2
        );
        graphics.setAntiAlias(tempAntiAlias);
    }

    /**
     * Creates the first half of the health circle
     * using percentages.
     * @param amt The health given.
     * @param max The maximum health.
     * @return The first half of the circle.
     */
    private static Curve createFirstHalfOfHealthArc(float amt, float max) {
        return null;
    }
}
