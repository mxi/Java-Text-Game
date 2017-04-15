package newGame.Entities.Weapons;

import newGame.Entities.Character;

public class Knife extends Melee {

    public static int INIT_DURABILITY = 0x2d; // 45
    public static int HOUSING = 0x3; // 3
    public static int DEGRADATION = 0x5; // 5

    private int expRewardForKill; // XP reward
    private int expRewardForHit; // XP reward

    public Knife(int idamageOutput, int idamageBonus, int irewardForKill, int irewardForHit, int ilevel) {
        super("Knife Level " + ilevel, INIT_DURABILITY, DEGRADATION, HOUSING, idamageOutput, idamageBonus, ilevel);
        expRewardForKill = irewardForKill;
        expRewardForHit = irewardForHit;

        super.addOnUpgradeEvent(() -> {
            setExpUntilLevelUp((int) (getExpUntilLevelUp() * 1.5));
        });
    }

    public int getExpRewardForKill() {
        return expRewardForKill;
    }

    public void setExpRewardForKill(int expRewardForKill) {
        this.expRewardForKill = expRewardForKill;
    }

    public int getExpRewardForHit() {
        return expRewardForHit;
    }

    public void setExpRewardForHit(int expRewardForHit) {
        this.expRewardForHit = expRewardForHit;
    }

    @Override
    protected void rewardForKill(Character character) {

    }

    @Override
    protected void rewardForHit(Character character) {

    }
}
