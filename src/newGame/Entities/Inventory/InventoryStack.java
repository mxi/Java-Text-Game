package newGame.Entities.Inventory;

import newGame.Entities.Item;
import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.Arrays;
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

    public int getSize() {
        return items.size();
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

    public void addAll(T... itemList) {
        items.addAll(Arrays.asList(itemList));
    }
}
