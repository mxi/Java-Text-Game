package newGame.Entities;

import newGame.MainGame;

public enum Shield {
    Leather("Leather", 10, 5, 1),
    Plastic("Plastic", 5, 5, 1),
    Wood("Wood", 15, 10, 1),
    Bone("Bone", 20, 15, 1),
    Paper("Paper", 5, 50, 1),
    Glass("Glass", 50, -5, 1),
    Copper("Copper", 20, 5, 1),
    Brass("Brass", 15, 20, 1),
    Gold("Gold", 5, 5, 1),
    Iron("Iron", 25, 40, 1),
    Steel("Steel", 50, 20, 1);

    public static int MaxLevel = 15;
    public String Name;
    public int MissChance;
    public int DamageAbsorption;
    public int Level;
    Shield(String iname, int imissChance, int idamageAbsorption, int ilevel) {
        Name = iname;
        MissChance = imissChance;
        DamageAbsorption = idamageAbsorption;
        Level = ilevel;
    }

    public static Shield fromLevel(int level) {
        switch(level) {
            case 1:
                return Shield.Leather;
            case 2:
                return Shield.Plastic;
            case 3:
                return Shield.Wood;
            case 4:
                return Shield.Bone;
            case 5:
                return Shield.Paper;
            case 6:
                return Shield.Glass;
            case 7:
                return Shield.Copper;
            case 8:
                return Shield.Brass;
            case 9:
                return Shield.Gold;
            case 10:
                return Shield.Iron;
            case 11:
                return Shield.Steel;
            default:
                return level > 1 ? Shield.Steel : Shield.Leather;
        }
    }

    public boolean wouldHit() {
        return MainGame.random.nextInt(101) > (MissChance) + (2 * Level);
    }

    public int calcNewDamage(int idamage) {
        return ((100 - (DamageAbsorption * Level)) * idamage / 100);
    }

    public void levelUp() {
        if(Level + 1 <= MaxLevel)
            Level++;
    }
}
