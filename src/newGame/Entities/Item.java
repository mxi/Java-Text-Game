package newGame.Entities;

public class Item extends Entity {

    public static int HP_INCREASE_ON_UPGRADE = 5;
    public static int HP_RES_INCREASE_ON_UPGRADE = 2;
    public static int HP_DRAIN_ON_USE = 5;
    public static int HOUSING_SPACE_INCREASE_ON_UPGRADE = 1;

    private int housingSpace;
    private int housingSpaceBonus;

    private int healthBonus;

    private int totalTimesUsed;
    private int timesUsed;

    public Item(String iname, int idurability, int idegradation, int ihousing, int ilevel) {
        super(iname, idurability * (HP_INCREASE_ON_UPGRADE * ilevel), ilevel);
        housingSpace = ihousing;
        housingSpaceBonus = 0;

        healthBonus = 0;

        totalTimesUsed = 0;
        timesUsed = 0;
    }

    public int getHousingSpace() {
        return housingSpace - getHousingSpaceBonusDecrease();
    }

    public void setHousingSpace(int housingSpace) {
        this.housingSpace = housingSpace < 1 ? 1 : housingSpace;
    }

    public int getHousingSpaceBonus() {
        return housingSpaceBonus - getHousingSpaceBonusDecrease();
    }

    public void setHousingSpaceBonus(int housingSpaceBonus) {
        this.housingSpaceBonus = housingSpaceBonus > 50 ? 50 : housingSpaceBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public void setHealthBonus(int healthBonus) {
        this.healthBonus = healthBonus > 50 ? 50 : healthBonus;
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
        damage(HP_DRAIN_ON_USE - getDamageBonusDecrease());
        timesUsed++;
        totalTimesUsed++;
    }

    public void destroyItem() {
        setHealth(1);
        timesUsed = totalTimesUsed - 1;
    }

    public void repairItem() {
        setHealth(getMaxHealth());
        timesUsed = 0;
    }

    public void upgradeItem() {
        setMaxHealth(getMaxHealth() + HP_INCREASE_ON_UPGRADE);
        setHealthBonus(healthBonus + HP_RES_INCREASE_ON_UPGRADE);
        setHousingSpace(housingSpace + HOUSING_SPACE_INCREASE_ON_UPGRADE);
    }

    // -- Private Methods

    private int getDamageBonusDecrease() {
        return (healthBonus * HP_DRAIN_ON_USE) / 100;
    }

    private int getHousingSpaceBonusDecrease() {
        return (housingSpaceBonus * housingSpace) / 100;
    }

}
