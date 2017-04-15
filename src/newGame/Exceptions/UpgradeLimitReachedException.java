package newGame.Exceptions;

import newGame.Entities.Entity;

public class UpgradeLimitReachedException extends Exception {

    public UpgradeLimitReachedException() {
        super("Upgrade limit reached for the entity.");
    }

    public UpgradeLimitReachedException(String message) {
        super(message);
    }

    public UpgradeLimitReachedException(Entity entity) {
        super("Upgrade limit reached for the entity \"" + entity.getName() + "\". ("
                + (entity.getLevel() + 1) + " > " + entity.getMaxLevel());
    }

    public UpgradeLimitReachedException(int maxLevel, int levelToUpgrade) {
        super("Upgrade limit reached for the entity. (" + levelToUpgrade + " > " + maxLevel);
    }

}
