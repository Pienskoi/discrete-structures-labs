package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

public class DijkstraWindow extends  JFrame{
    private int[][] weightMatrix, paths;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private JComboBox<Integer> vBox;
    private Graph graph;
    private int[] dist, prev;
    private HashSet<Integer> temp = new HashSet<>();
    private int first, v;

    DijkstraWindow(int[][] matrix, int[][] weightMatrix, boolean directed) {
        super("Пошук найкоротшого шляху");
        this.weightMatrix = weightMatrix;
        this.paths = new int[matrix.length][matrix.length];
        this.d = new Dimension(1550, 1000);
        this.graph = new Graph(matrix, directed);
        this.graph.addWeightToEdges(weightMatrix);
        this.dist = new int[matrix.length];
        this.prev = new int[matrix.length];
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        graph.draw(this);
        int n = weightMatrix.length;
        JLabel label = new JLabel("Початкова вершина:");
        label.setBounds(1100, 65, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        Integer[] vertices = new Integer[n];
        for (int i = 0; i < n; i++) {
            if (graph.getDegree(i) != 0) {
                vertices[i] = i + 1;
            }
        }
        this.vBox = new JComboBox<>(vertices);
        vBox.setBounds(1265, 65, 50, 30);
        vBox.setFont(this.FONT);
        vBox.setBackground(Color.WHITE);
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        vBox.setRenderer(listRenderer);
        vBox.setFocusable(false);
        vBox.setMaximumRowCount(12);
        vBox.setSelectedIndex(0);
        this.add(vBox);
        JButton b = new JButton("Розпочати пошук");
        b.setBounds(1320, 65, 200, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Start Dijkstra");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
        this.drawMatrix();
    }
    public void drawMatrix() {
        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix.length; j++) {
                String element = weightMatrix[i][j] + "";
                JLabel matrixLabel = new JLabel(element);
                matrixLabel.setBounds(1100 + 35 * j, 155 + 25 * i, d.width, 20);
                matrixLabel.setFont(this.FONT);
                this.add(matrixLabel);
            }
        }
    }
    public JComboBox<Integer> getComboBox() { return this.vBox; }
    public void start(int v) {
        this.v = v;
        this.first = v;
        int n = weightMatrix.length;
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            if (graph.getDegree(i) != 0) {
                temp.add(i);
            }
        }
        dist[v] = 0;
        temp.remove(v);
        graph.changeVertexColor(v + 1, Color.BLUE);
        this.clear();
        graph.draw(this);
        this.drawNextButton();
        this.drawMatrix();
        this.drawDistances();
    }
    public void search() {
        int n = weightMatrix.length;
        graph.changeVertexColor(v + 1, Color.BLUE);
        for (int i = 0; i < n; i++) {
            if (weightMatrix[v][i] > 0 && temp.contains(i)) {
                int alt = dist[v] + weightMatrix[v][i];
                if (alt < dist[i]) {
                    dist[i] = alt;
                    prev[i] = v + 1;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (temp.contains(i) && dist[i] < min) {
                min = dist[i];
                this.v = i;
            }
        }
        if (temp.isEmpty()) {
            this.finish();
        } else {
            this.clear();
            graph.draw(this);
            this.drawNextButton();
            this.drawMatrix();
            this.drawDistances();
            temp.remove(v);
        }
    }
    public void finish() {
        vBox.removeItemAt(first);
        vBox.setSelectedIndex(v);
        int n = paths.length;
        for (int i = 0; i < n; i++) {
            int c = 1;
            int vertex = i;
            while (vertex != first && graph.getDegree(vertex) != 0) {
                paths[i][vertex] = c;
                c++;
                vertex = prev[vertex] - 1;
            }
            paths[i][first] = c;
        }
        this.clear();
        graph.draw(this);
        this.drawMatrix();
        this.drawDistances();
        this.add(vBox);
        this.drawPathButton();
    }
    public void clear() {
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }
    public void drawNextButton() {
        JButton b = new JButton("Далі");
        b.setBounds(1100, 65, 400, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Next Dijkstra step");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void drawDistances() {
        int n = weightMatrix.length;
        JLabel label = new JLabel("Початкова вершина:   " + (first + 1));
        label.setBounds(1100, 35, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        for (int i = 0; i < n; i++) {
            if (graph.getDegree(i) != 0) {
                String distText = "Відстань: ";
                String prevText = "Попередник: ";
                if (dist[i] == Integer.MAX_VALUE) distText += "∞";
                else distText += dist[i];
                if (prev[i] == 0) prevText += "—";
                else prevText += prev[i];
                JLabel vertexLabel = new JLabel((i + 1) + "");
                JLabel distLabel = new JLabel(distText);
                JLabel prevLabel = new JLabel(prevText);
                vertexLabel.setBounds(1100, 185 + 25 * n + 20 * i, d.width, 20);
                vertexLabel.setFont(this.FONT);
                distLabel.setBounds(1150, 185 + 25 * n + 20 * i, d.width, 20);
                distLabel.setFont(this.FONT);
                prevLabel.setBounds(1300, 185 + 25 * n + 20 * i, d.width, 20);
                prevLabel.setFont(this.FONT);
                if (!temp.contains(i)) {
                    vertexLabel.setForeground(Color.BLUE);
                    distLabel.setForeground(Color.BLUE);
                    prevLabel.setForeground(Color.BLUE);
                }
                this.add(vertexLabel);
                this.add(distLabel);
                this.add(prevLabel);
            } else {
                String text = "Вершина " + (i + 1) + " ізольована";
                JLabel isolatedLabel = new JLabel(text);
                isolatedLabel.setBounds(1100, 185 + 25 * n + 20 * i, d.width, 20);
                isolatedLabel.setFont(this.FONT);
                this.add(isolatedLabel);
            }
        }
    }
    public void drawPathButton() {
        JLabel label = new JLabel("Кінцева вершина: ");
        label.setBounds(1100, 65, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        JButton b = new JButton("Показати шлях");
        b.setBounds(1320, 65, 200, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Show path");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void drawPath(int last) {
        this.clear();
        int c = -1;
        for (int i = 0; i < paths.length; i++) {
            if (paths[last][i] > 0) {
                JLabel label = new JLabel(i + 1 + "");
                label.setBounds(1500 - 40 * (paths[last][i] - 1), 105, d.width, 30);
                label.setFont(this.FONT);
                this.add(label);
                c++;
            }
        }
        for (int i = 0; i < c; i++) {
            JLabel label = new JLabel("->");
            label.setBounds(1480 - 40 * i, 105, d.width, 30);
            label.setFont(this.FONT);
            this.add(label);
        }
        graph.drawShortestPath(paths[last], this);
        this.drawMatrix();
        this.drawDistances();
        this.add(vBox);
        this.drawPathButton();
    }
}
