package newGame.Entities.Weapons;

import newGame.Entities.Entity;
import sz.csi.ConsoleSystemInterface;

public class Knife extends Melee {

    private boolean bypassShield;

    public Knife() {
        setRepresentation('K');
        setColor(ConsoleSystemInterface.RED);
        setName("Knife");
        setDamageOutput(3);
        bypassShield = false;
    }

    public boolean doesBypassShield() {
        return bypassShield;
    }

    public void setBypassesShield(boolean val) {
        bypassShield = val;
    }

    @Override
    protected void onAttack(Entity entity) {
        entity.damage(getDamageOutput());
    }
}
