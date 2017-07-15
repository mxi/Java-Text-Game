package newGame.Mapping;

import newGame.IntDimension;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An ArrayList-like data structure, but it attempts
 * to emulate a resizable 2d array.
 *
 * Efficiency:
 * Calculated under conditions on 7/12/17:
 * - Added 1 item to 0, 0
 * - Printed the representation of the 2d array via printToTerminal()
 * - Retrieved a piece of data from 0, 0
 * Results: ~1/50000 of a second.
 *
 * @param <T> An object to be contained within the 2d ArrayList.
 * @author Max
 */
public class ArrayList2D<T> {

    private boolean locked = false; // Determines if the 2d array can be resized.
    private List<List<T>> list2d = new ArrayList<>(); // 2d list.
    private IntDimension dimensions; // Dimensions of the 2d array.

    /**
     * Constructor for ArrayList2D:
     * Initializes list2d, width, and height with default parameters.
     * Then it adjusts the 2d list to match the width and height.
     */
    public ArrayList2D() {
        dimensions = new IntDimension(1, 1);
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
        dimensions = new IntDimension(Math.max(w, 0), Math.max(h, 0));
        adjust();
    }

    /**
     * Returns the value of the 'locked' boolean; determining
     * whether this 2d array can be resized or not.
     * @return Whether this 2d array can be resized.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the value of the 'locked' boolean; determining
     * whether this 2d array can be resized or not.
     * @param bool Value for the locked var.
     */
    public void setLocked(boolean bool) {
        locked = bool;
    }

    /**
     * Determines if this 2d array is filled (meaning there are no
     * null values).
     * @return True if the 2d array is full.
     */
    public boolean isFull() {
        return getElementCount() == getSpace();
    }

    /**
     * Gets the calculated amount of space in the current 2d array.
     * @return Calculated space.
     */
    public int getSpace() {
        return getWidth() * getHeight();
    }

    /**
     * Gets the available amount of space.
     * @return Available amount of space.
     */
    public int getAvailableSpace() {
        return getSpace() - getElementCount();
    }

    /**
     * Gets the percentage of used space in the 2d array.
     * @return Percentage of space used in 2d array.
     */
    public float getPercentageUsed() {
        return ((float) getElementCount() / (float) getSpace()) * 100f;
    }

    /**
     * Gets the percentage of space available in the 2d array.
     * @return Percentage of space available in 2d array.
     */
    public float getPercentageFree() {
        return 100f - getPercentageUsed();
    }

    /**
     * Returns the total number of elements in the 2d array.
     * @return Total non-null elements in 2d array.
     */
    public int getElementCount() {
        int total = 0;
        for(List<T> row : list2d) {
            for(final T elem : row) {
                if(elem != null)
                    total++;
            }
        }
        return total;
    }

    /**
     * Fills the 2d array with specified type.
     * @param type A type to replace null or non equivalent objects.
     */
    public void fill(T type) {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                list.set(i, type);
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
            }
        }
    }

    /**
     * Replaces all non-null values in the 2d array with a specified type.
     * @param type A type to replace non-null objects.
     */
    public void fillOccupiedOnly(T type) {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                final T item = list.get(i);
                if(item != null) {
                    list.set(i, type);
                }
            }
        }
    }

    /**
     * Sets a type at all coordinates based on a predicate.
     * @param type Type to replace if predicate is true.
     * @param pred Evaluation interface to decide whether to replace an item or not.
     */
    public void fillIf(T type, If<T> pred) {
        for(List<T> list : list2d) {
            for(int i = 0; i < list.size(); i++) {
                final T item = list.get(i);
                if(pred.test(item)) {
                    list.set(i, type);
                }
            }
        }
    }

    /**
     * Scans the entire 2d array and performs action
     * based on the ForEach interface in the parameters.
     * @param feobj ForEach interface to dictate action for each element.
     */
    public void forEach(ForEach<T> feobj) {
        for(List<T> row : list2d) {
            for(final T item : row) {
                feobj.onelem(item);
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
        if(locked) {
            return;
        }
        dimensions.setWidth(Math.max(w, 0));
        fitToWidth(dimensions.getWidth());
    }

    /**
     * @return Width of 2d array.
     */
    public int getWidth() {
        return (dimensions.getWidth() + 1);
    }

    /**
     * Sets a new height for this 2d array.
     * @param h New height.
     */
    public void setHeight(int h) {
        if(locked) {
            return;
        }
        dimensions.setHeight(Math.max(h, 0));
        fitToHeight(dimensions.getHeight());
    }

    /**
     * @return Height of the 2d array.
     */
    public int getHeight() {
        return (dimensions.getHeight() + 1);
    }

    /**
     * Sets the dimensions of the 2d array.
     * @param w Width of 2d array.
     * @param h Height of 2d array.
     */
    public void setDimensions(int w, int h) {
        if(locked) {
            return;
        }
        setHeight(h);
        setWidth(w);
    }

    public IntDimension getDimensions() {
        return new IntDimension(getWidth(), getHeight());
    }

    /**
     * Determines whether the specified coordinates fit the
     * current size of the 2d array.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return Whether the coordinates are valid for the 2d array size.
     */
    public boolean inBounds(int x, int y) {
        return ( (x >= 0 && x < dimensions.getWidth() && (y >= 0 && y < dimensions.getHeight()) ));
    }

    /**
     * Adds an element to the next available spot.
     * @param elem Element to be added.
     * @return True if it is able to find an open place to put the element in.
     */
    public boolean addElement(T elem) {
        for(List<T> row : list2d) {
            for(int i = 0; i < row.size(); i++) {
                if(row.get(i) == null) {
                    row.set(i, elem);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Forces an element into the 2d array (if the 2d array is
     * not locked) by adding a new row.
     * @param elem Element to be forced (if it can)
     */
    public void forceElement(T elem) {
        if(isFull() && !locked) {
            setHeight(dimensions.getHeight() + 1);
            addElement(elem);
        }
    }

    /**
     * Removes (aka. sets null) a specified element (if any) from the specified coordinates.
     * @param x X coordinate.
     * @param y Y coordinate
     * @return True if it was able to remove an element from specified coordinates.
     */
    public boolean removeElement(int x, int y) {
        if(inBounds(x, y) || isEmpty(x, y)) {
            return false;
        }
        list2d.get(y).set(x, null);
        return true;
    }

    /**
     * Removes any items that fit the predicate passed in the parameters.
     * @param pred Evaluation interface to decide whether to remove the item or not.
     */
    public void removeIf(If<T> pred) {
        for(List<T> row : list2d) {
            for(int i = 0; i < row.size(); i++) {
                if(pred.test(row.get(i))) {
                    row.set(i, null);
                }
            }
        }
    }

    /**
     * Places an element in specified coordinates.
     * @param elem Element.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return True if the element was successfully placed in the specified coordinates.
     */
    public boolean setElement(T elem, int x, int y) {
        if(locked && inBounds(x, y)) {
            list2d.get(y).set(x, elem);
            return true;
        }
        else if(!locked) {
            if(x < 0 || y < 0) {
                return false;
            }
            if(x >= dimensions.getWidth()) {
                setWidth(x);
            }
            if(y >= dimensions.getHeight()) {
                setHeight(y);
            }
            list2d.get(y).set(x, elem);
            return true;
        }
        return false;
    }

    /**
     * Gets an elements in specified coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return Object in those coordinates.
     */
    public T getElement(int x, int y) {
        if(isEmpty(x, y)) {
            return null;
        }
        return list2d.get(y).get(x);
    }

    /**
     * Checks to see if specified coordinates point to an empty
     * space in the 2d array.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return True if the object in row.index(x) is null.
     */
    public boolean isEmpty(int x, int y) {
        if((x < 0 || x > dimensions.getWidth()) || (y < 0 || y > dimensions.getHeight())) {
            return true;
        }
        return list2d.get(y).get(x) == null;
    }

    /**
     * Adjusts the dimensions of the two lists to
     * be compatible with the data members 'width' and 'height'
     */
    public void adjust() {
        fitToHeight(dimensions.getHeight());
        fitToWidth(dimensions.getWidth());
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
            out.flush();
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
                while(elems.size() <= dimensions.getWidth()) {
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

    /**
     * Interface for the method "foreach" in the 2d array.
     * @param <T> Generic type.
     */
    public interface ForEach<T> {
        /**
         * Actions that will be performed by the caller
         * for each item that the foreach function passes by.
         * @param item
         */
        void onelem(T item);
    }

    /**
     * Interface for the method "fillIf/removeIf" in the 2d array.
     * @param <T> Generic type.
     */
    public interface If<T> {
        /**
         * A test case to check whether a specific item fits a condition
         * specified by the caller.
         * @param item Element to test against.
         * @return Result of the test (predicate).
         */
        boolean test(T item);
    }

    /**
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return list2d.toString();
    }
}
