package newGame.Entities;

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

    public Character() {

    }

    public Character(String iname, CharacterType itype, int icolor, int ilevel) {
        super(iname, itype.calcNewHealth(100), ilevel);
        setRepresentation('@');
        setColor(icolor);
        invItems = new ArrayList<>();
        hotbar = new Item[3];
        selectedInHotbar = 0;
        inventorySpace = 9 + ilevel;
        strength = itype.calcNewStrength(100);
        dexterity = itype.calcNewDexterity(100);
        wisdom = itype.calcNewWisdom(100);
        intelligence = itype.calcNewIntelligence(100);
        charisma = itype.calcNewCharisma(100);
        type = itype;
    }

    public CharacterType getType() {
        return this.type;
    }

    public void setType(CharacterType type) {
        this.type = type;
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

    public String getTypeAsString() {
        return type.Type;
    }

    @Override
    public void onKeyPress(int key) {
        
    }

    @Override
    protected void onEntityUpgrade() {

    }

    @Override
    protected void onEntityDowngrade() {

    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - shield.calcNewDamage(amount));
    }
}
