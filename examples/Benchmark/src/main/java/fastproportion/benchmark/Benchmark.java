package fastproportion.benchmark;

import fastproportion.Proportion;
import fastproportion.ProportionMode;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class Benchmark {

    private Proportion proportion;
    private final float[] out = new float[4];

    @Setup(Level.Iteration)
    public void setup() {
        proportion = new Proportion(500, 500, 1920, 1080);
    }

    @org.openjdk.jmh.annotations.Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public float computeContainZeroAllocation() {
        proportion.compute(ProportionMode.CONTAIN, out);
        return out[0];
    }

    @org.openjdk.jmh.annotations.Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public float computeCoverZeroAllocation() {
        proportion.compute(ProportionMode.COVER, out);
        return out[0];
    }
}