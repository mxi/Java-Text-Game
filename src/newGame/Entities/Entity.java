package newGame.Entities;

import newGame.MainGame;

import java.awt.*;

public abstract class Entity {

    private int maxLevel = 30;
    private int expUntilLevelUp = 1024;
    private int hpIncreaseOnUpgrade = 5;
    private float expWeightOnUpgrade = 1.5f;

    private String name;
    private char representation;
    private int floor;
    private int color;
    private int minX;
    private int maxX;
    public int x;
    private int minY;
    private int maxY;
    public int y;
    private int maxHealth;
    private int initialHealth;
    private int health;
    private int exp;
    private int level;

    public Entity() {
        name = "Entity";
        representation = 'E';
        exp = 0;
        level = 1;
        health = 25;
        initialHealth = 25;
        maxHealth = 30;
        x = 0;
        y = 0;
    }

    public Entity(String iname, int ihealth, int ilevel) {
        name = iname;
        representation = '!';
        exp = 0;
        level = ilevel;
        health = ihealth;
        initialHealth = ihealth;
        maxHealth = ihealth;
        x = 0;
        y = 0;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int ifloor) {
        floor = ifloor;
    }

    public char getRepresentation() {
        return representation;
    }

    public void setRepresentation(char representation) {
        this.representation = representation;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double distance(double x, double y) {
        return Math.sqrt( Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) );
    }

    public double distance(Point p) {
        return distance(p.getX(), p.getY());
    }

    public double distance(Entity e) {
        return distance(e.getX(), e.getY());
    }

    public boolean intersects(double x, double y) {
        return getX() == x && getY() == y;
    }

    public boolean intersects(Point p) {
        return intersects(p.getX(), p.getY());
    }

    public boolean intersects(Entity e) {
        return intersects(e.getPosition());
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

    public void setBounds(int minX, int minY, int maxX, int maxY) {
        setMaxXY(maxX, maxY);
        setMinXY(minX, minY);
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

    public Point getPosition() {
        return new Point(getX(), getY());
    }

    public void moveLeft() {
        Point previousPos = getPosition();
        this.x = this.x - 1 < this.minX ? this.minX : this.x - 1;
        if(previousPos.getX() != getX() || previousPos.getY() != getY())
            MainGame.csi.print(x + 1, y, " ");
    }

    public Point previewLeft() {
        return new Point(this.x - 1 < getMinX() ? getMinX() : this.x - 1, getY());
    }

    public void moveRight() {
        Point previousPos = getPosition();
        this.x = this.x + 1 > this.maxX ? this.maxX : this.x + 1;
        if(previousPos.getX() != getX() || previousPos.getY() != getY())
            MainGame.csi.print(x - 1, y, " ");
    }

    public Point previewRight() {
        return new Point(this.x + 1 > getMaxX() ? this.maxX : this.x + 1, getY());
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveDown() {
        Point previousPos = getPosition();
        this.y = this.y + 1 > this.maxY ? this.maxY : this.y + 1;
        if(previousPos.getX() != getX() || previousPos.getY() != getY())
            MainGame.csi.print(x, y - 1, " ");
    }

    public Point previewDown() {
        return new Point(getX(), this.y + 1 > getMaxY() ? getMaxY() : this.y + 1);
    }

    public void moveUp() {
        Point previousPos = getPosition();
        this.y = this.y - 1 < this.minY ? this.minY : this.y - 1;
        if(previousPos.getX() != getX() || previousPos.getY() != getY())
            MainGame.csi.print(x, y + 1, " ");
    }

    public Point previewUp() {
        return new Point(getX(), this.y - 1 <getMinY() ? getMinY() : this.y - 1);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int amount) {
        if(exp + amount > expUntilLevelUp) {
            int leftOver = (exp + amount) - expUntilLevelUp;
            upgrade(leftOver);
        }
        else {
            this.exp += amount;
        }
    }

    public void removeExp(int amount) {
        if(exp - amount < 0) {
            int leftOver = -(exp - amount);
            downgrade(leftOver);
        }
        else {
            this.exp -= amount;
        }
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
        if(getHealth() > maxHealth)
            setHealth(maxHealth);
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

    public void upgrade(int leftOver) {
        if(getLevel() + 1 > getMaxLevel())
            return;

        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + hpIncreaseOnUpgrade);
        setExp(leftOver);
        setExpUntilLevelUp((int) (getExpUntilLevelUp() * expWeightOnUpgrade));
        onEntityUpgrade();
    }

    public void downgrade(int leftOver) {
        if(getLevel() - 1 < 0)
            return;

        setLevel(getLevel() - 1);
        setMaxHealth(getMaxHealth() - hpIncreaseOnUpgrade);
        setExpUntilLevelUp((int) (getExpUntilLevelUp() / expWeightOnUpgrade));
        setExp(getExpUntilLevelUp() - leftOver);
        onEntityDowngrade();
    }

    public abstract void onKeyPress(int key);

    protected abstract void onEntityUpgrade();

    protected abstract void onEntityDowngrade();
}
