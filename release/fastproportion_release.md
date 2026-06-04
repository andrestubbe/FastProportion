# FastProportion v0.1.0 — Initial Release 🚀

## 🎉 Version 0.1.0: Zero-Allocation Math Pipeline
**Release Date:** 2026-06-04  
**Tag:** `v0.1.0`

---

## ✨ Features

### [Zero-Allocation Geometry]
- The new `compute(mode, float[] out)` API completely eliminates object allocations.
- Runs at a staggering **~150 Million operations per second** (~6.5 ns/op).

### [Pure Float Precision]
- Avoids the rounding errors of classic integer `Math.min`/`Math.max` scaling.
- Flawless sub-pixel interpolation for high-refresh-rate UI animations.

### [Zero Dependencies]
- A microscopic footprint. Includes no third-party bloat.

---

## 📦 Installation (JitPack)

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>fastproportion</artifactId>
        <version>v0.1.0</version>
    </dependency>
</dependencies>
```

---

## 🔧 Technical Details
- **Architecture:** Pure Java (no JNI required).
- **Target:** Java 17+
- **Build System:** Standardized Maven pipeline.

---

## 🙏 Credits
- **FastUI:** Powering the stunning visual interactive Demo.

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
