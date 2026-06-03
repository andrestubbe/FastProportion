package fastproportion.demo;
import fastproportion.*;

import fastui.component.Component;
import java.awt.*;

public class ResizeHandle extends Component {
    private final Proportion p;

    public ResizeHandle(Proportion p) {
        this.p = p;
        this.width = 18;
        this.height = 18;
    }

    public void syncBounds() {
        this.setBounds(p.x + p.width - this.width, p.y + p.height - this.height, this.width, this.height);
    }

    @Override
    public void onRender(Graphics2D g) {
        syncBounds();
        g.setColor(new Color(128, 128, 128, 150));
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }
}
