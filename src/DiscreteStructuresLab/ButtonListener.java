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
        if ("Show Paths Window".equals(e.getActionCommand())) {
            PathsWindow window = new PathsWindow(matrix);
            window.setVisible(true);
        }
        if ("Show Reachability Window".equals(e.getActionCommand())) {
            ReachabilityWindow window = new ReachabilityWindow(matrix);
            window.setVisible(true);
        }
        if ("Show Connected Window".equals(e.getActionCommand())) {
            ConnectedWindow window = new ConnectedWindow(matrix);
            window.setVisible(true);
        }
        if ("Show Condensed Window".equals(e.getActionCommand())) {
            CondensedWindow window = new CondensedWindow(matrix);
            window.setVisible(true);
        }
    }
}