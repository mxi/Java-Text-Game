package newGame.Entities.Inventory;

import newGame.Entities.Item;
import newGame.Entities.Character;
import newGame.Entities.Weapons.Melee;

import java.util.Stack;

public class InventoryStack<T extends Item> {

    private Stack<T> items;
    private int limit;
    public InventoryStack() {
        items = new Stack<>();
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
        return getSize() == 0 ? "Empty" : sampleItem().getName();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit < 1 ? 1 : limit;
    }

    public T getNext() {
        if(getSize() > 0)
            return items.peek();
        else
            return null;
    }

    public T removeNext() {
        if(getSize() > 0)
            return items.pop();
        else
            return null;
    }

    public void addNext(T item) {
        if(getSize() + 1 <= getLimit())
            items.add(item);
    }

    public void addAll(InventoryStack<T> stack) {
        while(stack.getSize() != 0)
            addNext(stack.getNext());
    }

    public void addAll(T... itemList) {
        for(T item : itemList) {
            addNext(item);
        }
    }

    public void use(Character c) {
        if(getSize() > 0) {
            if(sampleItem() instanceof Melee) {
                sampleItem().useItem();
            }
            else {
                removeNext().useItem();
            }
        }
    }
}
