package DiscreteStructuresLab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private int[][] matrix;
    private boolean directed;
    private Window window;

    public ButtonListener(int[][] matrix, boolean directed) {
        this.directed = directed;
        this.matrix = matrix;
    }
    public ButtonListener(Window window) { this.window = window; }
    public void actionPerformed(ActionEvent e) {
        if ("Change Orientation".equals(e.getActionCommand())) {
            this.window.changeOrientation();
            this.window.redraw();
        }
        if ("Show Degrees Window".equals(e.getActionCommand())) {
            DegreesWindow window = new DegreesWindow(matrix, directed);
            window.setVisible(true);
        }
    }
}