package newGame.Entities;

import sz.csi.ConsoleSystemInterface;

public class InventoryStack<T> extends Representable {

    private int space;
    private int currentAmount;

    public InventoryStack() {
        setColor(ConsoleSystemInterface.LEMON);
        setName("InventoryStack<T>");

        space = 5;
        currentAmount = 0;
    }

    public InventoryStack(int initSpace) {
        setColor(ConsoleSystemInterface.LEMON);
        setName("InventoryStack<T>");

        space = initSpace;
        currentAmount = 0;
    }

    public void add(int amount) {
        currentAmount = Math.min(currentAmount + amount, space);
    }

    public void add(InventoryStack<T> anotherStack) {
        add(anotherStack.getAmount());
    }

    public void remove(int amount) {
        currentAmount = Math.max(currentAmount - amount, 0);
    }

    public void remove(InventoryStack<T> anotherStack) {
        remove(anotherStack.getAmount());
    }

    public void setAmount(int amount) {
        currentAmount = amount;
    }

    public int getAmount() {
        return currentAmount;
    }

    public void setSpace(int nSpace) {
        if(nSpace < 1)
            space = 1;
        else
            space = nSpace;
    }

    public int getSpace() {
        return space;
    }
}
