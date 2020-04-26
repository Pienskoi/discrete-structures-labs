package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class PathsWindow  extends JFrame {
    private int[][] matrix, matrixCopy, paths2, paths3;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private int pathsLength = 2;
    private Dimension d;
    private JPanel panel = new JPanel();

    PathsWindow(int[][] matrix) {
        super("Шляхи");
        this.matrix = matrix;
        this.paths2 = new int[matrix.length][matrix.length];
        this.paths3 = new int[matrix.length][matrix.length];
        this.matrixCopy = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrix.length);
            matrixCopy[i][i] = 0;
        }
        this.d = new Dimension(1920, 300 + 20 * matrix.length);
        JScrollPane scrollBar = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollBar);
        this.panel.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (matrixCopy[i][j] == 1 && matrixCopy[j][k] == 1) {
                        paths2[i][k] = 1;
                        for (int m = 0; m < n; m++) {
                            if (matrixCopy[k][m] == 1) {
                                paths3[i][m] = 1;
                            }
                        }
                    }
                }
            }
        }
        JLabel label = new JLabel("Шляхи довжиною:");
        label.setBounds(50, 50, d.width, 30);
        label.setFont(this.FONT);
        panel.add(label);
        JButton b = new JButton(pathsLength + "");
        b.setBounds(200, 50, 50, 30);
        b.setFont(this.FONT);
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setActionCommand("Show Paths");
        b.addActionListener(new ButtonListener(this));
        panel.add(b);
        if (pathsLength == 2) this.drawPaths2();
        else this.drawPaths3();
    }
    public void drawPaths2() {
        int n = matrix.length;
        int cMax = 0;
        for (int i = 0; i < n; i++) {
            int c = 0;
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (matrixCopy[i][j] == 1 && matrixCopy[j][k] == 1) {
                        String p2 = (i + 1) + " -> " + (j + 1) + " -> " + (k + 1);
                        JLabel paths2Label = new JLabel(p2);
                        paths2Label.setBounds(300 + 130 * i, 150 + 20 * c, d.width, 20);
                        paths2Label.setFont(this.FONT);
                        panel.add(paths2Label);
                        c++;
                    }
                }
            }
            if (c > cMax) cMax = c;
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + paths2[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(50, 150 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            panel.add(matrixLabel);
        }
        panel.setPreferredSize(new Dimension(1920, 170 + 20 * cMax));
    }
    public void drawPaths3() {
        int n = matrix.length;
        int cMax = 0;
        for (int i = 0; i < n; i++) {
            int c = 0;
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int m = 0; m < n; m++) {
                        if (matrixCopy[i][j] == 1 && matrixCopy[j][k] == 1 && matrixCopy[k][m] == 1) {
                            String p3 = (i + 1) + " -> " + (j + 1) + " -> " + (k + 1) + " -> " + (m + 1);
                            JLabel paths3Label = new JLabel(p3);
                            paths3Label.setBounds(300 + 130 * i, 150 + 20 * c, d.width, 20);
                            paths3Label.setFont(this.FONT);
                            panel.add(paths3Label);
                            c++;

                        }
                    }
                }
            }
            if (c > cMax) cMax = c;
        }
        for (int i = 0; i < n; i++) {
            String mat = "";
            for (int j = 0; j < n; j++) {
                mat = mat.concat("  " + paths3[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(50, 150 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            panel.add(matrixLabel);
        }
        panel.setPreferredSize(new Dimension(1920, 170 + 20 * cMax));
    }
    public void redraw() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        this.init();
    }
    public void changePathsLength() {
        if (this.pathsLength == 2) this.pathsLength = 3;
        else this.pathsLength = 2;
    }
}