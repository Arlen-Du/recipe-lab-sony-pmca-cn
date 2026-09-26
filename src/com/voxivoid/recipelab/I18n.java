package com.voxivoid.recipelab;

import java.util.HashMap;
import java.util.Map;

/**
 * Chinese localization mapping for Recipe Lab.
 *
 * Every string the UI shows passes through here on its way to the screen: this class translates it, and {@link Cn}
 * hands it the font bundled in assets/cn.ttf to be drawn with. The two layers are separable on purpose — an entry
 * that is missing falls back to the English original and never crashes (see the tests in test/I18nTest.java), while
 * a character the bundled font does not carry is a *build-time* problem: tools/check-font.js names the character and
 * the file, and the subset is regenerated. Wording is therefore free again; it is no longer chosen around which
 * glyphs the camera firmware happens to have (the comment at the top of docs/I18N.md has that history).
 *
 * What still constrains the wording:
 * - Chip values stay short (about three characters, see {@link #tChipValue}) so the parameter row does not shift.
 * - Creative Style and Picture Effect names follow Sony's own Chinese camera-menu wording, so the app reads like
 *   the camera menus for the same setting.
 * - Brand names are simplified; {@code Cine} stays in Latin deliberately (see {@link #has}).
 */
public final class I18n {
    private I18n() {}

    private static final Map<String, String> DICT = new HashMap<String, String>();

    static {
        // ---- Hint bar / key legend (2 characters: the bar has to fit six of them)
        DICT.put("browse", "浏览");
        DICT.put("pick", "写入");
        DICT.put("fav (hold)", "收藏(长按)");
        DICT.put("factory", "重置");
        DICT.put("hide", "隐藏");
        DICT.put("exit", "退出");
        DICT.put("edit", "修改");
        DICT.put("done", "完成");

        // ---- Browser (PickerView)
        DICT.put("BRAND", "品牌");
        DICT.put("Favourites", "收藏");
        DICT.put("recipes", "配方");
        DICT.put("close", "关闭");
        DICT.put("No favourites yet", "暂无收藏");
        DICT.put("Hold the centre button on a recipe to keep it here", "长按中央键加入收藏");

        // ---- Brand names (browser groups, Recipes.GROUPS) — keys must match GROUPS exactly.
        // Simplified like the brands themselves — the body was checked and 达 苏 尔 胶 draw normally, and the
        // bundled font covers them regardless. Cine is kept in Latin on purpose.
        DICT.put("Sony", "索尼");
        DICT.put("Fuji Sim", "富士");
        DICT.put("Fuji Film", "富士胶片");
        DICT.put("Kodak", "柯达");
        DICT.put("Cine", "Cine");
        DICT.put("Ricoh GR", "理光GR");
        DICT.put("Leica", "徕卡");
        DICT.put("Hasselblad", "哈苏");
        DICT.put("Canon / Nikon", "佳能/尼康");
        DICT.put("Pana / Olympus", "松下/奥林巴斯");
        DICT.put("Other Stocks", "其他");
        DICT.put("Ilford", "伊尔福");

        // ---- PromptView
        DICT.put("confirm", "确定");
        DICT.put("cancel", "取消");
        DICT.put("Accept", "确定");
        DICT.put("Cancel", "取消");
        DICT.put("quality slot not located yet — live view only", "画质槽位未知 — 仅限预览");

        // ---- Dev menu (MenuView / DevTools)
        DICT.put("DEV TOOLS", "开发菜单");
        DICT.put("move", "移动");


        DICT.put("select", "确定");
        DICT.put("Settings snapshot", "设置快照");
        DICT.put("Settings diff", "设置对比");
        DICT.put("Store the value of every settings id", "保存所有设置 ID 数值");
        DICT.put("Compare every settings id against the snapshot", "对比当前设置与快照");
        DICT.put("Test every slot a recipe writes for the read-only flag", "测试配方写入项是否只读");
        DICT.put("One JPEG per recipe, in table order — MENU stops the run", "自动连拍所有配方, 按MENU停止");
        DICT.put("Wait after applying a recipe before the shutter fires", "应用配方后的快门等待时间");
        DICT.put("No live preview — the sample run needs the camera", "无实时预览 — 需要相机");

        // ---- Common values
        DICT.put("auto", "自动");
        DICT.put("kelvin", "色温");
        DICT.put("off", "OFF");

        // ---- Creative Styles: Sony's own Chinese camera-menu wording, matching Recipes.STYLE_LABEL
        DICT.put("Standard", "标准");
        DICT.put("Vivid", "生动");
        DICT.put("Neutral", "中性");
        DICT.put("Portrait", "肖像");
        DICT.put("Landscape", "风景");
        DICT.put("B&W", "黑白");
        DICT.put("Clear", "清晰");
        DICT.put("Deep", "深色");
        DICT.put("Light", "明快");
        DICT.put("Sunset", "黄昏");
        DICT.put("Night", "夜景");
        DICT.put("Autumn", "红叶");
        DICT.put("Sepia", "棕褐色");

        // ---- Picture Effects (keys are Recipes.PE_LABEL, so the chips stay short)
        DICT.put("Toy", "玩具");
        DICT.put("Pop", "流行");
        DICT.put("Poster", "色调");
        DICT.put("Retro", "复古");
        DICT.put("High-key", "高亮");
        DICT.put("Part col", "局部");
        DICT.put("HC mono", "单色");
        DICT.put("Soft foc", "柔焦");
        DICT.put("HDR art", "HDR");
        DICT.put("Rich mono", "黑白");
        DICT.put("Miniature", "微缩");
        DICT.put("Illust", "插图");
        DICT.put("Watercol", "水彩");

        // ---- Effect sub-params (Compact)
        DICT.put("blue", "冷蓝");
        DICT.put("pink", "粉红");
        DICT.put("green", "绿");
        DICT.put("normal", "标准");
        DICT.put("cool", "冷调");
        DICT.put("warm", "暖调");
        DICT.put("magenta", "品红");
        DICT.put("red", "红");
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

        // ---- Common Toasts
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

    /**
     * Whether a key has an entry at all, even one whose text reads the same as its key — a brand deliberately left in
     * Latin, like "Cine". {@link #t(String)} cannot tell that entry from a fallback, so a coverage test asks this.
     */
    public static boolean has(String text) { return text != null && DICT.containsKey(text); }

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
            return "只读检查 — " + original.substring("Read-only check — ".length()).replace(" slots", "项").replace("slots", "项");
        }
        if (row == DevTools.ROW_SAMPLES && original.startsWith("Shoot samples — ")) {
            return "样片拍摄 — " + original.substring("Shoot samples — ".length()).replace(" recipes", "款").replace("recipes", "款");
        }
        if (row == DevTools.ROW_SETTLE && original.startsWith("Settle delay — ")) {
            return "稳定延时 — " + original.substring("Settle delay — ".length());
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
            return "风格外观将使用出厂画质";
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
            return msg.replace("Quality: ", "画质: ").replace(" — ENTER to pick", " — 按中央键写入");
        }

        // "Snapshot of N settings taken..."
        if (msg.startsWith("Snapshot of ") && msg.contains("settings taken. Change a menu setting, reopen, press C1 again.")) {
            String count = msg.replace("Snapshot of ", "").replaceAll(" settings taken.*", "").trim();
            return "已保存 " + count + " 项设置快照。修改设置后重新打开按 C1。";
        }

        // "Read failed: ..."
        if (msg.startsWith("Read failed: ")) {
            return "读取设置失败: " + msg.substring("Read failed: ".length());
        }

        // "Not written — the camera holds this setting / these settings read-only..."
        if (msg.startsWith("Not written — the camera holds ")) {
            return msg.replace("Not written — the camera holds this setting read-only: ", "未写入 — 此项设置为只读: ")
                      .replace("Not written — the camera holds these settings read-only: ", "未写入 — 以下设置项为只读: ")
                      .replace(". Unlock the settings store with OpenMemories-Tweak (Protection → Unlock protected settings), then pick the recipe again.",
                               "。请用 OpenMemories-Tweak 解除保护（Protection → Unlock protected settings）后再写入。");
        }

        // "Shooting X / Y · RECIPE — MENU stops"
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
     * Translates the HUD metadata line under recipe name concisely to prevent overflow.
     */
    public static String tMetaLine(String meta) {
        if (meta == null) return null;
        String res = meta;
        res = res.replace("Picture Effect ", "效果 ");
        res = res.replace(" (Creative Style ignored, JPEG only)", " (忽略风格, 仅JPEG)");
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
