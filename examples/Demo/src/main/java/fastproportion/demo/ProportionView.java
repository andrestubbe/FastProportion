package fastproportion.demo;

// Scene is in the same package
import fastui.component.Component;
import fastui.component.ClipContainer;
import java.awt.Graphics2D;

public class ProportionView extends Component {

    private final AnimatedLayout layout;
    private final Scene scene;
    
    private final Frame frame;
    private final ClipContainer clipContainer;
    private final Content content;

    public ProportionView(AnimatedLayout layout, Scene scene) {
        this.layout = layout;
        this.scene = scene;
        
        this.frame = new Frame();
        this.clipContainer = new ClipContainer();
        this.content = new Content();
        
        // Build the component tree
        this.add(this.frame);
        this.add(this.clipContainer);
        this.clipContainer.add(this.content);
    }

    @Override
    public void onRender(Graphics2D g) {
        // Sync bounds from layout data before rendering children
        this.layout.p.x = this.scene.x;
        this.layout.p.y = this.scene.y;

        // Sync button bounds (buttons live in Canvas root for now)
        this.layout.moveBtn.setBounds(this.layout.p.x - 6, this.layout.p.y - 6, 12, 12);
        this.layout.resizeBtn.setBounds(this.layout.p.x + this.layout.p.width - 6, this.layout.p.y + this.layout.p.height - 6, 12, 12);

        // Frame is relative to ProportionView (which is at 0,0 in root)
        this.frame.setBounds(layout.p.x, layout.p.y, layout.p.width, layout.p.height);
        
        // ClipContainer defines the clipping area (also relative to ProportionView)
        this.clipContainer.setBounds(layout.p.x, layout.p.y, layout.p.width, layout.p.height);
        
        // Content is a child of ClipContainer, so its coordinates must be RELATIVE to ClipContainer
        float relX = layout.animX - layout.p.x;
        float relY = layout.animY - layout.p.y;
        this.content.setBounds(relX, relY, layout.animW, layout.animH);
    }
}
