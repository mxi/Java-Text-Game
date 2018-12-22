package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class Fist extends Melee {

    public Fist() {
        setName("Fist");
        setMaxDurability(999);
        setDamageOutput(1);
        setRepresentation('F');
        setColor(ConsoleSystemInterface.CYAN);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());

        if (getUsesLeft() == 1)
            setTimesUsed(0);
    }
}
