package newGame.Entities;

import sz.csi.ConsoleSystemInterface;

public abstract class Item extends Entity {

    public int HP_INCREASE_ON_UPGRADE;
    public int HP_DRAIN_ON_USE;

    private int housingSpace;
    private int totalTimesUsed;
    private int timesUsed;

    public Item() {
        setName("Item");
        setRepresentation('I');
        setColor(ConsoleSystemInterface.LIGHT_GRAY);
        setMaxHealth(50);
        setHealth(50);
        setLevel(1);
        HP_INCREASE_ON_UPGRADE = 5;
        HP_DRAIN_ON_USE = 1;
        housingSpace = 1;
        totalTimesUsed = 0;
        timesUsed = 0;
    }

    public Item(String iname, int idurability, int idegradation, int ihousing, int ilevel) {
        super(iname, idurability, ilevel);
        setRepresentation('I');
        setColor(ConsoleSystemInterface.LIGHT_GRAY);
        HP_INCREASE_ON_UPGRADE = 5;
        HP_DRAIN_ON_USE = 1;
        housingSpace = ihousing;
        totalTimesUsed = 0;
        timesUsed = 0;
    }

    public int getHealthIncreaseOnUpgrade() {
        return HP_INCREASE_ON_UPGRADE;
    }

    public void setHealthIncreaseOnUpgrade(int healthIncrease) {
        HP_INCREASE_ON_UPGRADE = healthIncrease;
    }

    public int getHealthDrainOnUse() {
        return HP_DRAIN_ON_USE;
    }

    public void setHealthDrainOnUse(int healthDrain) {
        HP_DRAIN_ON_USE = healthDrain;
    }

    public int getHousingSpace() {
        return housingSpace;
    }

    public void setHousingSpace(int housingSpace) {
        this.housingSpace = housingSpace < 1 ? 1 : housingSpace;
    }

    public int getTotalTimesUsed() {
        return totalTimesUsed;
    }

    public void setTotalTimesUsed(int totalTimesUsed) {
        this.totalTimesUsed = totalTimesUsed;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public void useItem() {
        if(getHealth() > 0) {
            damage(HP_DRAIN_ON_USE);
            timesUsed++;
            totalTimesUsed++;
            onItemUse();
        }
    }

    public void destroyItem() {
        setHealth(0);
        timesUsed = totalTimesUsed;
    }

    public void repairItem() {
        setHealth(getMaxHealth());
        timesUsed = 0;
    }

    @Override
    protected void onEntityUpgrade() {
        onItemUpgrade();
    }

    @Override
    protected void onEntityDowngrade() {
        onItemDowngrade();
    }

    protected abstract void onItemUse();

    protected abstract void onItemUpgrade();

    protected abstract void onItemDowngrade();

}
