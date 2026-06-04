package fastproportion.demo;

import fastproportion.Proportion;

public class AnimatedLayout {
    public final Proportion p;
    public float animX;
    public float animY;
    public float animW;
    public float animH;

    public AnimatedLayout(Proportion p) {
        this.p = p;
    }

    public fastui.component.Image moveBtn;
    public fastui.component.Image resizeBtn;
}
