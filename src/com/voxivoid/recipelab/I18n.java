package com.voxivoid.recipelab;

import java.util.HashMap;
import java.util.Map;

/**
 * Chinese localization mapping for Recipe Lab.
 * Pure Java, no android.* imports so it can be compiled and unit tested without the Android SDK.
 *
 * Designed for minimum intrusion:
 * Core business classes (Params, Recipes, Favourites) remain intact in English,
 * and translations are applied only at the UI boundary.
 */
public final class I18n {
    private I18n() {}

    private static final Map<String, String> DICT = new HashMap<String, String>();

    static {
        // ---- Hint bar / key legend
        DICT.put("browse", "浏览");
        DICT.put("pick", "应用");
        DICT.put("fav (hold)", "长按收藏");
        DICT.put("factory", "恢复默认");
        DICT.put("hide", "隐藏");
        DICT.put("exit", "退出");
        DICT.put("edit", "调节");
        DICT.put("done", "完成");

        // ---- Browser (PickerView)
        DICT.put("BRAND", "品牌分类");
        DICT.put("Favourites", "我的收藏");
        DICT.put("recipes", "配方");
        DICT.put("close", "关闭");
        DICT.put("No favourites yet", "暂无收藏配方");
        DICT.put("Hold the centre button on a recipe to keep it here", "在配方上长按中央键即可加入收藏");

        // ---- PromptView
        DICT.put("confirm", "确认");
        DICT.put("cancel", "取消");
        DICT.put("Accept", "确认应用");
        DICT.put("Cancel", "取消");
        DICT.put("quality slot not located yet — live view only", "画质存储槽位未知 — 仅限实时预览");

        // ---- Dev menu (MenuView / DevTools)
        DICT.put("DEV TOOLS", "开发者工具");
        DICT.put("move", "移动");
        DICT.put("select", "选择");
        DICT.put("Settings snapshot", "创建设置快照");
        DICT.put("Settings diff", "设置差异对比");
        DICT.put("Store the value of every settings id", "记录相机当前所有设置 ID 的数值");
        DICT.put("Compare every settings id against the snapshot", "将当前所有设置与快照进行对比");
        DICT.put("Test every slot a recipe writes for the read-only flag", "测试配方写入的每个槽位是否只读");
        DICT.put("One JPEG per recipe, in table order — MENU stops the run", "按顺序为每个配方拍摄一张 JPEG，按 MENU 停止");
        DICT.put("Wait after applying a recipe before the shutter fires", "应用配方后等待画面稳定再释放快门");
        DICT.put("No live preview — the sample run needs the camera", "无实时预览 — 样片拍摄需要相机就绪");

        // ---- Status badge
        DICT.put("PREVIEW", "预览中");
        DICT.put("ACTIVE", "已生效");

        // ---- Common values / tags
        DICT.put("auto", "自动");
        DICT.put("kelvin", "色温");
        DICT.put("off", "关");

        // ---- Creative Styles
        DICT.put("Standard", "标准");
        DICT.put("Vivid", "生动");
        DICT.put("Neutral", "中性");
        DICT.put("Portrait", "肖像");
        DICT.put("Landscape", "风景");
        DICT.put("B&W", "黑白");
        DICT.put("Clear", "清澈");
        DICT.put("Deep", "深邃");
        DICT.put("Light", "清淡");
        DICT.put("Sunset", "日落");
        DICT.put("Night", "夜景");
        DICT.put("Autumn", "红叶");
        DICT.put("Sepia", "复古");

        // ---- Picture Effects
        DICT.put("Toy", "玩具相机");
        DICT.put("Pop", "流行色彩");
        DICT.put("Poster", "分色");
        DICT.put("Retro", "复古照片");
        DICT.put("High-key", "柔光亮调");
        DICT.put("Part col", "局部色彩");
        DICT.put("HC mono", "强反差单色");
        DICT.put("Soft foc", "柔焦");
        DICT.put("HDR art", "HDR绘画");
        DICT.put("Rich mono", "丰富色调黑白");
        DICT.put("Miniature", "微缩景观");
        DICT.put("Illust", "插图");
        DICT.put("Watercol", "水彩画");

        // ---- Effect sub-params
        DICT.put("blue", "冷色/蓝");
        DICT.put("pink", "粉红");
        DICT.put("green", "绿");
        DICT.put("normal", "标准");
        DICT.put("cool", "冷调");
        DICT.put("warm", "暖调");
        DICT.put("magenta", "洋红");
        DICT.put("red", "红");
        DICT.put("yellow", "黄");
        DICT.put("color", "彩色");
        DICT.put("bw", "黑白");

        // ---- Chip row names
        DICT.put("RECIPE", "配方");
        DICT.put("STYLE", "风格");
        DICT.put("SAT", "饱和");
        DICT.put("CON", "对比");
        DICT.put("SHARP", "清晰");
        DICT.put("MATRIX", "矩阵");
        DICT.put("EFFECT", "效果");
        DICT.put("SUB", "子项");
        DICT.put("WB", "白平衡");
        DICT.put("KELVIN", "色温");
        DICT.put("A-B", "A-B");
        DICT.put("G-M", "G-M");
        DICT.put("EV", "曝光");
        DICT.put("DRO", "DRO");
        DICT.put("QUALITY", "画质");

        // ---- Common Toasts
        DICT.put("Already picked — nothing to write", "已是当前设置 — 无需写入");
        DICT.put("Not picked", "已取消应用");
        DICT.put("Factory values staged — ENTER to pick", "已载入出厂设置 — 按中央键应用");
    }

    /** Translates a single text string, returns original if no translation found. */
    public static String t(String text) {
        if (text == null) return null;
        String tr = DICT.get(text);
        return tr != null ? tr : text;
    }

    /** Translates an array of strings in place or returns a translated copy. */
    public static String[] t(String[] texts) {
        if (texts == null) return null;
        String[] res = new String[texts.length];
        for (int i = 0; i < texts.length; i++) {
            res[i] = t(texts[i]);
        }
        return res;
    }

    /** Translates row/chip header name. */
    public static String tRow(String rowName) {
        return t(rowName);
    }

    /** Translates menu row label and detail. */
    public static String tMenuLabel(int row, String original) {
        if (original == null) return "";
        if (row == DevTools.ROW_LOCKS && original.startsWith("Read-only check — ")) {
            return "只读检查 — " + original.substring("Read-only check — ".length()).replace("slots", "个槽位");
        }
        if (row == DevTools.ROW_SAMPLES && original.startsWith("Shoot samples — ")) {
            return "样片连拍 — " + original.substring("Shoot samples — ".length()).replace("recipes", "个配方");
        }
        if (row == DevTools.ROW_SETTLE && original.startsWith("Settle delay — ")) {
            return "稳定延迟 — " + original.substring("Settle delay — ".length());
        }
        return t(original);
    }

    /** Translates menu row detail text. */
    public static String tMenuDetail(String original) {
        return t(original);
    }

    /** Translates prompt text (RAW/JPEG explanation). */
    public static String tQualityPromptDetail(String detail) {
        if (detail == null) return null;
        if (detail.equals("JPEG is needed to apply this recipe.")) {
            return "应用此配方需要相机处于 JPEG 格式。";
        }
        if (detail.equals("Creative Style recipes use the Factory recipe's quality.")) {
            return "创意风格配方将使用出厂预设的图像质量。";
        }
        return t(detail);
    }

    /**
     * Translates toast messages dynamically.
     */
    public static String tToast(String msg) {
        if (msg == null) return null;
        String direct = DICT.get(msg);
        if (direct != null) return direct;

        // "Picked — N values written, power-cycle the camera to apply everywhere"
        if (msg.startsWith("Picked — ") && msg.contains("written, power-cycle the camera to apply everywhere")) {
            int start = "Picked — ".length();
            int end = msg.indexOf(" value");
            if (end > start) {
                String n = msg.substring(start, end).trim();
                return "已应用 — 写入 " + n + " 项参数，重启相机即可全局生效";
            }
        }

        // "NAME added to Favourites" / "NAME removed from Favourites"
        if (msg.endsWith(" added to Favourites")) {
            String name = msg.substring(0, msg.length() - " added to Favourites".length());
            return name + " 已加入收藏";
        }
        if (msg.endsWith(" removed from Favourites")) {
            String name = msg.substring(0, msg.length() - " removed from Favourites".length());
            return name + " 已移出收藏";
        }

        // "NAME previewed — ENTER to pick"
        if (msg.endsWith(" previewed — ENTER to pick")) {
            String name = msg.substring(0, msg.length() - " previewed — ENTER to pick".length());
            return name + " 预览中 — 按中央键确认应用";
        }

        // "Quality: ... — ENTER to pick"
        if (msg.startsWith("Quality: ") && msg.contains(" — ENTER to pick")) {
            return msg.replace("Quality: ", "图像质量: ").replace(" — ENTER to pick", " — 按中央键确认应用");
        }

        // "Snapshot of N settings taken..."
        if (msg.startsWith("Snapshot of ") && msg.contains("settings taken. Change a menu setting, reopen, press C1 again.")) {
            String count = msg.replace("Snapshot of ", "").replaceAll(" settings taken.*", "").trim();
            return "已捕获 " + count + " 项设置快照。请在相机菜单中修改设置后重新打开应用并按 C1。";
        }

        // "Read failed: ..."
        if (msg.startsWith("Read failed: ")) {
            return "读取设置失败: " + msg.substring("Read failed: ".length());
        }

        // "Not written — the camera holds this setting / these settings read-only..."
        if (msg.startsWith("Not written — the camera holds ")) {
            return msg.replace("Not written — the camera holds this setting read-only: ", "未写入 — 该项设置为只读: ")
                      .replace("Not written — the camera holds these settings read-only: ", "未写入 — 以下设置项为只读: ")
                      .replace(". Unlock the settings store with OpenMemories-Tweak (Protection → Unlock protected settings), then pick the recipe again.",
                               "。请使用 OpenMemories-Tweak 解除保护（Protection → Unlock protected settings），然后重新应用配方。");
        }

        // "Shooting X / Y · RECIPE — MENU stops"
        if (msg.startsWith("Shooting ") && msg.contains(" — MENU stops")) {
            return msg.replace("Shooting ", "正在拍摄 ").replace(" — MENU stops", " — 按 MENU 停止");
        }

        // "Samples done — X of Y frames shot, listed in samples.txt"
        if (msg.startsWith("Samples done — ")) {
            return msg.replace("Samples done — ", "样片连拍完成 — ").replace("frames shot, listed in", "张照片已拍摄，记录于");
        }

        // "Sample run stopped..."
        if (msg.equals("Sample run stopped before the first frame")) {
            return "连拍在首张照片前已停止";
        }
        if (msg.startsWith("Sample run stopped — ")) {
            return msg.replace("Sample run stopped — ", "连拍已停止 — ").replace("frames shot, listed in", "张照片已拍摄，记录于");
        }

        return msg;
    }

    /**
     * Translates the HUD metadata line under recipe name.
     */
    public static String tMetaLine(String meta) {
        if (meta == null) return null;
        String res = meta;
        res = res.replace("Picture Effect ", "照片效果 ");
        res = res.replace(" (Creative Style ignored, JPEG only)", "（忽略风格外观，仅限JPEG）");
        res = res.replace("  ·  PP3 matrix", "  ·  PP3色彩矩阵");
        res = res.replace("  ·  QUALITY → ", "  ·  画质 → ");
        res = res.replace(" (now ", "（当前 ");
        res = res.replace("  ·  RAW is on: effect ignored", "  ·  已开启RAW: 效果不生效");
        res = res.replace("  ·  no live preview: ", "  ·  无实时预览: ");
        return res;
    }

    /**
     * Translates minimal overlay pill line.
     */
    public static String tMiniLine(String mini) {
        if (mini == null) return null;
        String res = mini;
        res = res.replace("   · preview", "   · 预览");
        res = res.replace("   · active", "   · 生效中");
        res = res.replace("   · quality → ", "   · 画质 → ");
        return res;
    }
}
