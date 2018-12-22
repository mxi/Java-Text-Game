package newGame.menus;

import newGame.MainGame;
import sz.csi.ConsoleSystemInterface;

import java.util.ArrayList;

public class MenuList extends MenuComponent {

    private ArrayList<MenuListItem> elements; // Elements to display onto list.
    private int width = 30, height = 7; // The dimensions of the menu list.
    private int itemOffset = 0; // The item offset for gui.
    private int itemSelected = 0; // The selected item in the menu list.

    /**
     * Constructor for the MenuList class that takes in the
     * position on the UI window.
     * @param x The x layout
     * @param y The y layout
     */
    public MenuList(int x, int y) {
        super(x, y);
        this.elements = new ArrayList<>();
    }

    /**
     * Constructor for the MenuList class that takes in the
     * position as well as pre-defined elements for the list.
     * @param elements The list of elements.
     * @param x The x layout
     * @param y The y layout.
     */
    public MenuList(ArrayList<MenuListItem> elements, int x, int y) {
        super(x, y);
        this.elements = elements;
    }

    /**
     * Gets the selected item.
     * @return The selected item.
     */
    public MenuListItem getSelectedItem() {
        return elements.get(itemSelected);
    }

    /**
     * Code that executes when the constructor for the MenuComponent
     * is finished, but before it returns to the caller. This will
     * be used to initialize any data members.
     */
    @Override
    protected void initialize() {

    }

    /**
     * Code that executes when the menu window is
     * closing/about to be closed so that this component
     * can save any data that it needs or log to
     * the logger.
     */
    @Override
    protected void destructor() {

    }

    /**
     * Renders this component onto the menu screen
     * (or however this component would like to
     * be rendered).
     * @param color The color of this component depending on focus state of this component.
     */
    @Override
    protected void render(Menu menu, int color) {
        char outline = (char) -1;

        // Draw the top and bottom outline of
        // the list box.
        for(int i = 0; i <= width; i++) {
            MainGame.csi.print(menu.getSceneX() + x + i, menu.getSceneY() + y, outline, color);
            MainGame.csi.print(menu.getSceneX() + x + i, menu.getSceneY() + y + height, outline, color);
        }

        // Draws the left and right outline of
        // the list box.
        for(int i = 1; i < height; i++) {
            MainGame.csi.print(menu.getSceneX() + x, menu.getSceneY() + y + i, outline, color);
            MainGame.csi.print(menu.getSceneX() + x + width, menu.getSceneY() + y + i, outline, color);
        }

        String selectedInfo = " Selected: " + itemSelected + " ";
        MainGame.csi.print(menu.getSceneX() + x + 1, menu.getSceneY() + y + height, selectedInfo, color);

        // Render the elements onto the list.
        for(int i = 0; i <= Math.min(height - 2 , elements.size()); i++) {
            MenuListItem item = elements.get(i + itemOffset);
            item.truncate(width - 4);
            int finalColor = isSelectedMenuItem(item) ? ConsoleSystemInterface.RED : color;
            MainGame.csi.print(menu.getSceneX() + x + 1, menu.getSceneY() + y + 1 + i,
                    item.getDescription(), finalColor);
        }
    }

    /**
     * Updates this component (if it's focused).
     * @param key User input key.
     */
    @Override
    protected void update(Menu menu, int key) {
        switch (key) {
            case 64:
            case 86: // Go to previous element.
                itemSelected--;
                break;
            case 67:
            case 82: // Go to the next element.
                itemSelected++;
                break;
        }

        if(itemSelected < 0)
            itemSelected = elements.size() - 1;
        else if(itemSelected >= elements.size())
            itemSelected = 0;

        itemOffset = Math.max(itemSelected - (height - 2), 0);
    }

    /**
     * Checks whether the specified menu list item is
     * the one that is selected.
     * @param item The item to check if selected.
     * @return Whether this item is selected.
     */
    private boolean isSelectedMenuItem(MenuListItem item) {
        return elements.get(itemSelected) == item;
    }
}
