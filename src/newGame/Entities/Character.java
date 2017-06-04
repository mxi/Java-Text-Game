package newGame.Entities;

import newGame.Entities.Inventory.InventoryStack;
import newGame.Entities.Weapons.Fist;
import newGame.Entities.Weapons.Knife;
import newGame.Entities.Weapons.Melee;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {

    private final int MAX_INVENTORY_STACKS = 3;
    private final char[] INV_CALLER = { 'E', 'R', 'T' };

    private CharacterType type;
    private Shield shield;

    private List<InventoryStack<? extends Item>> inventory = new ArrayList<>();
    private InventoryStack<Item> inHand;

    public Character(String iname, CharacterType itype) {
        setName(iname);
        setRepresentation('@');
        setColor(ConsoleSystemInterface.YELLOW);
        setItemsInHand(new Fist().toInventoryStack());
        type = itype;

        for(int i = 0; i < MAX_INVENTORY_STACKS; i++)
            addStack(new InventoryStack<>());
    }

    public InventoryStack<Item> getItemsInHand() {
        return inHand;
    }

    public void setItemsInHand(InventoryStack<Item> newItem) {
        inHand = newItem;
        if(inHand != null && inHand.isLoneItem())
            inHand.getItem().setOwner(this);
    }

    public InventoryStack<? extends Item> getStack(int index) {
        return inventory.get(index);
    }

    public void setStack(InventoryStack<? extends Item> stack, int index) {
        if(index >= MAX_INVENTORY_STACKS)
            return;

        inventory.set(index, stack);
    }

    public void addStack(InventoryStack<? extends Item> stack) {
        if(inventory.size() >= MAX_INVENTORY_STACKS)
            return;

        inventory.add(stack);
    }

    public void removeStack(int index) {
        inventory.remove(index);
    }

    public CharacterType getType() {
        return this.type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }

    public Shield getShield() {
        return this.shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public String getTypeAsString() {
        return type.Type;
    }

    public void displayInformation() {
        int baseY = MainGame.map.getMapHeight() + 1;

        // Clears everything:
        final int width = 100 ;
        final int height = MainGame.map.getMapHeight() + 5;
        for(int x = 1; x <= width; x++) {
            for (int y = MainGame.map.getMapHeight() + 1; y <= height; y++) {
                MainGame.map.setCharacter(' ', x, y, ConsoleSystemInterface.BLACK);
                MainGame.csi.refresh();
            }
        }

        // Prints everything again:
        MainGame.csi.print(1, baseY, getName() + " - " + getTypeAsString() + " - Level "  + getLevel());
        MainGame.csi.print(1, baseY + 1, "Health: " + getHealth() + "/" + getMaxHealth());
        MainGame.csi.print(1, baseY + 2, "Shield: " + (getShield() == null ? "None" : getShield().Name));

        MainGame.csi.print(25, baseY + 1, "In Hand: "); // +11
        MainGame.csi.print(25, baseY + 2, "Inventory: "); // +11

        if(inHand != null && inHand.isLoneItem() && inHand.getItem() instanceof Melee) {
            Item handled = inHand.getItem();
            MainGame.csi.print(36, baseY + 1, handled.getRepresentation(), handled.getColor());
            MainGame.csi.print(38, baseY + 1, handled.getName() + " | Durability (" + handled.getUsesLeft() +
                    "/" + handled.getMaxDurability() + ")");
        }
        else if(inHand != null) {
            Item sampled = inHand.sampleItem();
            MainGame.csi.print(36, baseY + 1, sampled.getRepresentation(), sampled.getColor());
            MainGame.csi.print(38, baseY + 1, sampled.getName() + " | Count (" + inHand.getAmount() + ")");
        }
        else {
            MainGame.csi.print(36, baseY + 1, "N Nothing", ConsoleSystemInterface.WHITE);
        }

        for(int i = 0; i < inventory.size(); i++) {
            int x = 36 + (7 * i);
            getStack(i).print(x, baseY + 2);
            MainGame.csi.print(x, baseY + 4, "[" + INV_CALLER[i] + "]", ConsoleSystemInterface.WHITE);
        }
    }

    @Override
    protected void onEntityUpgrade() {

    }

    @Override
    public void damage(int amount) {
        setHealth(getHealth() - (shield == null ? amount : shield.calcNewDamage(amount)));
    }
}
