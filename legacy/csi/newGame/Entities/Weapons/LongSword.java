package newGame.Entities.Weapons;

import newGame.Entities.Character;
import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class LongSword extends Melee {

    public LongSword() {
        setSwingRange(2);
        setName("Longsword");
        setRepresentation('L');
        setColor(ConsoleSystemInterface.TEAL);
        setDamageOutput(5);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
}
