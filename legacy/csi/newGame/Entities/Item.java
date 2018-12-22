package newGame.Entities;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Weapons.Fist;
import newGame.Entities.Weapons.Melee;
import sz.csi.ConsoleSystemInterface;

public abstract class Item extends Representable {

    private static final int DamageOnUse = 1;

    private Entity owner;
    private int maxDurability = 25;
    private int timesUsed = 0;
    private boolean oneTimeUse = true;

    public Item() {
        setName("Item");
        setRepresentation('I');
        setColor(ConsoleSystemInterface.LIGHT_GRAY);
    }

    /**
     * Determines if this item is a one time use.
     * @param value Whether it's a one time use (true), or not (false)
     */
    public void setIsOneTimeUse(boolean value) {
        oneTimeUse = value;
    }

    /**
     * Determines whether this item is a one time use.
     * @return Whether this item is a one time use.
     */
    public boolean isOneTimeUse() {
        return oneTimeUse;
    }

    public Entity getOwner() {
        return owner;
    }

    public InventoryStack<Item> toInventoryStack() {
        InventoryStack<Item> invStack = new InventoryStack<>();
        invStack.addNext(this);
        return invStack;
    }

    public void setOwner(Entity o) {
        owner = o;
    }

    public void useItem() {
        if(!isBroken()) {
            onItemUse();
            setTimesUsed(getTimesUsed() + DamageOnUse);

            if(isBroken() && getOwner() instanceof Character) {
                ((Character) getOwner()).setItemsInHand(new Fist().toInventoryStack());
            }
        }
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int newTimesUsed) {
        timesUsed = newTimesUsed;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int newMaxDurability) {
        maxDurability = newMaxDurability;
    }

    public int getUsesLeft() {
        return maxDurability - timesUsed;
    }

    public boolean isBroken() {
        return getUsesLeft() <= 0;
    }

    protected abstract void onItemUse();
}
