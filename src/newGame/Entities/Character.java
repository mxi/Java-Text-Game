package newGame.Entities;

import newGame.MainGame;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {

    private final int MAX_INVENTORY_SPACE = 60;
    private final int MIN_INVENTORY_SPACE = 5;

    private CharacterType type;
    private List<? extends Item> invItems;
    private Item[] hotbar;
    private Shield shield;
    private int selectedInHotbar;
    private int inventorySpace;
    private int strength;
    private int dexterity;
    private int wisdom;
    private int intelligence;
    private int charisma;
    private int color;
    public enum CharacterType {
        Fighter("Fighter", 20, 100, -15, 0, -15, 0),
        Ranger("Ranger", 0, 0, 25, 5, 0, 0),
        Wizard("Wizard", -50, -50, 0, 15, 15, 5);

        public String Type;
        public int HealthBonus;
        public int StrengthBonus;
        public int DexterityBonus;
        public int WisdomBonus;
        public int IntelligenceBonus;
        public int CharismaBonus;

        CharacterType(String type, int health, int strength, int dexterity, int wisdom, int intelligence, int charisma) {
            Type = type;
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

    public enum Shield {
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
        Shield(String iname, int imissChance, int idamageAbsorption, int ilevel, float iupgradeSpeed) {
            Name = iname;
            MissChance = imissChance;
            DamageAbsorption = idamageAbsorption;
            Level = ilevel;
            UpgradeSpeed = iupgradeSpeed;
        }

        public boolean wouldHit() {
            return MainGame.random.nextInt(101) > (MissChance * UpgradeSpeed) + (2 * Level);
        }

        public int calcNewDamage(int idamage) {
            return (int) ((100 - (DamageAbsorption * Level * UpgradeSpeed)) * idamage / 100);
        }

        public void levelUp() {
            if(Level + 1 <= MaxLevel)
                Level++;
        }
    }

    public Character(String iname, CharacterType itype, int icolor, int ilevel) {
        super(iname, itype.calcNewHealth(100), ilevel);
        invItems = new ArrayList<>();
        hotbar = new Item[3];
        selectedInHotbar = 0;
        inventorySpace = 9 + ilevel;
        strength = itype.calcNewStrength(100);
        dexterity = itype.calcNewDexterity(100);
        wisdom = itype.calcNewWisdom(100);
        intelligence = itype.calcNewIntelligence(100);
        charisma = itype.calcNewCharisma(100);
        color = icolor;
        type = itype;
    }

    public List<? extends Item> getInvetoryItems() {
        return this.invItems;
    }

    public void setInventoryItems(List<? extends Item> items) {
        this.invItems = items;
    }

    public Item[] getHotBar() {
        return this.hotbar;
    }

    public Shield getShield() {
        return this.shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public void setHotBar(Item[] hotbar) {
        this.hotbar = hotbar;
    }

    public Item getItemInHand() {
        return this.hotbar[selectedInHotbar];
    }

    public void setItemInHand(Item item) {
        this.hotbar[selectedInHotbar] = item;
    }

    public int getInventorySpaceOccupied() {
        int amount = 0;
        for(Item item : this.invItems)
            amount += item.getHousingSpace();
        return amount;
    }

    public int getInventorySpaceFree() {
        return inventorySpace - getInventorySpaceOccupied();
    }

    public int getInventorySpace() {
        return this.inventorySpace;
    }

    public void setInventorySpace(int inventorySpace) {
        this.inventorySpace = inventorySpace > MAX_INVENTORY_SPACE ? MAX_INVENTORY_SPACE : inventorySpace;
    }

    public void addInventorySpace(int amount) {
        this.inventorySpace = this.inventorySpace + amount > MAX_INVENTORY_SPACE ? MAX_INVENTORY_SPACE : this.inventorySpace + amount;
    }

    public void removeInventorySpace(int amount) {
        this.inventorySpace = this.inventorySpace - amount < MIN_INVENTORY_SPACE ? MIN_INVENTORY_SPACE : this.inventorySpace - amount;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTypeAsString() {
        return type.Type;
    }
}
