package newGame.Entities;

public class Entity {

    private static final int maxLevel = 30;
    private static final int expUntilLevelup = 1024;

    private String name;
    private int x;
    private int y;
    private int maxHealth;
    private int initialHealth;
    private int health;
    private int exp;
    private int level;

    public Entity(String iname, int ihealth, int ilevel) {
        name = iname;
        exp = 0;
        level = ilevel;
        health = ihealth;
        initialHealth = ihealth;
        maxHealth = ihealth;
        x = 0;
        y = 0;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
}
