package newGame.Entities.Weapons;

import newGame.Entities.Character;

public class Knife extends Melee {

    public static int INIT_DURABILITY = 45;
    public static int HOUSING = 3;
    public static int DEGRADATION = 5;
    public static float EXP_MULTIPLIER = 1.5f;

    public Knife(int idamageOutput, int idamageBonus, int irewardForKill, int irewardForHit, int ilevel) {
        super("Knife Level " + ilevel, INIT_DURABILITY, DEGRADATION, HOUSING, idamageOutput, idamageBonus, ilevel);
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
