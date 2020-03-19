package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Window extends JFrame {
    private int n1 = 9, n2 = 4, n3 = 2, n4 = 2;
    private boolean directed = true;
    private HashMap<Integer, Circle> vertices;
    private Dimension d = new Dimension(1000, 1000);
    private final Font FONT = new Font("Arial", Font.BOLD, 15);

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
                .drawGraph(matrix, this.directed)
                .drawMatrix(matrix);
    }

    private void drawMatrix(int[][] matrix) {
        JFrame frame = new JFrame("Info");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(450 + 20 * matrix.length, 200 + 20 * matrix.length));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(1000, 100);
        for (int i = 0; i < matrix.length; i++) {
            String mat = "";
            for (int j = 0; j < matrix[i].length; j++) {
                mat = mat.concat("  " + matrix[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(50, 50 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            frame.add(matrixLabel);
        }
        HashSet<Integer> inDegrees = new HashSet<>();
        HashSet<Integer> outDegrees = new HashSet<>();
        String pendant = "Висячі вершини: ";
        String isolated = "Ізольовані вершини: ";
        for (Integer i : vertices.keySet()) {
            Circle vertex = vertices.get(i);
            inDegrees.add(vertex.inDegree);
            outDegrees.add(vertex.outDegree);
            int sumDeg = vertex.outDegree + vertex.inDegree;
            int m = i;
            String index = "";
            while(m > 0) {
                int a = m % 10;
                String num = String.valueOf(Character.toChars(Integer.parseInt("208" + a, 16)));
                index = num + index;
                m = m / 10;
            }
            String deg = "";
            if (directed) {
                String degPlus = "δ" + "\u207A" + "(v" + index + ") = " + vertex.outDegree;
                String degMinus = "δ" + "\u207B" + "(v" + index + ") = " + vertex.inDegree;
                deg = degPlus + "   " + degMinus;

            } else {
                deg = "δ(v" + index + ") = " + sumDeg;
            }
            JLabel degLabel = new JLabel(deg);
            degLabel.setBounds(400, 50 + 20 * (i - 1), d.width, 20);
            degLabel.setFont(this.FONT);
            frame.add(degLabel);
            if (sumDeg == 1) {
                pendant += "δ" + index + " ";
            }
            if (sumDeg == 0) {
                isolated += "δ" + index + " ";
            }
        }
        JLabel pendantLabel = new JLabel(pendant);
        pendantLabel.setBounds(400, 300 , d.width, 20);
        pendantLabel.setFont(this.FONT);
        frame.add(pendantLabel);
        JLabel isolatedLabel = new JLabel(isolated);
        isolatedLabel.setBounds(400, 320 , d.width, 20);
        isolatedLabel.setFont(this.FONT);
        frame.add(isolatedLabel);
        String reg = "";
        if (inDegrees.size() <= 1 && outDegrees.size() <= 1) {
            reg = "Граф однорідний";
        } else {
            reg = "Граф неоднорідний";
        }
        JLabel regLabel = new JLabel(reg);
        regLabel.setBounds(400, 340, d.width, 20);
        regLabel.setFont(this.FONT);
        frame.add(regLabel);
        frame.setVisible(true);
    }

    public Window drawGraph(int[][] matrix, boolean directed) {
        Graph graph = new Graph(matrix, directed);
        graph.drawVertices(this);
        graph.drawEdges(this);
        this.vertices = graph.getVertices();
        return this;
    }
}
