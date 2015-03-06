package by.vduzh.ysura.garage.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * User: Viktar Duzh
 * Created: 04.03.15 9:15
 */
public class Util {
    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value. Must be greater than min.
     * @return Integer between min and max, inclusive.
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Randomly permutes the specified list of indexes of array.
     *
     * @param max the size of arrau
     * @return list of indexes
     */
    public static ArrayList<Integer> randIndexed(int max) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < max; i++) {
            ids.add(i);
        }

        Collections.shuffle(ids);

        return ids;
    }

}
