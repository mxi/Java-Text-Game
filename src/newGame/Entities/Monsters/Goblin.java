package newGame.Entities.Monsters;

import newGame.Entities.Entity;
import newGame.Entities.EntityAttributes;
import newGame.Entities.Item;
import sz.csi.ConsoleSystemInterface;

public class Goblin extends Entity {

    private EntityAttributes.Shield shield;
    private Item weapon;
    private int color;
    private char representation;

    public Goblin(char igoblin, int ihealth, int ilevel) {
        super("Goblin Level " + ilevel, ihealth + (5 * ilevel) - 5, ilevel);
        color = ConsoleSystemInterface.GREEN;
        representation = igoblin;
    }

    public Item getWeapon() {
        return this.weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public char getRepresentation() {
        return representation;
    }

    public void setRepresentation(char representation) {
        this.representation = representation;
    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - shield.calcNewDamage(amount));
    }
}
