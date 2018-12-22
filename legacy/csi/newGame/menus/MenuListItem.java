package newGame.menus;

/**
 * A item that maintains an item for
 * the MenuList component.
 *
 * @author Max
 */
public class MenuListItem {

    private String description; // The description of the item.

    /**
     * Creates a new item with a specified
     * description.
     * @param desc The description of the item.
     */
    public MenuListItem(String desc) {
        description = desc;
    }

    /**
     * Shortens the length of the description
     * to fit a specific length.
     * @param nSize The new length.
     */
    public void truncate(int nSize) {
        if(description.length() <= nSize) {
            return;
        }
        else if(nSize == 0) {
            description = "";
            return;
        }

        StringBuilder builder = new StringBuilder(nSize);
        for(int i = 0; i < nSize - 3; i++)
            builder.append(description.charAt(i));

        builder.append("...");
        description = builder.toString();
    }

    /**
     * Gets the description of this item.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }
}
