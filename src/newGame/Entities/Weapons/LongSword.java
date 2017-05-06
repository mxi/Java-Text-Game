package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;

public class LongSword extends Melee {

    public static int INIT_DURABILITY = 145;
    public static int HOUSING = 6;
    public static int DEGRADATION = 7;
    public static float EXP_MULTIPLIER = 1.5f;

    public LongSword(Entity owner, int idamageoutput, int idamageBonus, int irewardForKill, int irewardForHit, int ilevel) {
        super(owner, "Long Sword Level " + ilevel, INIT_DURABILITY, DEGRADATION, HOUSING, idamageoutput, ilevel);

        setExpRewardForKill(irewardForKill);
        setExpRewardForHit(irewardForHit);
    }

    @Override
    public void onKeyPress(int key) {

    }

    @Override
    protected void onMeleeUse() {

    }

    @Override
    protected void onMeleeUpgrade() {

    }

    @Override
    protected void onMeleeDowngrade() {

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
