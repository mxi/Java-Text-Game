package newGame.Entities;

import newGame.IntPoint;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.List;
import java.util.ArrayList;

public abstract class Entity extends Representable {

    public static List<Entity> entities = new ArrayList<>();

    private int maxLevel = 30;
    private int expUntilLevelUp = 1024;
    private int hpIncreaseOnUpgrade = 5;
    private float expWeightOnUpgrade = 1.5f;

    private char prevCharOfMap = '.';
    private int prevColorOfMap = ConsoleSystemInterface.WHITE;
    private int floor;
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

    public Entity() {
        setName("Entity");
        setRepresentation('E');
        setPrevCharOfMap('.');
        exp = 0;
        level = 1;
        health = 25;
        initialHealth = 25;
        maxHealth = 30;
        x = 0;
        y = 0;
    }

    public void spawn(char onWhatTile) {
        while(true) {
            int x = MainGame.random.nextInt(MainGame.map.getMapWidth()) + 1;
            int y = MainGame.random.nextInt(MainGame.map.getMapHeight()) + 1;

            if(MainGame.map.getCharacter(x, y) == onWhatTile) {
                setPosition(x, y);
                break;
            }
        }
    }

    public List<Entity> withinProxy(double range) {
        List<Entity> withinRange = new ArrayList<>();
        for(Entity e : entities)
            if(distance(e) <= range)
                withinRange.add(e);

        return withinRange;
    }

    public char getPrevCharOfMap() {
        return prevCharOfMap;
    }

    public void setPrevCharOfMap(char c) {
        prevCharOfMap = c;
    }

    public int getPrevColorOfMap() {
        return prevColorOfMap;
    }

    public void setPrevColorOfMap(int prevColorOfMap) {
        this.prevColorOfMap = prevColorOfMap;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int ifloor) {
        floor = ifloor;
    }

    public double distance(double x, double y) {
        return Math.sqrt( Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) );
    }

    public double distance(IntPoint p) {
        return distance(p.getX(), p.getY());
    }

    public double distance(Entity e) {
        return distance(e.getX(), e.getY());
    }

    public boolean intersects(double x, double y) {
        return getX() == x && getY() == y;
    }

    public boolean intersects(IntPoint p) {
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public IntPoint getPosition() {
        return new IntPoint(getX(), getY());
    }

    public void move(int deltaX, int deltaY) {
    	
    	IntPoint p = previewMove(deltaX, deltaY);
    	for(Entity e : entities) {
            if(e.intersects(p) || MainGame.csi.peekChar(p.getX(), p.getY()) == 'X')
                return;
        }
        MainGame.csi.print(getX(), getY(), prevCharOfMap, prevColorOfMap);
        setPosition(p.getX(), p.getY());
        setPrevCharOfMap(MainGame.csi.peekChar(getX(), getY()));
        setPrevColorOfMap(MainGame.csi.peekColor(getX(), getY()));
    }

    public IntPoint previewMove(int deltaX, int deltaY) {
        int newX = getX() + deltaX;
        int newY = getY() + deltaY;
/*
        if(newX < getMinX())
            newX = getMinX();
        else if(newX > getMaxX())
            newX = getMaxX();

        if(newY < getMinY())
            newY = getMinY();
        else if(newY > getMaxY())
            newY = getMaxY();
*/
        return new IntPoint(newX, newY);
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

    protected abstract void onEntityUpgrade();

}
