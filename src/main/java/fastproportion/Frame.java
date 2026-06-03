package fastproportion;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Frame {
    private static final Color COLOR = new Color(32, 32, 32);

    public void paint(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(COLOR);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
    }
}

