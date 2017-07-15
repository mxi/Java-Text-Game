package newGame.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max
 * This class is essentially list within a list
 * (to mimic a 2d array like 'Object[][]') but so
 * it can be dynamically sized).
 */
public class GenericArray2D<O> {

    private List<List<O>> elements = new ArrayList<>(); // '2d array' of elements (default = blank arraylist)
    private int width = 0xF; // width of the elements (default = 16)
    private int height = 0xF; // height of the elements (default = 16)

    public GenericArray2D() {
        adjust();
    }

    public

    private void adjust() {

    }
}
