package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private int n1 = 9, n2 = 4, n3 = 2, n4 = 2;
    private boolean regular, directed = true;
    private Dimension d = new Dimension(1500, 1000);
    private final Font FONT = new Font("Arial", Font.BOLD, 16);

    Window(String title) {
        super(title);
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        this
                .drawDirectedButton()
                .createGraph();
    }

    private void createGraph() {
        int[][] matrix = Matrix.generateMatrix(this.n1, this.n2, this.n3, this.n4, !this.directed);
        this
                .drawDegreesButton(matrix, this.directed)
                .drawMatrix(matrix)
                .drawGraph(matrix, this.directed);
    }
    public void drawGraph(int[][] matrix, boolean directed) {
        Graph graph = new Graph(matrix, directed);
        graph.getVertices();
        graph.getEdges();
        graph.draw(this);
        this.regular = graph.regular();
    }
    public void changeOrientation() { this.directed = !this.directed; }
    public void redraw() {
        Component[] components = this.getContentPane().getComponents();
        for (Component component : components) component.setVisible(false);
        this.getContentPane().removeAll();
        this.init();
    }
    public Window drawDirectedButton() {
        String text = "";
        if (directed) text = "Граф напрямлений";
        else text = "Граф ненапрямлений";
        JLabel label = new JLabel(text);
        label.setBounds(1100, 65, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        JButton b = new JButton("Змінити");
        b.setBounds(1300, 65, 100, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Change Orientation");
        b.addActionListener(new ButtonListener(this));
        this.add(b);
        return this;
    }
    public Window drawDegreesButton(int[][] matrix, boolean directed) {
        String text = "";
        if (regular) text = "Граф однорідний";
        else text = "Граф неоднорідний";
        JLabel label = new JLabel(text);
        label.setBounds(1100, 95, d.width, 30);
        label.setFont(this.FONT);
        this.add(label);
        JButton b = new JButton("Степені");
        b.setBounds(1300, 95, 100, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Show Degrees Window");
        b.addActionListener(new ButtonListener(matrix, directed));
        this.add(b);
        return this;
    }
    public Window drawMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            String mat = "";
            for (int j = 0; j < matrix[i].length; j++) {
                mat = mat.concat("  " + matrix[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(1100, 155 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
        return this;
    }
}
