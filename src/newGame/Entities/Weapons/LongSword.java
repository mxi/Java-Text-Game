package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;

public class LongSword extends Melee {

    public LongSword() {
        setSwingRange(2);
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
