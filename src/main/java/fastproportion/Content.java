package fastproportion;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Content {
    public static void paint(Graphics2D g2, double drawX, double drawY, double drawW, double drawH) {
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(drawX, drawY, drawW, drawH));

        // BLACK X
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(new Line2D.Double(drawX, drawY, drawX + drawW, drawY + drawH));
        g2.draw(new Line2D.Double(drawX + drawW, drawY, drawX, drawY + drawH));
    }
}

