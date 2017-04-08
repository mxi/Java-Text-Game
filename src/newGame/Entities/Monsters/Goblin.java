package newGame.Entities.Monsters;

import newGame.Entities.Entity;
import newGame.Entities.EntityAttributes;

public class Goblin extends Entity {

    // TODO: ADD WEAPON FOR THE GOBLINS

    private EntityAttributes.Shield shield;
    private char representation;

    public Goblin(char igoblin, int ihealth, int ilevel) {
        super("Goblin", ihealth + (5 * ilevel) - 5, ilevel);
        representation = igoblin;
    }

    public char getRepresentation() {
        return representation;
    }

    public void setRepresentation(char representation) {
        this.representation = representation;
    }
}
