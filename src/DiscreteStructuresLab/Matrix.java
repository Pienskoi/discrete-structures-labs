package DiscreteStructuresLab;

import java.util.Random;

public class Matrix {

    public static int[][] generateMatrix(int n1, int n2, int n3, int n4, int lab, boolean symmetric) {
        double labTask = 0;
        if (lab == 1) { labTask = 1 - n3 * 0.02 - n4 * 0.005 - 0.25; }
        if (lab == 2) { labTask = 1 - n3 * 0.01 - n4 * 0.01 - 0.3; }
        if (lab == 3) { labTask = 1 - n3 * 0.005 - n4 * 0.005 - 0.27; }
        if (lab == 4) { labTask = 1 - n3 * 0.01 - n4 * 0.005 - 0.15; }
        int n = 10 + n3;
        int[][] matrix = new int[n][n];
        Random random = new Random(n1 * 1000 + n2 * 100 + n3 * 10 + n4);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double element = (random.nextDouble() + random.nextDouble()) * labTask;
                matrix[i][j] = (int) Math.floor(element);
            }
        }
        if (symmetric) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (matrix[i][j] == 1) matrix[j][i] = 1;
                    else if (matrix[j][i] == 1) matrix[i][j] = 1;
        }
        return matrix;
    }
}
