package newGame.Entities;

public class Entity {

    private String name;
    private int x;
    private int y;
    private int health;
    private int level;

    public Entity(String iname, int ihealth, int ilevel) {
        name = iname;
        level = ilevel;
        health = ihealth;
        x = 0;
        y = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
