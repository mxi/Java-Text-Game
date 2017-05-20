package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;

public class Knife extends Melee {

    private boolean bypassShield;

    public Knife() {
        bypassShield = false;
    }

    public boolean doesBypassShield() {
        return bypassShield;
    }

    public void setBypassesShield(boolean val) {
        bypassShield = val;
    }

    @Override
    protected void onAttack(Entity entity) {

    }

    @Override
    protected void rewardForKill(Character character) {
        character.addExp(getExpRewardForKill());
    }

    @Override
    protected void rewardForHit(Character character) {
        character.addExp(getExpRewardForHit());
    }
}
