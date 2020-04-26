package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CondensedWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;

    CondensedWindow(int[][] matrix) {
        super("Граф конденсації");
        this.matrix = matrix;
        this.d = new Dimension(1600, 1000);
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
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<int[]> components = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] comp = new int[n];
            for (int j = i; j < n; j++) {
                if (!counter.contains(j)) {
                    if (connected[i][j] == 1) {
                        comp[j] = 1;
                        if (!counter.contains(i)) {
                            components.add(comp);
                        }
                        counter.add(j);
                    }
                }
            }

        }
        int c = components.size();
        int[][] condensed = new int[c][c];
        for (int i = 0; i < c; i++) {
            int[] comp1 = components.get(i);
            for (int j = 0; j < n; j++) {
                if (comp1[j] == 1) {
                    for (int k = 0; k < n; k++) {
                        for (int l = i + 1; l < c; l++) {
                            int[] comp2 = components.get(l);
                            if (matrix[j][k] == 1 && comp2[k] == 1) {
                                condensed[i][l] = 1;
                            }
                            if (matrix[k][j] == 1 && comp2[k] == 1) {
                                condensed[l][i] = 1;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < condensed.length; i++) {
            String mat = "";
            for (int j = 0; j < condensed.length; j++) {
                mat = mat.concat("  " + condensed[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(1100,  65 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
        for (int i = 0; i < components.size(); i++) {
            int[] comp = components.get(i);
            String text = "";
            for (int j = 0; j < comp.length; j++) {
                if (comp[j] == 1) {
                    text += (j + 1) + " ";
                }
            }
            int m = i + 1;
            String index = "";
            while (m > 0) {
                int a = m % 10;
                String num = String.valueOf(Character.toChars(Integer.parseInt("208" + a, 16)));
                index = num + index;
                m = m / 10;
            }
            text = text.trim().replace(" ", ", ");
            JLabel compLabel = new JLabel("K" + index + " = { " + text + " }");
            compLabel.setBounds(1200 + condensed.length * 10, 65 + 20 * i, d.width, 20);
            compLabel.setFont(this.FONT);
            this.add(compLabel);
        }
        Graph graph = new Graph(condensed, true);
        graph.draw(this);
    }
}