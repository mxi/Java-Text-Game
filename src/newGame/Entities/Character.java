package newGame.Entities;

import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {

    private final int MAX_INVENTORY_SPACE = 60;
    private final int MIN_INVENTORY_SPACE = 5;

    private CharacterType type;
    private Shield shield;
    private int strength;
    private int dexterity;
    private int wisdom;
    private int intelligence;
    private int charisma;

    private List<InventoryStack<? extends Representable>> inventory = new ArrayList<>();
    private Item inHand = null;

    public Character(String iname, CharacterType itype) {
        setName(iname);
        setRepresentation('@');
        setColor(ConsoleSystemInterface.CYAN);
        strength = itype.calcNewStrength(100);
        dexterity = itype.calcNewDexterity(100);
        wisdom = itype.calcNewWisdom(100);
        intelligence = itype.calcNewIntelligence(100);
        charisma = itype.calcNewCharisma(100);
        type = itype;
    }

    public List<InventoryStack<? extends Representable>> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryStack<? extends Representable>> newInventory) {
        inventory = newInventory;
    }

    public void addStackToInventory(InventoryStack<? extends Representable> stack) {
        inventory.add(stack);
    }

    public void removeStackFromInventory(InventoryStack<? extends Representable> stack) {
        inventory.remove(stack);
    }

    public void removeStackFromInventory(int index) {
        inventory.remove(index);
    }

    public Item getItemInHand() {
        return inHand;
    }

    public void setItemInHand(Item item) {
        inHand = item;
        inHand.setOwner(this);
    }

    public CharacterType getType() {
        return this.type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }

    public Shield getShield() {
        return this.shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
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

    public String getTypeAsString() {
        return type.Type;
    }

    public void displayInformation() {
        int baseY = MainGame.map.getMapHeight() + 1;

        // Clears everything:
        for(int i = 0; i < 4; i++)
            MainGame.csi.print(1, baseY + i, "\t\t\t\t\t\t\t\t");

        // Prints everything again:
        MainGame.csi.print(1, baseY, getName() + " - " + getTypeAsString() + " - Level "  + getLevel());
        MainGame.csi.print(1, baseY + 2, "Health: " + getHealth() + "/" + getMaxHealth());
        MainGame.csi.print(1, baseY + 3, "Shield: " + (getShield() == null ? "None" : getShield().Name));

        MainGame.csi.print(25, baseY + 2, "In Hand: " + "  " + (inHand == null
                ? "Nothing" : inHand.getName() + "(" + inHand.getUsesLeft() +" / " + inHand.getMaxDurability() + ")"));

        if(inHand != null) {
            MainGame.csi.print(34, baseY + 2, inHand.getRepresentation(), inHand.getColor());
        }
        else {
            MainGame.csi.print(34, baseY + 2, "N", ConsoleSystemInterface.WHITE);
        }

        MainGame.csi.print(25, baseY + 3, "Inventory: "); // +11
        if(inventory.size() == 0) {
            MainGame.csi.print(36, baseY + 3, "Empty", ConsoleSystemInterface.GRAY);
        }
        else {
            for (InventoryStack<? extends Representable> stacks : inventory) {

            }
        }
    }

    @Override
    protected void onEntityUpgrade() {

    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - (shield == null ? amount : shield.calcNewDamage(amount)));
    }
}
