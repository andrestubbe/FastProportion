package fastproportion.demo;
import fastproportion.*;

import fastui.component.Component;
import java.awt.Color;
import java.awt.Graphics2D;

public class Frame extends Component {

    public static final Color COLOR = new Color(32, 32, 32);

    @Override
    public void onRender(Graphics2D g) {
        g.setColor(COLOR);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
}
