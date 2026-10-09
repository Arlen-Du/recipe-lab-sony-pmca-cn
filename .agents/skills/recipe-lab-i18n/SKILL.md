---
name: recipe-lab-i18n
description: Recipe Lab (Sony PMCA) i18n Chinese localization guidelines, custom recipe maintenance (5207), font subsetting workflow, and test verification.
---

# Recipe Lab 汉化与滤镜维护规范 (i18n & Recipe Maintenance Guide)

本指南记录了 Recipe Lab（索尼 PMCA 相机应用）的汉化本地化规范、滤镜名称处理规则、自定义胶片滤镜（5207）维护方法及字体子集化流程。

---

## 1. 汉化架构与原则

### 1.1 核心架构
- `src/com/voxivoid/recipelab/Lang.java`: 语言管理器（支持 `Auto` / `English` / `简体中文` / `繁體中文`）。
- `src/com/voxivoid/recipelab/TextEn.java`: 英文源文本表（所有词条的权威 Key 来源）。
- `src/com/voxivoid/recipelab/TextZhHans.java`: 简体中文文本表。
- `src/com/voxivoid/recipelab/TextZhHant.java`: 繁体中文文本表。
- `test/com/voxivoid/recipelab/LangTest.java`: 自动化测试，验证词条覆盖率、占位符一致性及字体字符覆盖。

### 1.2 滤镜名称不汉化规范
- **原则**：所有滤镜/配方（Recipe）名称**保持英文原始名称**（如 `Classic Chrome`, `Kodak Portra 400`, `Kodak Vision3 250D (5207)` 等），不进行中文翻译。
- **原因**：
  1. 摄影胶片与色彩配方在摄影圈均通用其原始型号与品牌名。
  2. 汉化滤镜名会导致屏幕上出现两行（中文字符主标题 + 小号英文原名），占用索尼微单 640×480 紧凑屏幕的垂直空间。
  3. 保持英文名称时，`Recipes.originalName(r)` 返回 `null`，界面只居中显示一行干净的滤镜名。
- **实现约束**：
  - `LangTest.java` 中的 `canonicalKeys()` **不得**包含 `recipe_*`。
  - `TextZhHans.java` 和 `TextZhHant.java` **不得**包含 `recipe_*` 词条。
  - `Recipes.displayName(r)` 在缺失 `recipe_*` 时自动回退至 canonical 英文名 `r.name`。

### 1.3 紧凑 UI 与索尼官方术语
索尼相机屏幕空间有限，参数面板横向并排 13 个参数项（Chip），汉化文本必须经过实机验证并保持精简：
- **参数行标签（Chip Labels）**：
  - 饱和度 → `饱和`
  - 对比度 → `对比`
  - 锐度 → `锐度`
  - 子选项 → `子项`
  - 白平衡 → `WB`
  - 琥珀-蓝 → `A-B`（防止 4 字撑破 Chip）
  - 绿-洋红 → `G-M`（防止 4 字撑破 Chip）
  - 曝光补偿 → `EV`（防止 4 字撑破 Chip）
  - DRO → `DRO`
  - 画质 → `画质`
- **创意风格（Creative Styles）**：采用索尼相机固件官方中文菜单命名：
  - Standard → `标准`
  - Vivid → `生动`（非“鲜艳”）
  - Neutral → `中性`
  - Portrait → `肖像`（非“人像”）
  - Clear → `清晰`（非“清澈”）
  - Deep → `深色`（非“浓郁”）
  - Light → `明快`（非“淡雅”）
  - Autumn → `红叶`（非“秋叶”）
- **操作与提示文案**：
  - pick → `写入`（将参数写入相机）
  - select → `确定`
  - edit → `修改`
  - change → `切换`
  - ENTER 提示 → 改为相机硬件对应的 `中央键`

---

## 2. 5207 胶片滤镜规范 (Kodak Vision3 250D)

### 2.1 配方定义
在 `src/com/voxivoid/recipelab/Recipes.java` 的 `KODAK` 分组中：
```java
new Recipe(KODAK, "Kodak Vision3 250D (5207)", NEUTRAL, -1, -2, -1, K, 5500, 1, 1, 0, 1, 4),
```
- **参数解析**：
  - 分组：`KODAK`
  - 风格：`NEUTRAL` (中性)
  - 调整：饱和 `-1`, 对比 `-2`, 锐度 `-1`
  - 白平衡：`K` (色温模式), `5500K`
  - 色温微调：`A1` (琥珀 +1), `G1` (绿色 +1)
  - 照片效果：`0` (关闭)
  - 曝光补偿：`+1` (+0.3 EV)
  - 动态范围：`4` (DRO Lv4)

### 2.2 配方数量同步（78 款）
添加 5207 滤镜后，内置配方总数从 77 增加为 78。必须同步更新以下测试与文档：
- `test/com/voxivoid/recipelab/RecipesTest.java`：
  - `assertEquals(78, Recipes.ALL.length)`
  - `position(Recipes.ALL.length - 1)` 断言为 `"78 / 78"`
  - Summary 测试：`assertEquals("Neutral  -1/-2  +0.3  DRO Lv4  5500K  A1  G1", recipe("Kodak Vision3 250D (5207)").summary());`
- `test/com/voxivoid/recipelab/DevToolsTest.java`：
  - `Shoot samples — 78 recipes` 及 78 相关的 frame 断言
- `test/com/voxivoid/recipelab/ParamsHudTest.java`：
  - HUD Mini Pill 位置断言更新为 `" / 78"`
- 文档：`README.md`, `docs/DEVELOPMENT.md`, `docs/FAQ.md`, `docs/SAMPLES.md` 中的滤镜数量同步更新为 78。

---

## 3. 字体子集化与验证工作流 (Font Subsetting & Build)

任何汉化文本的修改都会影响打包字体的字形集合。请遵循以下标准流程：

### 3.1 重新生成字体子集
```bash
python3 tools/subset-font.py
```
- 该脚本会扫描 `TextZhHans.java` 和 `TextZhHant.java` 所使用的所有字符，结合 Noto Sans CJK SC/TC 生成紧凑的静态 TrueType 字体文件（输出至 `assets/fonts/`）。
- 去除了滤镜名称中文翻译后，字库体积大幅缩减至 ~93 KB。

### 3.2 运行测试套件
```bash
./tools/test.sh
```
- 确保所有 307 项测试（包括 `LangTest` 的字符 cmap 覆盖检查和行高比例测试）全部通过。

### 3.3 构建 APK
```bash
./build.sh
```
- **Android SDK 自动探测**：`build.sh` 会自动检测环境变量及常见 SDK 路径（如 macOS 默认的 `~/Library/Android/sdk`、Linux 的 `~/Android/Sdk`）。
- **NDK r16b 与预编译库回退**：
  - 索尼相机 PMCA 平台采用 Android 2.3.7，依赖早期的 `armeabi` 架构。现代 NDK（r17+）已废弃 `armeabi`，且旧版 `android-ndk-r16b` 在 macOS (Apple Silicon / Catalina+) 上极难运行。
  - `build.sh` 支持自动回退：若未配置 `ANDROID_NDK`，将自动复用已编译好的 [jni/prebuilt/armeabi/librecipelab.so](file:///Users/arlen/CodeBuddy/recipe-lab-sony-pmca-cn/jni/prebuilt/armeabi/librecipelab.so)。
  - `aapt` 打包参数增加 `-0 ttf`，防止字体文件被压缩导致 Android 2.3 `AssetManager` 读取异常。

