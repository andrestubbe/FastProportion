# FastProportion v0.1.0 [ALPHA] — Aspect-Ratio Scaling for Java

[![Status](https://img.shields.io/badge/status-v0.1.0-brightgreen.svg)](https://github.com/andrestubbe/fastproportion/releases/tag/v0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Cross%20Platform-lightgrey.svg)]()
[![JitPack](https://jitpack.io/v/andrestubbe/fastproportion.svg)](https://jitpack.io/#andrestubbe/fastproportion)

---

**⚡ A tiny, zero-dependency aspect-ratio scaling utility for Java.**

**FastProportion** is a lightweight math library for pixel-accurate layout calculations. It computes contain, cover, fit-horizontal, and fit-vertical scaling modes, returning the resulting viewport coordinates as a `float[]`. Designed as the mathematical foundation for responsive FastJava UIs.

---

## Table of Contents

- [Why FastProportion?](#why-fastproportion)
- [Quick Start](#quick-start)
- [Features](#features)
- [API Quick Reference](#api-quick-reference)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastProportion?

Standard Java layout approaches — `GridBagLayout`, manual `Math.min`/`Math.max` scaling inline in `paintComponent`, or ad-hoc OOP abstractions — tend to tangle the scaling math with the rendering code. This makes animations and mode transitions brittle and hard to test in isolation.

**FastProportion** separates the math cleanly from the UI:

- **Pure float pipeline**: All calculations use 32-bit floats from input to output, keeping the internal math clean before the final draw cast.
- **Minimal and focused**: The library does one thing — compute scaled bounding boxes — with no dependencies and no hidden state.
- **Easy to interpolate**: Because `compute()` returns plain `float[]` values, animating between two modes is a straightforward lerp with no additional abstraction required.

> **Note:** `compute()` allocates a small `float[4]` array on each call. This is negligible for typical UI use, but if you are calling it in a tight inner loop at very high frequency, caching the result is recommended.

---

## Quick Start

```java
import fastproportion.Proportion;
import fastproportion.ProportionMode;

public class Example {
    public static void main(String[] args) {
        // Container: 500×500, Content: 1920×1080
        Proportion p = new Proportion(500, 500, 1920, 1080);

        // Calculate the bounding box for CONTAIN mode
        float[] bounds = p.compute(ProportionMode.CONTAIN);

        float x = bounds[0];
        float y = bounds[1];
        float w = bounds[2];
        float h = bounds[3];

        System.out.printf("Draw at: x=%.1f, y=%.1f, w=%.1f, h=%.1f%n", x, y, w, h);
    }
}
```

---

## Features

- **Accurate scaling**: Four standard modes — contain, cover, fit-horizontal, fit-vertical — computed with a strictly 32-bit float pipeline.
- **Seamless transitions**: Return values are plain floats, easy to lerp for animated mode switches.
- **Small and focused**: No dependencies, no reflection, no configuration.
- **Framework-agnostic**: Works with Java2D, OpenGL, or any custom rendering pipeline.

---

## Performance

**FastProportion** computes layouts incredibly fast thanks to its pure float pipeline and lack of object allocations in the hotpath beyond the single array return.

### JMH Benchmark Results

*Measured on Windows, JDK 25.0.1. Benchmark measures throughput (operations per millisecond).*

| Mode | Score (ops/ms) | Ops per Second |
|---|---|---|
| `CONTAIN` | ~132,215 ops/ms | > 132 Million |
| `COVER` | ~107,472 ops/ms | > 107 Million |

To run the benchmarks locally, execute `run-benchmark.bat` in the root directory.

---

## API Quick Reference

| Method | Description |
|---|---|
| `new Proportion(w, h, cw, ch)` | Creates a scaling context with container dimensions (`w`, `h`) and content dimensions (`cw`, `ch`). Position defaults to (0, 0); set `p.x` and `p.y` before calling `compute()` if needed. |
| `compute(ProportionMode mode)` | Returns `float[] { scaledX, scaledY, scaledWidth, scaledHeight }` for the given mode. |

### ProportionMode values

| Mode | Behaviour |
|---|---|
| `CONTAIN` | Scale to fit entirely within the container, preserving aspect ratio. Letterboxed if needed. |
| `COVER` | Scale to fill the container entirely, preserving aspect ratio. Content may be clipped. |
| `FIT_HORIZONTAL` | Scale so the content width matches the container width exactly. |
| `FIT_VERTICAL` | Scale so the content height matches the container height exactly. |

---

## Installation

### Maven (via JitPack)

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>fastproportion</artifactId>
        <version>v0.1.0</version>
    </dependency>
</dependencies>
```

### Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastproportion:v0.1.0'
}
```

### Direct download

📦 **[fastproportion-v0.1.0.jar](https://github.com/andrestubbe/fastproportion/releases/download/v0.1.0/fastproportion-v0.1.0.jar)**

---

## Documentation

- **[PHILOSOPHY.md](docs/PHILOSOPHY.md)** — Design rationale and goals.
- **[ROADMAP.md](docs/ROADMAP.md)** — Planned features and milestones.
- **[GITHUB_SETUP.md](docs/GITHUB_SETUP.md)** — Contributor setup guide.

---

## Platform Support

| Platform | Status |
|---|---|
| Windows | ✅ Supported |
| Linux | ✅ Supported |
| macOS | ✅ Supported |

---

## License

MIT — see [LICENSE](LICENSE) for details.

---

## Related Projects

- [FastAnimation](https://github.com/andrestubbe/FastAnimation) — Timeline-based animation engine
- [FastTween](https://github.com/andrestubbe/FastTween) — Pool-based tweening
- [FastTheme](https://github.com/andrestubbe/FastTheme) — Native window styling
- [FastUI](https://github.com/andrestubbe/FastUI) — Java UI framework
- [FastCore](https://github.com/andrestubbe/FastCore) — JNI loader and utilities

---

*Part of the FastJava ecosystem.*
