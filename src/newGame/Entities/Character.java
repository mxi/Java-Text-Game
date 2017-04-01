package newGame.Entities;

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

        public int calcNewHealth(int ihealth) {
            return (int) (ihealth * (HealthBonus / 100.0));
        }

        public int calcNewStrength(int istrength) {
            return (int) (istrength * (StrengthBonus / 100.0));
        }

        public int calcNewDexterity(int idexterity) {
            return (int) (idexterity * (DexterityBonus / 100.0));
        }

        public int calcNewWisdom(int iwisdom) {
            return (int) (iwisdom * (WisdomBonus / 100.0));
        }

        public int calcNewIntelligence(int iintelligence) {
            return (int) (iintelligence * (IntelligenceBonus / 100.0));
        }

        public int calcNewCharisma(int ichrasima) {
            return (int) (ichrasima * (CharismaBonus / 100.0));
        }
    }

    public Character(String iname, CharacterType itype, int ilevel) {
        super(iname, ilevel);
        type = itype;
    }
}
