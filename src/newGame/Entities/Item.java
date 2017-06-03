package newGame.Entities;

import sz.csi.ConsoleSystemInterface;

public abstract class Item extends Representable {

    private static final int DamageOnUse = 1;

    private Entity owner;
    private int maxDurability = 25;
    private int timesUsed = 0;

    public Item() {
        setName("Item");
        setRepresentation('I');
        setColor(ConsoleSystemInterface.LIGHT_GRAY);
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity o) {
        owner = o;
    }

    public void useItem() {
        if(!isBroken()) {
            onItemUse();
            setTimesUsed(getTimesUsed() + DamageOnUse);

            if(isBroken()) {
                Character c = (Character) getOwner();
                c.setItemInHand(null);
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
