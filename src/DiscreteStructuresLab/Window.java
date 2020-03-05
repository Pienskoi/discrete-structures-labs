package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private int n1 = 9, n2 = 4, n3 = 2, n4 = 2;
    private boolean directed = true;
    private Dimension d = new Dimension(1000, 1000);
    private final Font FONT = new Font("Arial", Font.PLAIN, 16);

    Window(String title) {
        super(title);
        this.init();
    }

    public void init() {
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.createMatrix();
    }

    private void createMatrix() {
        int[][] matrix = Matrix.generateMatrix(this.n1, this.n2, this.n3, this.n4, !this.directed);
        this
                .drawMatrix(matrix)
                .drawGraph(matrix, this.directed);
    }

    private Window drawMatrix(int[][] matrix) {
        JFrame frame = new JFrame("Matrix");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(20 * matrix.length, 30 + 20 * matrix.length));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        for (int i = 0; i < matrix.length; i++) {
            String mat = "";
            for (int j = 0; j < matrix[i].length; j++) {
                mat = mat.concat("  " + matrix[i][j]);
            }
            mat = mat.trim();
            JLabel label = new JLabel(mat);
            label.setBounds(0, 20 * i, d.width, 20);
            label.setFont(this.FONT);
            frame.add(label);
        }
        return this;
    }

    public void drawGraph(int[][] matrix, boolean directed) {
        Graph graph = new Graph(matrix, directed);
        graph.drawVertices(this);
        graph.drawEdges(this);
    }
}
