package newGame.menus;

/**
 * This is a specialized class for the NotificationMenu
 * class, and it's primary use is to be the constructor
 * argument that contains the message already processed.
 * Meaning that the string is already split up
 * into a String[] (for each line of text)
 * and it has the dimension requirements
 * of the text.
 */
public class NotificationText {

    private String[] processedText; // The processed text of this notification text object.

    /**
     * Constructor for a notification text object
     * that takes in a string of text
     * that will be then printed onto a label
     * of a notification menu.
     * @param text Text to display on the notification menu.
     */
    public NotificationText(String text) {
        if(text == null)
            throw new IllegalArgumentException("Cannot create NotificationText object with null text.");
        processedText = processTextIntoArray(text, identifyProperArraySize(text));
    }

    /**
     * Gets the processed text of this object.
     * @return Processed text.
     */
    public String[] getProcessedText() {
        return processedText;
    }

    /**
     * Gets the height (length) of the array of the processedText.
     * @return Processed text array length.
     */
    public int getProcessedTextHeight() {
        return processedText.length;
    }

    /**
     * Gets the maximum width of the processed text
     * of this object.
     * @return Maximum width of the processed text of this object.
     */
    public int getProcessedTextWidth() {
        int maxWidth = 0;
        for(String s : processedText) {
            if(s.length() > maxWidth)
                maxWidth = s.length();
        }
        return maxWidth;
    }

    /**
     * Identifies the proper length of the line array for this
     * notification text object. i.e calculates the length of
     * the string array of lines for a specified
     * string of text.
     * @param text Text to calculate the string array length for.
     * @return The correct length for the string array.
     */
    private int identifyProperArraySize(String text) {
        int size = 1;
        int buffered = 0; // The current line's character count.
        for(int i = 0; i < text.length(); i++) {
            if(size > NotificationMenu.MAX_TEXT_HEIGHT_SPAN)
                throw new IllegalArgumentException("Argument surpassed " + NotificationMenu.MAX_TEXT_HEIGHT_SPAN + " line limit.");
            char c = text.charAt(i);
            buffered++;
            if(c == '\n' || buffered == NotificationMenu.MAX_TEXT_WIDTH_SPAN) {
                size++;
                buffered = 0;
            }
        }
        return size;
    }

    /**
     * Processes the specified text into a string array of a specified length
     * so that this object may be used to display text on  a notification menu.
     * @param origin Original string to process.
     * @param arrayLength The calculated amount of lines that this object contains.
     * @return The processed string[]
     */
    private String[] processTextIntoArray(String origin, int arrayLength) {
        final StringBuffer[] array = new StringBuffer[arrayLength];
        for(int i = 0; i < arrayLength; i++)
            array[i] = new StringBuffer(NotificationMenu.MAX_TEXT_HEIGHT_SPAN);
        int currentLine = 0;
        int buffered = 0; // The current line's character count.
        for(int i = 0; i < origin.length(); i++) {
            char c = origin.charAt(i);
            buffered++;
            array[currentLine].append(c == '\n' ? "" : c);
            if(c == '\n' || buffered == NotificationMenu.MAX_TEXT_WIDTH_SPAN) {
                currentLine++;
                buffered = 0;
            }
        }
        String[] copied = new String[arrayLength];
        for(int i = 0; i < arrayLength; i++)
            copied[i] = array[i].toString();
        return copied;
    }
}
