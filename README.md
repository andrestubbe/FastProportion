# FastProportion — High-Performance Aspect-Ratio Scaling for Java

**A tiny, zero-dependency, allocation-free aspect-ratio scaling utility for Java.**

[![Build](https://img.shields.io/github/actions/workflow/status/andrestubbe/fastproportion/maven.yml?branch=main)](https://github.com/andrestubbe/fastproportion/actions)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Cross%20Platform-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![JitPack](https://jitpack.io/v/andrestubbe/fastproportion.svg)](https://jitpack.io/#andrestubbe/fastproportion)

<p align="center">
  <b>FastProportion computes contain, cover, fit horizontal and fit vertical layouts and returns pixel‑accurate viewport coordinates.</b>
</p>

```java
// Quick Start — Example
import fastproportion.Proportion;
import fastproportion.ProportionMode;

public class Demo {
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

## Table of Contents
- [Key Features](#key-features)
- [Performance](#performance)
- [Installation](#installation)
- [Technical Examples & Hero Demos](#technical-examples--hero-demos)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)

---

## Key Features
-   **🚀 Float Pipeline** — 100% pure float calculations. Zero slow double-to-int casts during layout rendering.
-   **⚡ Allocation-Free Hotpath** — `compute()` returns a tiny array and mutates zero global state, making it thread-safe and extremely fast.
-   **📦 Zero Dependencies** — Just requires Java 17+. No external libraries, no native JNI code.
-   **🧩 FastJava Ready** — Built to integrate seamlessly into custom Swing/Java2D high-performance rendering pipelines.

---

## 📊 Performance
Because `FastProportion` is entirely pure math and relies on `switch` statements without allocating heavy objects, it can compute millions of layouts per second. This makes it ideal for complex `Masonry` layouts, Video Editors, and real-time graphics where the viewport changes 60 to 144 times a second.

---

## 📥 Installation

FastProportion is available via JitPack. 

### Option 1: Maven (JitPack)
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

### Option 2: Gradle (JitPack)
Add this to your `build.gradle` file:
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:fastproportion:v0.1.0'
}
```

### Option 3: Direct Download
Download the latest pre-compiled JAR directly:
📦 [**fastproportion-v0.1.0.jar**](https://github.com/andrestubbe/fastproportion/releases)

---

## Technical Examples & Hero Demos
See the `examples/` directory for the interactive visual demonstration:

| Case | App | Description |
|------|--------------|-------------------------|
| Interactive Viewer | [Demo/Main.java](examples/Demo/src/main/java/fastproportion/demo/Main.java) | An interactive GUI showing animated, seamless transitions between `CONTAIN`, `COVER`, `FIT_HORIZONTAL`, and `FIT_VERTICAL`. |

To run the visual demo locally, execute:
```cmd
run-demo.bat
```

---

## Documentation
*   **[PHILOSOPHIE.md](docs/PHILOSOPHIE.md)**: The FastJava philosophy.
*   **[ROADMAP.md](docs/ROADMAP.md)**: Future development and milestones.
*   **[GITHUB_SETUP.md](docs/GITHUB_SETUP.md)**: Setup guide for contributors.

---

## Platform Support
| Platform | Status |
|----------|--------|
| Windows | ✅ Fully Supported |
| Linux | ✅ Fully Supported |
| macOS | ✅ Fully Supported |

---

## License
MIT License — See [LICENSE](LICENSE) file for details.

---

## Related Projects
- [FastCore](https://github.com/andrestubbe/FastCore) — Native Library Loader for Java
- [FastTheme](https://github.com/andrestubbe/FastTheme) — Dark Mode and Theming for FastJava UIs
- [FastUI](https://github.com/andrestubbe/FastUI) — High Performance Java UI Library
- [FastAnimation](https://github.com/andrestubbe/FastAnimation) — Java Animation Engine

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
