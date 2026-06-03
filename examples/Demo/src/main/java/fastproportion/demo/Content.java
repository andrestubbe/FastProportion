package fastproportion.demo;
import fastproportion.*;

import fastui.component.Component;
import java.awt.Color;
import java.awt.Graphics2D;

public class Content extends Component {

    @Override
    public void onRender(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        g.setColor(Color.BLACK);
        g.drawLine((int)x, (int)y, (int)(x + width), (int)(y + height));
        g.drawLine((int)(x + width), (int)y, (int)x, (int)(y + height));
    }
}
