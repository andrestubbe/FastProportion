# FastProportion Roadmap 🗺️

**Vision:** To provide a mathematically perfect, zero-dependency layout calculator that handles the scaling logic so the UI engine doesn't have to.

## 🟢 v0.1.0: Initial Release (Current)
- [x] **Core Math Engine**: Pure float pipeline for 4 core modes.
- [x] **Modes Supported**: `CONTAIN`, `COVER`, `FIT_HORIZONTAL`, `FIT_VERTICAL`.
- [x] **Zero State Mutations**: `compute()` acts as a pure function.
- [x] **JMH Benchmark Integration**: Proven throughput of 100M+ ops/sec.

## 🟡 v0.2.0: Alignment & Anchoring
- [ ] **Custom Anchors**: Support for alignment beyond absolute center (e.g., `TOP_LEFT`, `BOTTOM_RIGHT`, `CENTER_TOP`).
- [ ] **Float Array Caching**: Internal thread-local caching mechanism or object pool to truly eliminate the `float[4]` allocation on every call for extreme hotpaths.
- [ ] **Aspect Ratio Overrides**: Pass arbitrary aspect ratios without requiring explicit container dimensions.

## 🔴 v1.0.0: UI Framework Bindings
- [ ] **FastUI Binding**: A lightweight wrapper component for `FastUI` that automatically applies `FastProportion` math to image rendering.
- [ ] **AWT/Java2D Helpers**: Optional utility methods to directly convert the float array into a `java.awt.geom.Rectangle2D.Float`.

---
**Focus:** Doing one thing, and doing it perfectly. No scope creep.
