package newGame.Entities.Weapons;

import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public abstract class LongBow extends Ranged{
	public LongBow() {
        setSwingRange(0);
        setName("Longbow");
        setRepresentation('B');
        setColor(ConsoleSystemInterface.TEAL);
        setDamageOutput(5);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
}
