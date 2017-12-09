package newGame.Entities;

import newGame.Animations.Animations;
import newGame.IntPoint;
import newGame.MainGame;
import newGame.Mapping.Tile;
import sz.csi.ConsoleSystemInterface;

import java.util.List;
import java.util.ArrayList;

public abstract class Entity extends Representable {

    // Percentage of damage that would be considered negligible:
    private static final float PERCENT_DAMAGE_NEGLIGIBLE = 82f;
    // Percentage of damage that would be considered moderate:
    private static final float PERCENT_DAMAGE_MODERATE = 38f;
    // Percentage of damage that would be considered critical:
    private static final float PERCENT_DAMAGE_CRITICAL = 1f;

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

    public Entity() {
        setName("Entity");
        setRepresentation('E');
        health = 25;
        initialHealth = 25;
        maxHealth = 30;
        x = 0;
        y = 0;
    }

    public boolean inSight(Entity entity) {
        IntPoint p1 = entity.getPosition();
        IntPoint p2 = getPosition();

        if(p1.getY() == p2.getY()) {
            int commonY = p1.getY();
            int maxX = Math.max(p1.getX(), p2.getX());
            int minX = Math.min(p1.getX(), p2.getX());
            for(int x = minX + 1; x < maxX; x++)
                if(MainGame.map.getTile(x, commonY).isSolid()/*MainGame.map.getCharacter(commonX, y) == 'X'*/)
                    return false;

            return true;
        }
        else if(p1.getX() == p2.getX()) {
            int commonX = p1.getX();
            int maxY = Math.max(p1.getY(), p2.getY());
            int minY = Math.min(p1.getY(), p2.getY());
            for(int y = minY + 1; y < maxY; y++)
                if(MainGame.map.getTile(commonX, y).isSolid()/*MainGame.map.getCharacter(x, commonY) == 'X'*/)
                    return false;

            return true;
        }
        else {
            double slope = ((float) p1.getY() - (float) p2.getY()) / ((float) p1.getX() - (float) p2.getX());
            double intercept = p1.getY() - (slope * p1.getX());

            int maxX = Math.max(p1.getX(), p2.getX());
            int minX = Math.min(p1.getX(), p2.getX());
            int maxY = Math.max(p1.getY(), p2.getY());
            int minY = Math.min(p1.getY(), p2.getY());

            for(int x = minX; x <= maxX; x++) {
                int nowY = (int) Math.round(slope * x + intercept);
                int nextY = (int) Math.round(slope * (x + 1) + intercept);
                if(nextY < nowY) {
                    for(int y = nowY; y >= nextY && y >= minY; y--) {
                        Tile t = MainGame.map.getTile(x, y);
                        if(t == null || t.isSolid())
                            return false;
                    }
                }
                else {
                    for(int y = nowY; y <= nextY && y <= maxY; y++) {
                        Tile t = MainGame.map.getTile(x, y);
                        if(t == null || t.isSolid())
                            return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Draws a path from one point to another.
     * @param p1 Ray point.
     * @param p2 Destination point.
     */
    @Deprecated
    public void debugDrawPath(IntPoint p1, IntPoint p2) {
        if(p1.isEquivalentTo(p2)) {
            return;
        }
        final int color = ConsoleSystemInterface.MAGENTA;
        if(p1.getX() == p2.getX()) {
            int commonX = p1.getX();
            int maxY = Math.max(p1.getY(), p2.getY());
            int minY = Math.min(p1.getY(), p2.getY());
            for(int y = minY; y <= maxY; y++) {
                final char old = MainGame.csi.peekChar(commonX, y);
                MainGame.csi.print(commonX, y, old, color);
                MainGame.csi.refresh();
            }
        }
        else if(p1.getY() == p2.getY()) {
            int commonY = p1.getY();
            int maxX = Math.max(p1.getX(), p2.getX());
            int minX = Math.min(p1.getX(), p2.getX());
            for(int x = minX; x <= maxX; x++) {
                final char old = MainGame.csi.peekChar(x, commonY);
                MainGame.csi.print(x, commonY, old, color);
                MainGame.csi.refresh();
            }
        }
        else {
            double slope = ((float) p1.getY() - (float) p2.getY()) / ((float) p1.getX() - (float) p2.getX());
            double intercept = p1.getY() - (slope * p1.getX());

            int maxX = Math.max(p1.getX(), p2.getX());
            int minX = Math.min(p1.getX(), p2.getX());
            int maxY = Math.max(p1.getY(), p2.getY());
            int minY = Math.min(p1.getY(), p2.getY());

            for(int x = minX; x <= maxX; x++) {
                int nowY = (int) Math.round(slope * x + intercept);
                int nextY = (int) Math.round(slope * (x + 1) + intercept);
                if(nextY < nowY) {
                    for(int y = nowY; y >= nextY && y >= minY; y--) {
                        final char prev = MainGame.csi.peekChar(x, y);
                        MainGame.csi.print(x, y, prev, color);
                    }
                }
                else {
                    for(int y = nowY; y <= nextY && y <= maxY; y++) {
                        final char prev = MainGame.csi.peekChar(x, y);
                        MainGame.csi.print(x, y, prev, color);
                    }
                }
            }
        }
        MainGame.csi.refresh();
    }

    public void spawn(Tile onWhatTile) {
        final IntPoint rpos = MainGame.map.getMapBuffer().randomLoc(onWhatTile);
        setPosition(rpos.getX() + 1, rpos.getY() + 1);

        if(!MainGame.map.getEntities().contains(this)) {
            MainGame.map.getEntities().add(this);
        }
    }

    public List<Entity> withinProxy(double range) {
        List<Entity> withinRange = new ArrayList<>();
        for(Entity e : MainGame.map.getEntities())
            if(distance(e) <= range)
                withinRange.add(e);

        return withinRange;
    }

    public void adaptToMap() {
        setMinXY(MainGame.map.getMinX(), MainGame.map.getMinY());
        setMaxXY(MainGame.map.getMaxX(), MainGame.map.getMaxY());
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(IntPoint p) {
        x = p.getX();
        y = p.getY();
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
    	final int x = p.getX();
    	final int y = p.getY();
    	if(!MainGame.map.isPassableTile(x, y)) {
            return;
        }

        setPosition(x, y);
    }

    public IntPoint previewMove(int deltaX, int deltaY) {
        int newX = getX() + deltaX;
        int newY = getY() + deltaY;
        return new IntPoint(newX, newY);
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
        this.health -= amount;
        if(isDead()) {
            Animations.entityDeath(this, getX(), getY());
            MainGame.map.getEntities().removeIf(entity -> entity.equals(this));
        }
        else {
            Animations.entityDamage(this, getX(), getY());
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Gets the damage state of this entity.
     * @return Entity's health/damage state.
     */
    public DamageState getDamageState() {
        final float currentdmgs = ((float) getHealth() / (float) getMaxHealth()) * 100f; // Current Damage Status
        if(currentdmgs >= PERCENT_DAMAGE_NEGLIGIBLE) {
            return DamageState.NEGLIGIBLE;
        }
        else if(currentdmgs >= PERCENT_DAMAGE_MODERATE) {
            return DamageState.MODERATE;
        }
        else if(currentdmgs >= PERCENT_DAMAGE_CRITICAL) {
            return DamageState.CRITICAL;
        }
        else {
            return DamageState.FATAL;
        }
    }
}
