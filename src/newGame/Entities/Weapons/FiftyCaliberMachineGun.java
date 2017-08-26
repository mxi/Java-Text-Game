package newGame.Entities.Weapons;

import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class FiftyCaliberMachineGun extends Melee {

    public FiftyCaliberMachineGun() {
        setRepresentation('5');
        setColor(ConsoleSystemInterface.BROWN);
        setName(".50 CMG");
        setDamageOutput(Integer.MAX_VALUE);
        setSwingRange(Float.MAX_VALUE);
        setMaxDurability(Integer.MAX_VALUE);
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
}
