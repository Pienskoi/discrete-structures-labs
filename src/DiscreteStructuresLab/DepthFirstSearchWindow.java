package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class DepthFirstSearchWindow extends JFrame {
    private int[][] matrix, newMatrix, treeMatrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private boolean directed, tree = false;
    private int[] newArr;
    private Stack<Integer> stack;
    private int vertex, counter;
    private JComboBox<Integer> vBox;
    private Graph graph;

    DepthFirstSearchWindow(int[][] matrix, boolean directed) {
        super("Пошук вглиб");
        this.matrix = matrix;
        this.treeMatrix = new int[matrix.length][matrix.length];
        this.newMatrix = new int[matrix.length][matrix.length];
        this.d = new Dimension(1550, 1000);
        this.directed = directed;
        this.newArr = new int[matrix.length];
        this.stack = new Stack<>();
        this.graph = new Graph(matrix, directed);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        graph.draw(this);
        int n = matrix.length;
        JLabel label = new JLabel("Початкова вершина:");
        label.setBounds(1100, 65, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        Integer[] vertices = new Integer[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = i + 1;
        }
        this.vBox = new JComboBox<>(vertices);
        vBox.setBounds(1265, 65, 50, 30);
        vBox.setFont(this.FONT);
        vBox.setBackground(Color.WHITE);
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        vBox.setRenderer(listRenderer);
        vBox.setFocusable(false);
        vBox.setMaximumRowCount(12);
        vBox.setSelectedIndex(0);
        this.add(vBox);
        JButton b = new JButton("Розпочати обхід");
        b.setBounds(1320, 65, 170, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Start DFS");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public JComboBox<Integer> getComboBox() { return this.vBox; }
    public void start(int v) {
        this.vertex = v;
        newArr[vertex - 1] = 1;
        counter = 1;
        newMatrix[vertex - 1][counter - 1] = 1;
        stack.push(vertex);
        this.clear();
        graph.changeVertexColor(vertex, Color.RED);
        graph.changeVertexNumber(vertex, counter);
        graph.draw(this);
        this.drawNextButton();
        this.drawMatrices(counter);
    }
    public void search() {
        int v1 = vertex;
        graph.changeVertexColor(v1, Color.BLUE);
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (matrix[vertex - 1][i] == 1 && newArr[i] == 0 && v1 == vertex) {
                counter++;
                newArr[i] = counter;
                vertex = i + 1;
                stack.push(vertex);
                graph.changeEdgeColor(v1, vertex, Color.BLUE);
                graph.changeVertexNumber(vertex, counter);
                treeMatrix[newArr[v1 - 1] - 1][counter - 1] = 1;
                newMatrix[vertex - 1][counter - 1] = 1;
            }
        }
        if (stack.size() > 1) {
            if (v1 == vertex) {
                stack.pop();
                vertex = stack.peek();
            }
            this.clear();
            graph.changeVertexColor(vertex, Color.RED);
            graph.draw(this);
            this.drawNextButton();
            this.drawMatrices(counter);
            this.revalidate();
            this.repaint();
        } else {
            graph.changeVertexColor(vertex, Color.BLUE);
            this.finish();
        }
    }
    public void finish() {
        this.clear();
        this.drawMatrices(matrix.length);
        JButton b = new JButton();
        if (tree) {
            this.drawTree();
            b.setText("Показати граф з одержаною нумерацією");
        } else {
            graph.draw(this);
            b.setText("Показати дерево обходу");
        }
        b.setBounds(1100, 65, 390, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Show tree or graph");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void clear() {
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }
    public void drawNextButton() {
        JButton b = new JButton("Далі");
        b.setBounds(1100, 65, 390, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Next DFS step");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
    }
    public void drawMatrices(int n) {
        for (int i = 0; i < newMatrix.length; i++) {
            String mat = "";
            for (int j = 0; j < newMatrix.length; j++) {
                mat = mat.concat("  " + newMatrix[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(1100, 125 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + treeMatrix[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(1100, 155 + 20 * newMatrix.length + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
    }
    public void changeGraphToTree() {this.tree = !this.tree; }
    public void drawTree() {
        Graph tree = new Graph(treeMatrix, directed);
        tree.draw(this);
    }
}
