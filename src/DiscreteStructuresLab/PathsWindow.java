package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class PathsWindow  extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;

    PathsWindow(int[][] matrix) {
        super("Шляхи");
        this.matrix = matrix;
        this.d = new Dimension(1920, 300 + 40 * matrix.length);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        int n = matrix.length;
        int[][] matrixCopy = new int[n][n];
        int[][] paths2 = new int[n][n];
        int[][] paths3 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixCopy[i][j] = matrix[i][j];
            }
            matrixCopy[i][i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int c1 = 0;
            int c2 = 0;
            for (int j = 0; j < n; j++) {
                if (matrixCopy[i][j] == 1) {
                    for (int k = 0; k < n; k++) {
                        if (matrixCopy[j][k] == 1) {
                            paths2[i][k] = 1;
                            String p2 = (i + 1) + " -> " + (j + 1) + " -> " + (k + 1);
                            JLabel matrixLabel = new JLabel(p2);
                            matrixLabel.setBounds(300 + 130 * i, 50 + 20 * c1, d.width, 20);
                            matrixLabel.setFont(this.FONT);
                            this.add(matrixLabel);
                            c1++;
                            for (int m = 0; m < n; m++) {
                                if (matrixCopy[k][m] == 1) {
                                    paths3[i][m] = 1;
                                    String p3 = (i + 1) + " -> " + (j + 1) + " -> " + (k + 1) + " -> " + (m + 1);
                                    JLabel matrixLabel2 = new JLabel(p3);
                                    matrixLabel2.setBounds(300 + 130 * i, 90 + 20 * n + 20 * c2, d.width, 20);
                                    matrixLabel2.setFont(this.FONT);
                                    this.add(matrixLabel2);
                                    c2++;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + paths2[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(50, 50 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + paths3[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(50, 90 + 20 * n + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
    }
}