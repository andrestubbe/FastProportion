# The Philosophy of FastProportion

> [!IMPORTANT]
> **"Math is math. Rendering is rendering. Never mix the two."**

FastProportion is built on the principle that modern Java UI development requires a strict separation of pure mathematical computation from stateful UI hierarchies. When building high-performance, smooth, and animated user interfaces, the math behind the layouts should never be coupled to the components that render them.

## Core Tenets

### 1. Pure Mathematical Pipeline
All calculations must happen within a strict `float` pipeline. By avoiding `double` variables and objects like `Rectangle` or `Dimension`, FastProportion completely eliminates casting overhead and prevents floating-point inaccuracies from bleeding into the rendering engine. 

### 2. Zero-State Hotpaths
Layout calculations must be perfectly deterministic. `Proportion.compute(...)` takes inputs and returns outputs without ever modifying global state or hidden variables. This pure-function design makes it thread-safe, incredibly fast, and immune to nasty side-effects during complex animations.

### 3. Allocation Awareness
In UI rendering (especially at 144 FPS), the Garbage Collector is the enemy of smooth motion. While `FastProportion` does return a single `float[4]` array per computation for API convenience, its internal logic strictly avoids creating objects, `ArrayList`s, or temporary wrappers.

### 4. Framework Agnosticism
A scaling utility should not know what it is scaling. Whether you are resizing a `BufferedImage` in Java2D, a textured quad in OpenGL/Vulkan, or a component in `FastUI`, FastProportion simply gives you the coordinates. It deliberately avoids UI dependencies.

### 5. Blueprint Consistency
As part of the **FastJava** ecosystem, FastProportion adheres to a standardized architecture:
*   **Minimal API Surface**: Do one thing, and do it perfectly.
*   **Zero Dependencies**: Plug and play.
*   **Premium Quality**: Built as a robust primitive for high-performance systems and complex orchestrations like `FastAnimation`.

---
**⚡ FastProportion — A flawless mathematical foundation for the FastJava Ecosystem.**
