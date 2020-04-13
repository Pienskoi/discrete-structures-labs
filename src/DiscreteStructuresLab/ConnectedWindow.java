package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConnectedWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;

    ConnectedWindow(int[][] matrix) {
        super("Матриця зв'язності і компоненти сильної зв'язності");
        this.matrix = matrix;
        this.d = new Dimension(90 + 40 * matrix.length, 80 + 20 * matrix.length);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        int n = matrix.length;
        int[][] reach = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, reach[i], 0, n);
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
        int[][] transpose = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transpose[j][i] = reach[i][j];
            }
        }
        int[][] connected = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                connected[i][j] = reach[i][j] * transpose[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + connected[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(30,  30 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
        ArrayList<Integer> counter = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < n; i++) {
            String text = "";
            for (int j = i; j < n; j++) {
                if (!counter.contains(j)) {
                    if (connected[i][j] == 1) {
                        counter.add(j);
                        text += (j + 1) + " ";
                    }
                }
            }
            if (text.length() > 0) {
                text = text.trim().replace(" ", ", ");
                String index = String.valueOf(Character.toChars(Integer.parseInt("208" + (c + 1), 16)));
                JLabel compLabel = new JLabel("K" + index + " = { " + text + " }");
                compLabel.setBounds(60 + 16 * n, 30 + 20 * c, d.width, 20);
                compLabel.setFont(this.FONT);
                this.add(compLabel);
                c++;
            }
        }
    }
}