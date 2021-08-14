package seamcarving;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        List<Integer> result = new ArrayList<>();

        // initialize 2D array
        double[][] list = new double[picture.width()][picture.height()];

        for (int i = 0; i < picture.height(); i++) {
            // energy for leftmost column
            list[0][i] = f.apply(picture, 0, i);
        }

        // energy in other columns
        for (int i = 1; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                list[i][j] = f.apply(picture, i, j);
                if (j == 0) {
                    list[i][j] = f.apply(picture, i, j) + Math.min(list[i-1][j], list[i-1][j+1]);
                } else if (j == picture.height() - 1) {
                    list[i][j] = f.apply(picture, i, j) + Math.min(list[i-1][j-1], list[i-1][j]);
                } else {
                    list[i][j] = f.apply(picture, i, j) + Math.min(Math.min(list[i-1][j-1],
                            list[i-1][j]), list[i-1][j+1]);
                }
            }
        }

        // find the smallest value in the right most column
        int yEnd = 0;
        double min = list[picture.width() - 1][0];
        for (int i = 1; i < picture.height(); i++) {
            double value = list[picture.width() - 1][i];
            if (value < min) {
                min = value;
                yEnd = i;
            }
        }
        result.add(yEnd);

        // find the shortest path on other columns
        for (int i = picture.width() - 2; i >= 0; i--) {
            if (yEnd == 0) {
                if (list[i][1] < list[i][0]) {
                    yEnd = 1;
                }
            } else if (yEnd == picture.height() - 1) {
                if (list[i][yEnd] < list[i][yEnd - 1]) {
                    yEnd = picture.height() - 1;
                } else {
                    yEnd = picture.height() - 2;
                }
            } else {
                double minValue = Math.min(Math.min(list[i][yEnd - 1], list[i][yEnd]), list[i][yEnd + 1]);
                if (minValue == list[i][yEnd - 1]) {
                    yEnd = yEnd - 1;
                } else if (minValue == list[i][yEnd + 1]) {
                    yEnd = yEnd + 1;
                }
            }

            result.add(yEnd);
        }

        Collections.reverse(result);
        return result;
    }
}
