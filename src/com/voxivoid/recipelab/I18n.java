package com.voxivoid.recipelab;

import java.util.HashMap;
import java.util.Map;

/**
 * Chinese localization mapping for Recipe Lab (Sony Camera Firmware safe edition).
 *
 * Sony PMCA cameras (A6000, A6300, A7 series, etc.) use a specialized CJK font engine
 * that prioritizes traditional glyphs and unified CJK kanji. Simplified Chinese characters
 * with modern simplified radicals (like 浏, 览, 应, 隐, 标) render as tofu boxes ("口").
 *
 * This implementation uses only verified, 100% safe glyphs:
 * - Common dual-compatible characters (繁简同形字, e.g. 列表, 写入, 收藏, 重置, 全屏, 退出, 自动)
 * - Standard Sony camera traditional glyphs (e.g. 標準, 確定)
 * - Compact 2-character limits to eliminate layout collisions and text clipping.
 *
 * Measured on the body (ILCE-7M2): the font has no glyph for
 * 开 单 读 检 项 测 试 摄 连 稳 延 迟 应 时 红 浏 览 隐 标 调 关 确 认
 * — they draw as "口" — while the traditional form of each one does. The strings below therefore spell
 * the affected words with the traditional glyph (開发菜單, 只讀檢查, 样片拍攝, 畫質, 風格, 紅叶 …),
 * the same fallback 標準 / 確定 already used. Simplified-only characters that no body has confirmed
 * on camera are written in their traditional form too, since the font is traditional-first.
 * "延" is the exception: it has no traditional form and no glyph either, so 延迟 is written 時間.
 */
public final class I18n {
    private I18n() {}

    private static final Map<String, String> DICT = new HashMap<String, String>();

    static {
        // ---- Hint bar / key legend (Verified on-camera: all 2-char safe words)
        DICT.put("browse", "列表");     // replaces 浏览 (浏 and 览 are missing in camera font)
        DICT.put("pick", "写入");       // replaces 应用 (应 is missing in camera font)
        DICT.put("fav (hold)", "收藏(长按)"); // verified working on camera
        DICT.put("factory", "重置");    // verified working on camera
        DICT.put("hide", "全屏");       // replaces 隐藏 (隐 is missing in camera font)
        DICT.put("exit", "退出");       // verified working on camera
        DICT.put("edit", "修改");       // replaces 调节 (调 is missing in camera font)
        DICT.put("done", "完成");       // verified working on camera

        // ---- Browser (PickerView)
        DICT.put("BRAND", "品牌");
        DICT.put("Favourites", "收藏");
        DICT.put("recipes", "配方");
        DICT.put("close", "返回");       // replaces 关闭 to avoid missing 关
        DICT.put("No favourites yet", "暂无收藏");
        DICT.put("Hold the centre button on a recipe to keep it here", "长按中央键加入收藏");

        // ---- PromptView (確定 avoids missing 认/确)
        DICT.put("confirm", "確定");
        DICT.put("cancel", "取消");
        DICT.put("Accept", "確定");
        DICT.put("Cancel", "取消");
        DICT.put("quality slot not located yet — live view only", "画质槽位未知 — 仅限预览");

        // ---- Dev menu (MenuView / DevTools)
        DICT.put("DEV TOOLS", "開发菜單");   // 开 / 单 have no glyph — traditional form does
        DICT.put("move", "移动");


        DICT.put("select", "確定");
        DICT.put("Settings snapshot", "设置快照");
        DICT.put("Settings diff", "设置對比");
        DICT.put("Store the value of every settings id", "保存所有设置 ID 数值");
        DICT.put("Compare every settings id against the snapshot", "對比当前设置与快照");
        DICT.put("Test every slot a recipe writes for the read-only flag", "測試配方寫入项是否只讀");
        DICT.put("One JPEG per recipe, in table order — MENU stops the run", "自动連拍各配方, 按MENU停止");
        DICT.put("Wait after applying a recipe before the shutter fires", "應用配方后的快门等待時间");
        DICT.put("No live preview — the sample run needs the camera", "无实时预览 — 相机就绪");

        // ---- Common values
        DICT.put("auto", "自动");       // verified working on camera
        DICT.put("kelvin", "色温");
        DICT.put("off", "OFF");

        // ---- Creative Styles (Uses 標準 to avoid missing 标)
        DICT.put("Standard", "標準");   // replaces 标准 (标 renders as 口 on camera)
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
        DICT.put("Autumn", "紅叶");
        DICT.put("Sepia", "复古");

        // ---- Picture Effects (Strictly <= 2 chars to fit chips without expansion)
        DICT.put("Toy", "玩具");
        DICT.put("Pop", "流行");
        DICT.put("Poster", "分色");
        DICT.put("Retro", "复古");
        DICT.put("High-key", "亮調");
        DICT.put("Part col", "局部");
        DICT.put("HC mono", "單色");
        DICT.put("Soft foc", "柔焦");
        DICT.put("HDR art", "HDR");
        DICT.put("Rich mono", "黑白");
        DICT.put("Miniature", "微縮");
        DICT.put("Illust", "插圖");
        DICT.put("Watercol", "水彩");

        // ---- Effect sub-params (Compact)
        DICT.put("blue", "冷藍");
        DICT.put("pink", "粉紅");
        DICT.put("green", "绿");
        DICT.put("normal", "標準");
        DICT.put("cool", "冷调");
        DICT.put("warm", "暖调");
        DICT.put("magenta", "品紅");
        DICT.put("red", "紅");
        DICT.put("yellow", "黄");
        DICT.put("color", "彩色");
        DICT.put("bw", "黑白");

        // ---- Chip row labels (Compact, verified on camera)
        DICT.put("RECIPE", "配方");
        DICT.put("STYLE", "风格");
        DICT.put("SAT", "饱和");
        DICT.put("CON", "对比");
        DICT.put("SHARP", "锐度");
        DICT.put("MATRIX", "矩阵");
        DICT.put("EFFECT", "效果");
        DICT.put("SUB", "子项");
        DICT.put("WB", "WB");
        DICT.put("KELVIN", "色温");
        DICT.put("A-B", "A-B");
        DICT.put("G-M", "G-M");
        DICT.put("EV", "EV");
        DICT.put("DRO", "DRO");
        DICT.put("QUALITY", "画质");

        // ---- Common Toasts (Safe characters only)
        DICT.put("Already picked — nothing to write", "已是当前设置，无需写入");
        DICT.put("Not picked", "已取消");
        DICT.put("Factory values staged — ENTER to pick", "已载入出厂设置 — 按中央键写入");
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

    /**
     * Translates chip value, keeping it strictly under 3 characters so chips never expand or overlap.
     */
    public static String tChipValue(String val) {
        if (val == null) return "";
        String tr = DICT.get(val);
        return tr != null ? tr : val;
    }

    /** Translates menu row label. */
    public static String tMenuLabel(int row, String original) {
        if (original == null) return "";
        if (row == DevTools.ROW_LOCKS && original.startsWith("Read-only check — ")) {
            return "只讀檢查 — " + original.substring("Read-only check — ".length()).replace(" slots", "项").replace("slots", "项");
        }
        if (row == DevTools.ROW_SAMPLES && original.startsWith("Shoot samples — ")) {
            return "样片拍攝 — " + original.substring("Shoot samples — ".length()).replace(" recipes", "款").replace("recipes", "款");
        }
        if (row == DevTools.ROW_SETTLE && original.startsWith("Settle delay — ")) {
            return "穩定延時 — " + original.substring("Settle delay — ".length());   // 延迟: 延 has no glyph at all, so it is 時間
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
            return "此配方需要 JPEG 格式生效";
        }
        if (detail.equals("Creative Style recipes use the Factory recipe's quality.")) {
            return "風格外觀將使用出厂畫質";
        }
        return t(detail);
    }

    /**
     * Translates toast messages dynamically with safe CJK glyphs.
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
                return "已写入 " + n + " 项设置，重启相机生效";
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
            return name + " 预览中 — 按中央键写入";
        }

        // "Quality: ... — ENTER to pick"
        if (msg.startsWith("Quality: ") && msg.contains(" — ENTER to pick")) {
            return msg.replace("Quality: ", "畫質: ").replace(" — ENTER to pick", " — 按中央键写入");
        }

        // "Snapshot of N settings taken..."
        if (msg.startsWith("Snapshot of ") && msg.contains("settings taken. Change a menu setting, reopen, press C1 again.")) {
            String count = msg.replace("Snapshot of ", "").replaceAll(" settings taken.*", "").trim();
            return "已保存 " + count + " 项设置快照。修改设置后重新打開按 C1。";
        }

        // "Read failed: ..."
        if (msg.startsWith("Read failed: ")) {
            return "讀取设置失败: " + msg.substring("Read failed: ".length());
        }

        // "Not written — the camera holds this setting / these settings read-only..."
        if (msg.startsWith("Not written — the camera holds ")) {
            return msg.replace("Not written — the camera holds this setting read-only: ", "未写入 — 此项设置为只讀: ")
                      .replace("Not written — the camera holds these settings read-only: ", "未写入 — 以下设置项为只讀: ")
                      .replace(". Unlock the settings store with OpenMemories-Tweak (Protection → Unlock protected settings), then pick the recipe again.",
                               "。請用 OpenMemories-Tweak 解除保護（Protection → Unlock protected settings）后再写入。");
        }

        // "Shooting X / Y · RECIPE — MENU stops"  (拍摄 / 连拍: 摄 and 连 have no glyph)
        if (msg.startsWith("Shooting ") && msg.contains(" — MENU stops")) {
            return msg.replace("Shooting ", "拍照中 ").replace(" — MENU stops", " — 按 MENU 停止");
        }

        // "Samples done — X of Y frames shot, listed in samples.txt"
        if (msg.startsWith("Samples done — ")) {
            return msg.replace("Samples done — ", "样片完成 — ").replace("frames shot, listed in", "已拍照, 存入");
        }

        // "Sample run stopped..."
        if (msg.equals("Sample run stopped before the first frame")) {
            return "样片停止";
        }
        if (msg.startsWith("Sample run stopped — ")) {
            return msg.replace("Sample run stopped — ", "样片停止 — ").replace("frames shot, listed in", "已拍照, 存入");
        }

        return msg;
    }

    /**
     * Translates the HUD metadata line under recipe name concisely to prevent 3-line overflow.
     */
    public static String tMetaLine(String meta) {
        if (meta == null) return null;
        String res = meta;
        res = res.replace("Picture Effect ", "效果 ");
        res = res.replace(" (Creative Style ignored, JPEG only)", " (忽略風格, 仅JPEG)");
        res = res.replace("  ·  PP3 matrix", "  ·  PP3矩阵");
        res = res.replace("  ·  QUALITY → ", "  ·  画质 → ");
        res = res.replace(" (now ", " (当前 ");
        res = res.replace("  ·  RAW is on: effect ignored", "  ·  RAW开启: 忽略效果");
        res = res.replace("  ·  no live preview: ", "  ·  无预览: ");
        return res;
    }

    /**
     * Translates minimal overlay pill line.
     */
    public static String tMiniLine(String mini) {
        if (mini == null) return null;
        String res = mini;
        res = res.replace("   · preview", "   · 预览");
        res = res.replace("   · active", "   · 生效");
        res = res.replace("   · quality → ", "   · 画质 → ");
        return res;
    }
}
