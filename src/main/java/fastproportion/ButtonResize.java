package fastproportion;

import java.awt.*;

public class ButtonResize extends Button {

    public static final int SIZE = 18;

    public ButtonResize() {
        super(SIZE);
    }

    public boolean hit(int mx, int my, Proportion p) {
        this.x = p.x + p.width - size;
        this.y = p.y + p.height - size;
        return super.hit(mx, my);
    }

    @Override
    public void paint(Graphics2D g2, Proportion p) {
        this.x = p.x + p.width - size;
        this.y = p.y + p.height - size;

        g2.setColor(new Color(90, 90, 90));
        g2.fillOval(x, y, size, size);
    }
}

