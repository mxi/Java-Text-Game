package newGame.Entities;

import newGame.Exceptions.UpgradeLimitReachedException;
import newGame.MainGame;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    private int maxLevel = 30;
    private int expUntilLevelUp = 1024;

    private List<Runnable> onUpgrade;
    private String name;
    private int minX;
    private int maxX;
    private int x;
    private int minY;
    private int maxY;
    private int y;
    private int maxHealth;
    private int initialHealth;
    private int health;
    private int exp;
    private int level;

    public Entity(String iname, int ihealth, int ilevel) {
        onUpgrade = new ArrayList<>();
        name = iname;
        exp = 0;
        level = ilevel;
        health = ihealth;
        initialHealth = ihealth;
        maxHealth = ihealth;
        x = 0;
        y = 0;
    }

    public double distance(int x, int y) {
        return Math.sqrt( Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) );
    }

    public void addOnUpgradeEvent(Runnable upgradeEvent) {
        this.onUpgrade.add(upgradeEvent);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getExpUntilLevelUp() {
        return expUntilLevelUp;
    }

    public void setExpUntilLevelUp(int expUntilLevelUp) {
        this.expUntilLevelUp = expUntilLevelUp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setMinXY(int x, int y) {
        this.minX = x;
        this.minY = y;
    }

    public void setMaxXY(int x, int y) {
        this.maxX = x;
        this.maxY = y;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void moveLeft() {
        this.x = this.x - 1 < this.minX ? this.minX : this.x - 1;
        MainGame.csi.print(x + 1, y, " ");
    }

    public void moveRight() {
        this.x = this.x + 1 > this.maxX ? this.maxX : this.x + 1;
        MainGame.csi.print(x - 1, y, " ");
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveDown() {
        this.y = this.y - 1 < this.minY ? this.minY : this.y - 1;
        MainGame.csi.print(x, y + 1, " ");
    }

    public void moveUp() {
        this.y = this.y + 1 > this.maxY ? this.maxY : this.y + 1;
        MainGame.csi.print(x, y - 1, " ");
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int amount) {
        this.exp += amount;
    }

    public void removeExp(int amount) {
        this.exp -= amount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getInitialHealth() {
        return this.initialHealth;
    }

    public void setInitialHealth(int initialHealth) {
        this.initialHealth = initialHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void heal(int amount) {
        this.health = amount + this.health > this.maxHealth ? this.maxHealth : this.health + amount;
    }

    public void damage(int amount) {
        this.health = this.health - amount < 0 ? 0 : this.health - amount;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void upgrade() throws UpgradeLimitReachedException {
        if(this.level + 1 > this.maxLevel)
            throw new UpgradeLimitReachedException(this);

        onUpgrade.forEach(runnable -> runnable.run());

        setExp(0);
    }
}
