package newGame.Entities;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Weapons.Fist;
import newGame.Entities.Weapons.Melee;
import newGame.IntPoint;
import newGame.MainGame;
import newGame.Mapping.MapInterface;
import newGame.Mapping.Tile;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {

    private static final int maxLevel = 100;
    private static final int defExpUntilLevelUp = 1024;
    private static final float expIncPerLevel = 1.05f;

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
    }

    public boolean isInventoryFull() {
        return inventory.size() == MAX_INVENTORY_STACKS;
    }

    public InventoryStack<Item> getItemsInHand() {
        return inHand;
    }

    public void setItemsInHand(InventoryStack<Item> newItem) {
        inHand = newItem;
        if(inHand != null && inHand.isLoneItem())
            inHand.getItem().setOwner(this);
    }

    public InventoryStack<Item> getStack(int index) {
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

    public List<InventoryStack<Item>> getInventory() {
        return inventory;
    }

    public void dropItemInHand() {
        if(inHand.isLoneItem() && inHand.sampleItem() == new Fist()) {
            return;
        }
        dropInventoryStack(getItemsInHand());
        setItemsInHand(new Fist().toInventoryStack());
    }

    public void dropStack(int index) {
        dropInventoryStack(getStack(index));
        removeStack(index);
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

    public int getLevel() {
        return level;
    }

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
        int baseX = 1;
        int baseY = MainGame.map.getMapHeight() + 1;

        MainGame.clearCsi(baseX, baseY, MainGame.map.getMapWidth() + 1, baseY + 4);

        // Prints everything again:
        // Main Info:
        MainGame.csi.print(baseX, baseY, getName() + " - " + getTypeAsString() + " - Level "  + getLevel());
        // Health Info:
        MainGame.csi.print(baseX, baseY + 1, "Health: ");
        switch(getDamageState()) {
            case NEGLIGIBLE:
                MainGame.csi.print(baseX + 8, baseY + 1, getHealth() + "/" + getMaxHealth());
                break;
            case MODERATE:
                MainGame.csi.print(baseX + 8, baseY + 1, getHealth() + "/" + getMaxHealth() + " Moderate",
                        ConsoleSystemInterface.YELLOW);
                break;
            case CRITICAL:
                MainGame.csi.print(baseX + 8, baseY + 1, getHealth() + "/" + getMaxHealth() + " Critical",
                        ConsoleSystemInterface.RED);
                break;
            case FATAL:
                MainGame.csi.print(baseX + 8, baseY + 1, getHealth() + "/" + getMaxHealth() + " Fatal",
                        ConsoleSystemInterface.DARK_RED);
                break;
            default:
                MainGame.csi.print(baseX + 8, baseY + 1, getHealth() + "/" + getMaxHealth());
        }
        // Shield Info:
        MainGame.csi.print(baseX, baseY + 2, "Shield: " + (getShield() == null ? "None" : getShield().Name));

        // In Hand / Inventory Info:
        MainGame.csi.print(baseX + 24, baseY + 1, "In Hand: "); // +9
        MainGame.csi.print(baseX + 24, baseY + 2, "Inventory: "); // +11

        // "Pickup" option Info:
        final Tile onTile = MainGame.map.getTile(getX(), getY());
        if(onTile.hasItems()) {
            final Item sample = onTile.getInventoryStack().sampleItem();
            MainGame.csi.print(baseX, baseY + 3, "[P] Pickup: ", ConsoleSystemInterface.WHITE);
            MainGame.csi.print(baseX + 12, baseY + 3, sample.getName(), sample.getColor());
        }
        else {
            MainGame.csi.print(baseX, baseY + 3, "Nothing to pickup.");
        }

        // In Hand Item Info:
        if(inHand != null && inHand.isLoneItem() && inHand.getItem() instanceof Melee) {
            Item handled = inHand.getItem();
            MainGame.csi.print(baseX + 33, baseY + 1, handled.getRepresentation(), handled.getColor());
            MainGame.csi.print(baseX + 35, baseY + 1, handled.getName() + " : Durability (" + handled.getUsesLeft() +
                    "/" + handled.getMaxDurability() + ")");
        }
        else if(inHand != null) {
            Item sampled = inHand.sampleItem();
            MainGame.csi.print(baseX + 35, baseY + 1, sampled.getRepresentation(), sampled.getColor());
            MainGame.csi.print(baseX + 37, baseY + 1, sampled.getName() + " : Count (" + inHand.getSize() + ")");
        }
        else {
            MainGame.csi.print(baseX + 35, baseY + 1, "Nothing", ConsoleSystemInterface.WHITE);
        }

        // Inventory Info:
        for(int i = 0; i < inventory.size(); i++) {
            int x = 36 + (10 * i);
            InventoryStack<Item> itemStack = getStack(i);
            if(itemStack == null || itemStack.getSize() == 0) {
                MainGame.csi.print(baseX + x - 1, baseY + 2, "0", ConsoleSystemInterface.RED);
                MainGame.csi.print(baseX + x - 1, baseY + 3, "Empty", ConsoleSystemInterface.RED);
            }
            else {
                MainGame.csi.print(baseX + x - 1, baseY + 2, Integer.toString(itemStack.getSize()), ConsoleSystemInterface.WHITE);
                MainGame.csi.print(baseX + x - 1, baseY + 3, itemStack.getName(), ConsoleSystemInterface.WHITE);
            }
            MainGame.csi.print(baseX + x - 1, baseY + 4, "[" + INV_CALLER[i] + "]", ConsoleSystemInterface.WHITE);
        }
    }

    private void dropInventoryStack(InventoryStack<Item> stack) {
        if(stack == null) {
            return;
        }
        final int distleft = getX();
        final int disttop = getY();
        final int distright = MainGame.map.getMapWidth() - distleft;
        final int distbottom = MainGame.map.getMapHeight() - disttop;
        int shortest = Math.min(Math.min(distleft, disttop), Math.min(distright, distbottom));
        IntPoint freespace = null;
        scanner:
        for(int i = 1; i < shortest; i++) {
            for(int x = distleft - i; x < distleft + i; x++) {
                for(int y = disttop - i; y < disttop + i; y++) {
                    final Tile mapt = MainGame.map.getTile(x, y);
                    if(mapt != null && mapt.equalsTo(Tile.SPACE) && !mapt.hasItems()) {
                        freespace = new IntPoint(x, y);
                        break scanner;
                    }
                }
            }
        }
        if(freespace != null) {
            MainGame.map.getTile(freespace).setInventoryStack(stack);
        }
    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - (shield == null ? amount : shield.calcNewDamage(amount)));
    }

    private void characterUpgrade() {

    }
}
