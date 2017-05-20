package newGame.Entities;

import sz.csi.ConsoleSystemInterface;

public abstract class Item extends Representable {

    private static final int DamageOnUse = 1;

    private Entity owner;
    private int maxDurability = 25;
    private int timesUsed = 0;

    private int housingSpace;

    public Item() {
        setName("Item");
        setRepresentation('I');
        setColor(ConsoleSystemInterface.LIGHT_GRAY);
        housingSpace = 1;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity o) {
        owner = o;
    }

    public int getHousingSpace() {
        return housingSpace;
    }

    public void setHousingSpace(int newHousingSpace) {
        housingSpace = newHousingSpace < 1 ? 1 : newHousingSpace;
    }

    public void useItem() {
        if(!isBroken()) {
            onItemUse();
            setTimesUsed(getTimesUsed() + DamageOnUse);
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
