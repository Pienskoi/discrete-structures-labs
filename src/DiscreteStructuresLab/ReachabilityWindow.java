package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class ReachabilityWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;

    ReachabilityWindow(int[][] matrix) {
        super("Матриця досяжності");
        this.matrix = matrix;
        this.d = new Dimension(40 + 20 * matrix.length, 80 + 20 * matrix.length);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        int n = matrix.length;
        int[][] reach = matrix;
        for (int i = 0; i < n; i++) {
            reach[i][i] = 1;
        }
        for (int k = 0; k < n; k++) {
            for (int i =0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (reach[i][j] == 1 || (reach[i][k] == 1 && reach[k][j] == 1))
                        reach[i][j] = 1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + reach[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(30,  30 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
    }
}