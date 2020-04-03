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
        this.d = new Dimension(1500, 1000);
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
        for (int[] comp : components) {
            for (int i = 0; i < n; i++) {
                if (comp[i] == 1) {
                    for (int j = 0; j < n; j++) {
                        for (int k = i + 1; k < c; k++) {
                            if (matrix[i][j] == 1 && components.get(k)[j] == 1) {
                                condensed[i][k] = 1;
                            }
                            if (matrix[j][i] == 1 && components.get(k)[j] == 1) {
                                condensed[k][i] = 1;
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
            text = text.trim().replace(" ", ", ");
            String index = String.valueOf(Character.toChars(Integer.parseInt("208" + (i + 1), 16)));
            JLabel compLabel = new JLabel("K" + index + " = { " + text + " }");
            compLabel.setBounds(1200 + condensed.length * 10, 65 + 20 * i, d.width, 20);
            compLabel.setFont(this.FONT);
            this.add(compLabel);
        }
        Graph graph = new Graph(condensed, true);
        graph.getVertices();
        graph.getEdges();
        graph.draw(this);
    }
}