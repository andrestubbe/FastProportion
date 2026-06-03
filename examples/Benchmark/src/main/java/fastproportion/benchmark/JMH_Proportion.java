package fastproportion.benchmark;

import fastproportion.Proportion;
import fastproportion.ProportionMode;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
public class JMH_Proportion {

    private Proportion proportion;

    @Setup(Level.Iteration)
    public void setup() {
        // Create it once per thread to avoid allocation overhead during measurement
        proportion = new Proportion(500, 500, 1920, 1080);
    }

    @Benchmark
    public void computeContain(Blackhole bh) {
        float[] bounds = proportion.compute(ProportionMode.CONTAIN);
        bh.consume(bounds);
    }

    @Benchmark
    public void computeCover(Blackhole bh) {
        float[] bounds = proportion.compute(ProportionMode.COVER);
        bh.consume(bounds);
    }
}
