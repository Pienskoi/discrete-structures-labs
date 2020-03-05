package DiscreteStructuresLab;

import java.util.Random;

public class Matrix {

    public static int[][] generateMatrix(int n1, int n2, int n3, int n4, boolean symmetric) {
        int n = 10 + n3;
        int[][] matrix = new int[n][n];
        Random random = new Random(n1 * 1000 + n2 * 100 + n3 * 10 + n4);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double element = (random.nextDouble() + random.nextDouble()) * (1 - n3 * 0.02 - n4 * 0.005 - 0.25);
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
