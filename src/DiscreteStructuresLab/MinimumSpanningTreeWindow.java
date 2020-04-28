package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MinimumSpanningTreeWindow extends JFrame {
    private int[][] weightMatrix, treeMatrix, bannedMatrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private boolean tree = false;
    private Graph graph;
    private ArrayList<Integer> weights = new ArrayList<>();
    private int min, counter = 0;

    MinimumSpanningTreeWindow(int[][] matrix, int[][] weightMatrix, boolean directed) {
        super("Знаходження мінімального кістяка графа");
        this.weightMatrix = weightMatrix;
        this.treeMatrix = new int[matrix.length][matrix.length];
        this.bannedMatrix = new int[matrix.length][matrix.length];
        this.d = new Dimension(1550, 1000);
        this.graph = new Graph(matrix, directed);
        this.graph.addWeightToEdges(weightMatrix);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        graph.draw(this);
        JButton b = new JButton("Розпочати");
        b.setBounds(1100, 65, 390, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Next MST step");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
        this.drawMatrix();
        this.sort();
    }
    public void drawMatrix() {
        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix.length; j++) {
                String element = weightMatrix[i][j] + "";
                JLabel matrixLabel = new JLabel(element);
                matrixLabel.setBounds(1100 + 35 * j, 125 + 25 * i, d.width, 20);
                matrixLabel.setFont(this.FONT);
                this.add(matrixLabel);
            }
        }
    }
    public void clear() {
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }
    public void find() {
        int n = weightMatrix.length;
        min = weights.get(0);
        weights.remove(0);
        LOOP:
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (weightMatrix[i][j] == min && treeMatrix[i][j] == 0 && bannedMatrix[i][j] == 0) {
                    if (this.checkCycle(i, j)) {
                        graph.changeEdgeColor(i + 1, j + 1, Color.RED);
                        bannedMatrix[i][j] = 1;
                        bannedMatrix[j][i] = 1;
                    } else {
                        graph.addEdgeToTree(i + 1, j + 1);
                        graph.changeVertexColor(i + 1, Color.BLUE);
                        graph.changeVertexColor(j + 1, Color.BLUE);
                        treeMatrix[i][j] = min;
                        treeMatrix[j][i] = min;
                        counter++;
                    }
                    break LOOP;
                }
            }
        }
        if (counter == n - 1) {
            this.finish();
        } else {
            this.clear();
            this.drawMatrix();
            this.drawTreeMatrix();
            this.drawNextButton();
            graph.draw(this);
        }
    }
    public void sort() {
        int n = weightMatrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (weightMatrix[i][j] > 0) {
                    weights.add(weightMatrix[i][j]);
                }
            }
        }
        Collections.sort(weights);
    }
    public boolean checkCycle(int u, int v) {
        ArrayList<Integer> vertices = new ArrayList<>();
        this.trail(u, vertices);
        return vertices.contains(v) || u == v;
    }
    public void trail(int v, ArrayList<Integer> vertices) {
        int n = treeMatrix.length;
        for (int i = 0; i < n; i++) {
            if (treeMatrix[v][i] >= 1 && !vertices.contains(i)) {
                vertices.add(i);
                this.trail(i, vertices);
            }
        }
    }
    public void finish() {
        this.clear();
        this.drawMatrix();
        this.drawTreeMatrix();
        JButton b = new JButton();
        if (tree) {
            graph.drawTree(this);
            b.setText("Показати граф");
        } else {
            graph.draw(this);
            b.setText("Показати кістяк");
        }
        b.setBounds(1100, 65, 390, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Show MST tree or graph");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void drawTreeMatrix() {
        for (int i = 0; i < treeMatrix.length; i++) {
            for (int j = 0; j < treeMatrix.length; j++) {
                String element = treeMatrix[i][j] + "";
                JLabel matrixLabel = new JLabel(element);
                matrixLabel.setBounds(1100 + 35 * j, 155 + 25 * weightMatrix.length + 25 * i, d.width, 20);
                matrixLabel.setFont(this.FONT);
                this.add(matrixLabel);
            }
        }
    }
    public void drawNextButton() {
        JButton b = new JButton("Далі");
        b.setBounds(1100, 65, 390, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Next MST step");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void changeGraphToTree() { this.tree = !this.tree; }
}
