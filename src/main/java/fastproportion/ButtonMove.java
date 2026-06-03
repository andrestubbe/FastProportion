package fastproportion;

import java.awt.*;

public class ButtonMove extends Button {

    public static final int SIZE = 22;

    public ButtonMove() {
        super(SIZE);
    }

    public boolean hit(int mx, int my, Proportion p) {
        this.x = p.x;
        this.y = p.y;
        return super.hit(mx, my);
    }

    @Override
    public void paint(Graphics2D g2, Proportion p) {
        this.x = p.x;
        this.y = p.y;

        g2.setColor(new Color(70, 70, 70));
        g2.fillRect(x, y, size, size);

        g2.setColor(Color.WHITE);
        int cx = x + size / 2;
        int cy = y + size / 2;
        g2.drawLine(cx - 4, cy, cx + 4, cy);
        g2.drawLine(cx, cy - 4, cx, cy + 4);
    }
}

