package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class Fist extends Melee {

    public Fist() {
        setName("Fist");
        setMaxDurability(999);
        setDamageOutput(1);
        setExpRewardForHit(1);
        setExpRewardForKill(2);
        setRepresentation('F');
        setColor(ConsoleSystemInterface.CYAN);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());

        if(getUsesLeft() <= getMaxDurability() - 1)
            setTimesUsed(0);
    }

    @Override
    protected void rewardForKill(Character entity) {
        entity.addExp(getExpRewardForKill());
    }

    @Override
    protected void rewardForHit(Character entity) {
        entity.addExp(getExpRewardForHit());
    }
}
