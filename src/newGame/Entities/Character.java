package newGame.Entities;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Weapons.Fist;
import newGame.Entities.Weapons.Melee;
import newGame.MainGame;
import newGame.Mapping.Tile;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {

    private static final int maxLevel = 100;
    private static final int defExpUntilLevelUp = 1024;
    private static final float expIncPerLevel = 1.12f;

    private final int MAX_INVENTORY_STACKS = 3;
    private final char[] INV_CALLER = { 'E', 'R', 'T' };

    private CharacterType type;
    private Shield shield;
    private List<InventoryStack<Item>> inventory = new ArrayList<>();
    private InventoryStack<Item> inHand;

    private int level = 1;
    private float expUntilLevelUp = defExpUntilLevelUp;
    private float exp = 0;

    public Character(String iname, CharacterType itype) {
        setName(iname);
        setRepresentation('@');
        setColor(ConsoleSystemInterface.YELLOW);
        setItemsInHand(new Fist().toInventoryStack());
        type = itype;

        for(int i = 0; i < MAX_INVENTORY_STACKS; i++)
            addStack(new InventoryStack<>());
    }

    public InventoryStack<Item> getItemsInHand() {
        return inHand;
    }

    public void setItemsInHand(InventoryStack<Item> newItem) {
        inHand = newItem;
        if(inHand != null && inHand.isLoneItem())
            inHand.getItem().setOwner(this);
    }

    public InventoryStack<? extends Item> getStack(int index) {
        return inventory.get(index);
    }

    public void setStack(InventoryStack<Item> stack, int index) {
        if(index >= MAX_INVENTORY_STACKS)
            return;

        inventory.set(index, stack);
    }

    public void addStack(InventoryStack<Item> stack) {
        if(inventory.size() >= MAX_INVENTORY_STACKS)
            return;

        inventory.add(stack);
    }

    public void removeStack(int index) {
        inventory.remove(index);
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

    public String getTypeAsString() {
        return type.Type;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.min(Math.max(level, 0), maxLevel);
    }

    public float getExpUntilLevelUp() {
        return expUntilLevelUp;
    }

    public void setExpUntilLevelUp(float expUntilLevelUp) {
        this.expUntilLevelUp = expUntilLevelUp;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void addExp(float xp) {
        final float added = exp + xp;
        if(added > expUntilLevelUp) {
            exp = expUntilLevelUp - added;
            expUntilLevelUp = defExpUntilLevelUp * ((float) Math.pow(expIncPerLevel, getLevel()));
            characterUpgrade();
        }
        else {
            exp = added;
        }
    }

    public void displayInformation() {
        int baseY = MainGame.map.getMapHeight() + 1;

        MainGame.clearCsi(1, baseY, 78, 4);

        // Prints everything again:
        MainGame.csi.print(1, baseY, getName() + " - " + getTypeAsString() + " - Level "  + getLevel());
        MainGame.csi.print(1, baseY + 1, "Health: " + getHealth() + "/" + getMaxHealth());
        MainGame.csi.print(1, baseY + 2, "Shield: " + (getShield() == null ? "None" : getShield().Name));

        MainGame.csi.print(25, baseY + 1, "In Hand: "); // +11
        MainGame.csi.print(25, baseY + 2, "Inventory: "); // +11

        final Tile onTile = MainGame.map.getTile(getX(), getY());
        if(onTile.hasItems()) {
            final Item sample = onTile.getInventoryStack().sampleItem();
            MainGame.csi.print(1, baseY + 3, "[P] Pickup: ", ConsoleSystemInterface.WHITE);
            MainGame.csi.print(12, baseY + 3, sample.getName(), sample.getColor());
        }
        else {
            MainGame.csi.print(1, baseY + 3, "Nothing to pickup.");
        }

        if(inHand != null && inHand.isLoneItem() && inHand.getItem() instanceof Melee) {
            Item handled = inHand.getItem();
            MainGame.csi.print(36, baseY + 1, handled.getRepresentation(), handled.getColor());
            MainGame.csi.print(38, baseY + 1, handled.getName() + " | Durability (" + handled.getUsesLeft() +
                    "/" + handled.getMaxDurability() + ")");
        }
        else if(inHand != null) {
            Item sampled = inHand.sampleItem();
            MainGame.csi.print(36, baseY + 1, sampled.getRepresentation(), sampled.getColor());
            MainGame.csi.print(38, baseY + 1, sampled.getName() + " | Count (" + inHand.getAmount() + ")");
        }
        else {
            MainGame.csi.print(36, baseY + 1, "N Nothing", ConsoleSystemInterface.WHITE);
        }

        for(int i = 0; i < inventory.size(); i++) {
            int x = 36 + (7 * i);
            InventoryStack<?> itemStack = getStack(i);
            if(itemStack == null) {
                MainGame.csi.print(x, baseY + 2, "0", ConsoleSystemInterface.RED);
            }
            else {
                MainGame.csi.print(x, baseY + 2, Integer.toString(itemStack.getSize()), ConsoleSystemInterface.WHITE);
            }
            MainGame.csi.print(x, baseY + 4, "[" + INV_CALLER[i] + "]", ConsoleSystemInterface.WHITE);
        }
    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - (shield == null ? amount : shield.calcNewDamage(amount)));
    }

    private void characterUpgrade() {

    }
}
