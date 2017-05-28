package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class LongSword extends Melee {

    public LongSword() {
        setSwingRange(2);
        setName("Longsword");
        setRepresentation('L');
        setColor(ConsoleSystemInterface.TEAL);

        setDamageOutput(2);
        setSwingRange(2);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
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
