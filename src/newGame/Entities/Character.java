package newGame.Entities;

import sz.csi.ConsoleSystemInterface;

public class Character extends Entity {

    private CharacterType type;
    public enum CharacterType {
        Fighter(20, 100, -15, 0, -15, 0),
        Ranger(0, 0, 25, 5, 0, 0),
        Wizard(-50, -50, 0, 15, 15, 5);

        public int HealthBonus;
        public int StrengthBonus;
        public int DexterityBonus;
        public int WisdomBonus;
        public int IntelligenceBonus;
        public int CharismaBonus;

        CharacterType(int health, int strength, int dexterity, int wisdom, int intelligence, int charisma) {
            HealthBonus = health + 100;
            StrengthBonus = strength + 100;
            DexterityBonus = dexterity + 100;
            WisdomBonus = wisdom + 100;
            IntelligenceBonus = intelligence + 100;
            CharismaBonus = charisma + 100;
        }
    }

    public Character(String iname, CharacterType itype, int ilevel) {
        super(iname, ilevel);
        type = itype;
    }
}
