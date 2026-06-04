package fastproportion.demo;
import fastproportion.*;

import fastui.component.Component;
import java.awt.Color;
import java.awt.Graphics2D;

public class Content extends Component {

    @Override
    public void onRender(Graphics2D g) {
        int ax = (int)getAbsoluteX();
        int ay = (int)getAbsoluteY();
        g.setColor(Color.WHITE);
        g.fillRect(ax, ay, (int)width, (int)height);
        
        g.setColor(Color.BLACK);
        g.drawLine(ax, ay, ax + (int)width, ay + (int)height);
        g.drawLine(ax + (int)width, ay, ax, ay + (int)height);
    }
}
