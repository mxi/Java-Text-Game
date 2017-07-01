package newGame.Entities.Inventory;

import newGame.Entities.Item;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.Stack;

public class InventoryStack<T extends Item> {

    private Stack<T> items;
    private String name;
    private int limit;
    public InventoryStack() {
        items = new Stack<>();
        name = "InvStack";
        limit = 1;
    }

    public Item getItem() {
        if(items.size() == 1)
            return items.get(0);

        return null;
    }

    public Item sampleItem() {
        if(items.size() > 0)
            return items.get(0);

        return null;
    }

    public boolean isLoneItem() {
        return items.size() == 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit < 1 ? 1 : limit;
    }

    public int getAmount() {
        return items.size();
    }

    public T getNext() {
        if(getAmount() > 0)
            return items.peek();
        else
            return null;
    }

    public T removeNext() {
        if(getAmount() > 0)
            return items.pop();
        else
            return null;
    }

    public void addNext(T item) {
        if(getAmount() + 1 <= getLimit())
            items.add(item);
    }

    public void addAll(InventoryStack<T> stack) {
        while(stack.getAmount() != 0)
            addNext(stack.getNext());
    }

    public void addAll(T... items) {
        for(T item : items)
            addNext(item);
    }

    public void print(int x, int y) {
        if(items.size() > 0) {
            Item sample = items.get(0);
            MainGame.csi.print(x, y, sample.getName(), sample.getColor());
            MainGame.csi.print(x, y + 1, Integer.toString(getAmount()), ConsoleSystemInterface.GREEN);
        }
        else {
            MainGame.map.setCharacter('*', x, y, ConsoleSystemInterface.GRAY);
            MainGame.csi.print(x, y + 1, "0", ConsoleSystemInterface.RED);
        }
    }
}
