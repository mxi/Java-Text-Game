package newGame.Entities.Treasures;

import newGame.Entities.Representable;

public class Treasure extends Representable {

    private int stackingSpace;
    private int rarity;

    public Treasure() {

    }

    public Treasure(int space, int rareChance) {
        stackingSpace = space;
        rarity = rareChance;
    }
}
