package newGame;

import newGame.Entities.Entity;
import newGame.Entities.Monsters.Goblin;
import newGame.Entities.Monsters.Monster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterList {

    private List<Monster> monsters;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    public MonsterList() {
        monsters = new ArrayList<>();
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setBounds(int minX, int minY, int maxX, int maxY) {
        setMaxXY(maxX, maxY);
        setMinXY(minX, minY);
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMinXY(int minX, int minY) {
        setMinX(minX);
        setMinY(minY);
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setMaxXY(int maxX, int maxY) {
        setMaxX(maxX);
        setMaxY(maxY);
    }

    public void addMonster(Monster m) {
        monsters.add(m);
    }

    public void spawnGoblin(int level) {
        Goblin goblin = new Goblin(level);
        goblin.setBounds(minX, minY, maxX, maxY);
        goblin.setPosition(MainGame.random.nextInt(maxX) + 1, MainGame.random.nextInt(maxX));
        monsters.add(goblin);
    }

    public void removeMonster(Monster m) {
        monsters.remove(m);
    }

    public void removeMonster(int index) {
        monsters.remove(index);
    }

    public Monster getMonster(int index) {
        return monsters.get(index);
    }

    public Monster getMonster(double x, double y) {
        for(Monster m : monsters)
            if(m.intersects(x, y))
                return m;
        return null;
    }

    public Monster getMonster(Point p) {
        return getMonster(p.getX(), p.getY());
    }

    public Monster getMonster(Entity e) {
        return getMonster(e.getX(), e.getY());
    }
}
