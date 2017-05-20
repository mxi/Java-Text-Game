package newGame.Entities;

import newGame.MainGame;

public enum ShieldType {

    Leather("Leather", 10, 5, 1, 0.96f),
    Plastic("Plastic", 5, 5, 1, 1.02f),
    Wood("Wood", 15, 10, 1, 1.09f),
    Bone("Bone", 20, 15, 1, 1.15f),
    Paper("Paper", 5, 50, 1, 1.27f),
    Glass("Glass", 50, -5, 1, 1.35f),
    Copper("Copper", 20, 5, 1, 1.46f),
    Brass("Brass", 15, 20, 1, 1.66f),
    Gold("Gold", 5, 5, 1, 1.79f),
    Iron("Iron", 25, 40, 1, 1.85f),
    Steel("Steel", 50, 20, 1, 1.92f);

    public static int MaxLevel = 15;

    public String Name;
    public int MissChance;
    public int DamageAbsorption;
    public int Level;
    public float UpgradeSpeed;
    ShieldType(String iname, int imissChance, int idamageAbsorption, int ilevel, float iupgradeSpeed) {
        Name = iname;
        MissChance = imissChance;
        DamageAbsorption = idamageAbsorption;
        Level = ilevel;
        UpgradeSpeed = iupgradeSpeed;
    }

    public boolean wouldHit(int istrength) {
        return MainGame.random.nextInt(101) > ((MissChance / istrength) * UpgradeSpeed) + (2 * Level);
    }

    public int calcNewDamage(int idamage) {
        return (int) ((100 - (DamageAbsorption * Level * UpgradeSpeed)) * idamage / 100);
    }

    public void levelUp() {
        if(Level + 1 <= MaxLevel)
            Level++;
    }
}