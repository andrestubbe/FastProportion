package fastproportion.demo;
import fastproportion.*;

import fastui.component.Component;
import java.awt.*;

public class MoveHandle extends Component {
    private final Proportion p;

    public MoveHandle(Proportion p) {
        this.p = p;
        this.width = 22;
        this.height = 22;
    }

    public void syncBounds() {
        this.setBounds(p.x, p.y, this.width, this.height);
    }

    @Override
    public void onRender(Graphics2D g) {
        syncBounds();
        g.setColor(new Color(64, 64, 64, 200));
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        int cx = (int)x + (int)width / 2;
        int cy = (int)y + (int)height / 2;
        g.drawLine(cx - 4, cy, cx + 4, cy);
        g.drawLine(cx, cy - 4, cx, cy + 4);
    }
}
