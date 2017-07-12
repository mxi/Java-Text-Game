package newGame.Mapping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An ArrayList-like data structure, but it attempts
 * to emulate a resizable 2d array.
 *
 * Efficiency:
 * Calculated under conditions:
 * - Added 1 item to 0, 0
 * - Printed the representation of the 2d array via printToTerminal()
 * - Retrieved a piece of data from 0, 0
 * Results: ~1/50000 of a second.
 *
 * @param <T> An object to be contained within the 2d ArrayList.
 */
public class ArrayList2D<T> {

    private List<List<T>> list2d; // 2d list.
    private int width; // Width of the list.
    private int height; // Height of the list.

    /**
     * Constructor for ArrayList2D:
     * Initializes list2d, width, and height with default parameters.
     * Then it adjusts the 2d list to match the width and height.
     */
    public ArrayList2D() {
        list2d = new ArrayList<>();
        width = 1;
        height = 1;
        adjust();
    }

    /**
     * Constructor for ArrayList2D:
     * Initializes list2d, assigns width and height, and finally adjusts
     * the 2d array to match the width and height.
     * @param w Width of 2d array.
     * @param h Height of 2d array.
     */
    public ArrayList2D(int w, int h) {
        list2d = new ArrayList<>();
        width = Math.max(w, 0);
        height = Math.max(h, 0);
        adjust();
    }

    /**
     * Fills the 2d array with specified type.
     * @param type A type to replace null or non equivalent objects.
     */
    public void fill(T type) {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                final T item = list.get(i);
                if(item == null || item != type) {
                    list.set(i, type);
                }
            }
        }
    }

    /**
     * Replaces all null values in the 2d array with a specified type.
     * @param type A type to replace null objects.
     */
    public void fillNullOnly(T type) {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                final T item = list.get(i);
                if(item == null) {
                    list.set(i, type);
                }
            }
        }
    }

    /**
     * Clears the entire 2d array and replaces it with
     * null values.
     */
    public void purge() {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                list.set(i, null);
            }
        }
    }

    /**
     * Sets a new width for this 2d array.
     * @param w New width.
     */
    public void setWidth(int w) {
        width = Math.max(w, 0);
        fitToWidth(width);
    }

    /**
     * @return Width of 2d array.
     */
    public int getWidth() {
        return (width + 1);
    }

    /**
     * Sets a new height for this 2d array.
     * @param h New height.
     */
    public void setHeight(int h) {
        height = Math.max(h, 0);
        fitToHeight(height);
    }

    /**
     * @return Height of the 2d array.
     */
    public int getHeight() {
        return (height + 1);
    }

    /**
     * Places an element in specified coordinates.
     * @param elem Element.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return True if the element was successfully placed in the specified coordinates.
     */
    public boolean setElement(T elem, int x, int y) {
        if(x < 0 || y < 0) {
            return false;
        }
        if(x >= width) {
            setWidth(x);
        }
        if(y >= height) {
            setHeight(y);
        }
        list2d.get(y).set(x, elem);
        return true;
    }

    /**
     * Gets an elements in specified coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return Object in those coordinates.
     */
    public T getElement(int x, int y) {
        if((x < 0 || x > width) || (y < 0 || y > height)) {
            return null;
        }
        return list2d.get(y).get(x);
    }

    /**
     * Adjusts the dimensions of the two lists to
     * be compatible with the data members 'width' and 'height'
     */
    public void adjust() {
        fitToHeight(height);
        fitToWidth(width);
    }

    /**
     * Prints the layout of the 2d array into the console.
     * @param out OutputStream to write the data to.
     * @param close Whether to close the output stream after finish.
     * @deprecated Because it should not be used; it's only for testing.
     */
    @Deprecated
    public void printToTerminal(OutputStream out, boolean close) {
        final int lbrkt = 91; // Left square bracket '[' in ASCII
        final int rbrkt = 93; // Right square bracket ']' in ASCII
        final int asterisk = 42; // Asterisk '*' in ASCII
        final int obj = 79; //'O' for non-null objects in ASCII
        final int nline = 10; // '\n' character in ASCII
        try {
            for(List<T> row : list2d) {
                out.write(lbrkt);
                for(T elem : row) {
                    if(elem == null) {
                        out.write(asterisk);
                    }
                    else {
                        out.write(obj);
                    }
                }
                out.write(rbrkt);
                out.write(nline);
            }
            if(close) {
                out.close();
            }
        }
        catch(IOException e) {
            // Do nothing...
        }
    }

    /**
     * Fits the List<List<T>> to a specified height.
     * @param y Target height.
     */
    private void fitToHeight(int y) {
        if(list2d.size() != (y + 1)) {
            list2d = fitSize(list2d, y);
        }
    }

    /**
     * Fits the List<List<T>> to a specified width.
     * @param x Target width.
     */
    private void fitToWidth(int x) {
        for(int i = 0; i < list2d.size(); i++) {
            final List<T> list = list2d.get(i);
            if(list.size() != (x + 1)) {
                list2d.set(i, fitSize(list, null, x));
            }
        }
    }

    /**
     * Resizes the provided list to an indicated size.
     * @param cpy Copy of the list.
     * @param placeh Place holder if list.size() increases
     * @param targetsize Target (final) size of the list
     * @return Resized list.
     */
    private List<T> fitSize(List<T> cpy, T placeh, final int targetsize) {
        final int cursize = cpy.size();
        if(cursize <= targetsize) {
            while(cpy.size() <= targetsize) {
                cpy.add(placeh);
            }
        }
        else if(cursize > targetsize) {
            while(cpy.size() > targetsize) {
                cpy.remove(cpy.size() - 1);
            }
        }
        return cpy;
    }

    /**
     * Resizes the provided list to an indicated size.
     * @param cpy Copy of the list containing another list of T objects.
     * @param targetsize Target size of the list.
     * @return Resized list.
     */
    private List<List<T>> fitSize(List<List<T>> cpy, final int targetsize) {
        final int cursize = cpy.size();
        if(cursize <= targetsize) {
            while(cpy.size() <= targetsize) {
                final List<T> elems = new ArrayList<>();
                while(elems.size() <= width) {
                    elems.add(null);
                }
                cpy.add(elems);
            }
        }
        else if(cursize > targetsize) {
            while(cpy.size() > targetsize) {
                cpy.remove(cpy.size() - 1);
            }
        }
        return cpy;
    }
}
