package fastproportion.demo;

import fastproportion.*;

import fastanimation.FastAnimation;
import fastanimation.AnimationEngine.HeartbeatMode;
import fasttween.FastTween;
import fasttween.Ease;

import java.util.List;

public class ModeSwitchAnimator {

    private final List<AnimatedLayout> layouts;
    private final Runnable repaintCallback;

    private final float[] tempFrom = new float[4];
    private final float[] tempTo = new float[4];

    public ModeSwitchAnimator(List<AnimatedLayout> layouts, Runnable repaintCallback) {
        this.layouts = layouts;
        this.repaintCallback = repaintCallback;

        FastAnimation.setHeartbeatMode(HeartbeatMode.JAVA);
    }

    public void init(ProportionMode mode) {
        updateProgress(1f, mode, mode);
    }

    public void animate(ProportionMode from, ProportionMode to) {
        updateProgress(0f, from, to);

        FastAnimation.parallel(
            FastTween.to(0f, 1f, 300)
                .ease(Ease.LINEAR)
                .onUpdate(t -> updateProgress(t, from, to))
        ).start();
    }

    private void updateProgress(float t, ProportionMode from, ProportionMode to) {
        for (AnimatedLayout layout : layouts) {
            // CURRENT
            layout.p.compute(from, tempFrom);
            float cx = tempFrom[0], cy = tempFrom[1], cw = tempFrom[2], ch = tempFrom[3];

            // TARGET
            layout.p.compute(to, tempTo);
            float tx = tempTo[0], ty = tempTo[1], tw = tempTo[2], th = tempTo[3];

            // LINEAR INTERPOLATION
            layout.animX = cx + (tx - cx) * t;
            layout.animY = cy + (ty - cy) * t;
            layout.animW = cw + (tw - cw) * t;
            layout.animH = ch + (th - ch) * t;
        }

        repaintCallback.run();
    }
}
