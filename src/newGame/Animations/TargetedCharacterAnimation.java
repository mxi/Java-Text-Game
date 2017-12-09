package newGame.Animations;

import sz.csi.ConsoleSystemInterface;

/**
 * This class will control an animation on
 * a specified character of the csi.
 */
public class TargetedCharacterAnimation {

    /*
     * Data members of targeted character animation.
     */
    private ConsoleSystemInterface parent;
    private int characterLocationX = 0;
    private int characterLocationY = 0;

    /**
     * Constructor for the TargetedCharacterAnimation that takes in
     * the csi and location of the character to animate.
     * @param csi Csi containing the targeted character.
     * @param x Location X of the targeted square.
     * @param y Location Y of the targeted square.
     */
    public TargetedCharacterAnimation(ConsoleSystemInterface csi, int x, int y) {
        if(csi == null)
            throw new IllegalStateException("CSI cannot be null.");
        parent = csi;
        characterLocationX = x;
        characterLocationY = y;
    }
}
