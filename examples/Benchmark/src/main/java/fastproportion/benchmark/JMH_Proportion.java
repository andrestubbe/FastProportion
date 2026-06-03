package fastproportion.benchmark;

import fastproportion.Proportion;
import fastproportion.ProportionMode;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Thread)
public class JMH_Proportion {

    private Proportion proportion;

    @Setup(Level.Iteration)
    public void setup() {
        // Create it once per thread to avoid allocation overhead during measurement
        proportion = new Proportion(500, 500, 1920, 1080);
    }

    @Benchmark
    public float[] computeContain() {
        // Measures the throughput of the math pipeline for CONTAIN mode
        return proportion.compute(ProportionMode.CONTAIN);
    }

    @Benchmark
    public float[] computeCover() {
        // Measures the throughput of the math pipeline for COVER mode
        return proportion.compute(ProportionMode.COVER);
    }
}
