package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle extends JComponent {
    public int x, y, diam;
    private int n;

    public Circle(int x, int y, int diam, Dimension d, int n) {
        this.x = x;
        this.y = y;
        this.diam = diam;
        this.n = n;
        this.setOpaque(false);
        this.setBounds(0, 0, d.width, d.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int x = this.x - (diam / 2);
        int y = this.y - (diam / 2);
        Shape circle = new Ellipse2D.Double(x, y, diam, diam);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(circle);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g2d.getFontMetrics();
        int xn = this.x - fm.stringWidth(String.valueOf(n)) / 2;
        int yn = this.y + fm.getAscent() - fm.getHeight() / 2;
        g2d.drawString(String.valueOf(n), xn, yn);
    }
}
