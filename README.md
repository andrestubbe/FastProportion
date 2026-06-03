# FastProportion v0.1.0 [ALPHA] — High-Performance Aspect-Ratio Scaling for Java

[![Status](https://img.shields.io/badge/status-v0.1.0-brightgreen.svg)](https://github.com/andrestubbe/fastproportion/releases/tag/v0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Cross%20Platform-lightgrey.svg)]()
[![JitPack](https://jitpack.io/v/andrestubbe/fastproportion.svg)](https://jitpack.io/#andrestubbe/fastproportion)

---

**⚡ A tiny, zero-dependency, allocation-free aspect-ratio scaling utility for Java.**

**FastProportion** is a high-performance math library built for zero-latency layout calculations. It computes contain, cover, fit horizontal, and fit vertical layouts, returning pixel‑accurate viewport coordinates. It is designed to act as the mathematical foundation for responsive `FastJava` UIs.

---

## Table of Contents

- [Why FastProportion?](#why-fastproportion)
- [Quick Start](#quick-start)
- [Features](#features)
- [Performance Benchmarks](#performance-benchmarks)
- [API Quick Reference](#api-quick-reference)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastProportion?

Standard Java layout approaches (like `GridBagLayout`, manual `Math.max` scaling in `paintComponent`, or heavy OOP abstractions) suffer from architectural flaws when dealing with high-speed rendering:

- **Garbage Collection Pauses**: Creating new `Rectangle` or `Dimension` objects every frame during resizing or animation causes the GC to stall the UI thread.
- **Double-to-Int Overhead**: Mixing `double` calculations with `int` rendering coordinates introduces constant casting overhead.
- **State Fragility**: Hardcoding aspect-ratio math directly into UI components makes animations and transitions brittle and bug-prone.

**FastProportion** solves this by strictly separating the math from the UI:

- **Pure Float Pipeline**: 100% float calculations from input to output. Zero slow `double`-to-`int` casts during layout rendering.
- **Allocation-Free Hotpath**: The `compute()` method returns a lightweight array and mutates zero global state, rendering Garbage Collection completely irrelevant during motion.
- **Thread-Safe & Pure Math**: FastProportion only handles *proportions*, decoupling the heavy lifting from the UI thread.

---

## Quick Start

```java
import fastproportion.Proportion;
import fastproportion.ProportionMode;

public class Example {
    public static void main(String[] args) {
        // Container: 500x500, Content: 1920x1080
        Proportion p = new Proportion(500, 500, 1920, 1080);
        
        // Calculate the bounding box for "CONTAIN" mode
        float[] bounds = p.compute(ProportionMode.CONTAIN);
        
        float x = bounds[0];
        float y = bounds[1];
        float w = bounds[2];
        float h = bounds[3];
        
        System.out.printf("Draw image at: x=%.1f, y=%.1f, w=%.1f, h=%.1f%n", x, y, w, h);
    }
}
```

---

## Features

- **⚡ High-Precision Math**: Accurate scaling using a strictly 32-bit float pipeline.
- **📈 Seamless Transitions**: Easy to interpolate values for fluid layout animations.
- **📦 Zero GC Pressure**: Returns primitive arrays and avoids object instantiation in the hotpath.
- **🖇️ Ecosystem Ready**: Seamlessly integrates into any Java2D, OpenGL, or custom UI framework.

---

## Performance Benchmarks

Because **FastProportion** is entirely pure math and relies on simple switch statements without allocating heavy objects, it can compute millions of layouts per second. 

This makes it ideal for complex `Masonry` layouts, Video Editors, and real-time graphics where the viewport changes 60 to 144 times a second.

*(JMH Benchmarks coming soon)*

---

## API Quick Reference

| Method                   | Description                                                                            |
|--------------------------|----------------------------------------------------------------------------------------|
| `new Proportion(w, h, cw, ch)` | Initializes the scaling context with container and content dimensions.                 |
| `compute(ProportionMode)`| Returns a `float[]` array `[x, y, width, height]` scaled to the specified mode.        |

---

## Installation

### Option 1: Maven (Recommended)

Add the JitPack repository and the dependency to your `pom.xml`:

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

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastproportion:v0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)

Download the latest JAR directly to add it to your classpath:

1. 📦 **[fastproportion-v0.1.0.jar](https://github.com/andrestubbe/fastproportion/releases/download/v0.1.0/fastproportion-v0.1.0.jar)** (The Core Library)

---

## Documentation

* **[PHILOSOPHIE.md](docs/PHILOSOPHIE.md)**: Zero-allocation and low-overhead processing designs.
* **[ROADMAP.md](docs/ROADMAP.md)**: Planned milestone features and performance extensions.
* **[GITHUB_SETUP.md](docs/GITHUB_SETUP.md)**: Setup guide for contributors.

---

## Platform Support

| Platform      | Status            |
|---------------|-------------------|
| Windows       | ✅ Fully Supported |
| Linux         | ✅ Fully Supported |
| macOS         | ✅ Fully Supported |

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

## Related Projects

- [FastCore](https://github.com/andrestubbe/FastCore) — Native JNI Loader and Utilities
- [FastAnimation](https://github.com/andrestubbe/FastAnimation) — Zero overhead timeline orchestration
- [FastTween](https://github.com/andrestubbe/FastTween) — Zero overhead pool-based tweening
- [FastTheme](https://github.com/andrestubbe/FastTheme) — High-performance native window styling
- [FastUI](https://github.com/andrestubbe/FastUI) — High Performance Java UI Library

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
