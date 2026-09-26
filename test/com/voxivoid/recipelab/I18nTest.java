package com.voxivoid.recipelab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class I18nTest {

    @Test
    void hintBarTranslations() {
        assertEquals("列表", I18n.t("browse"));
        assertEquals("写入", I18n.t("pick"));
        assertEquals("收藏(长按)", I18n.t("fav (hold)"));
        assertEquals("重置", I18n.t("factory"));
        assertEquals("全屏", I18n.t("hide"));
        assertEquals("退出", I18n.t("exit"));
        assertEquals("修改", I18n.t("edit"));
        assertEquals("完成", I18n.t("done"));
    }

    @Test
    void browserTranslations() {
        assertEquals("品牌", I18n.t("BRAND"));
        assertEquals("收藏", I18n.t("Favourites"));
        assertEquals("暂无收藏", I18n.t("No favourites yet"));
    }

    @Test
    void everyBrandHasATranslation() {
        for (String g : Recipes.GROUPS) assertTrue(I18n.has(g), g + " has no brand translation");
        assertEquals("索尼", I18n.t("Sony"));
        assertEquals("理光GR", I18n.t("Ricoh GR"));
        assertEquals("佳能/尼康", I18n.t("Canon / Nikon"));
        assertEquals("松下/奧林巴斯", I18n.t("Pana / Olympus"));
    }

    @Test
    void toastDynamicTranslations() {
        assertEquals("Velvia 已加入收藏", I18n.tToast("Velvia added to Favourites"));
        assertEquals("Velvia 已移出收藏", I18n.tToast("Velvia removed from Favourites"));
        assertEquals("已写入 5 項设置，重啟相機生效",
                I18n.tToast("Picked — 5 values written, power-cycle the camera to apply everywhere"));
        assertEquals("已写入 1 項设置，重啟相機生效",
                I18n.tToast("Picked — 1 value written, power-cycle the camera to apply everywhere"));
        assertEquals("Kodak Portra 400 預覽中 — 按中央键写入",
                I18n.tToast("Kodak Portra 400 previewed — ENTER to pick"));
    }

    @Test
    void menuTranslations() {
        assertEquals("设置對比", I18n.tMenuLabel(DevTools.ROW_SNAPSHOT, "Settings diff"));
        assertEquals("设置快照", I18n.tMenuLabel(DevTools.ROW_SNAPSHOT, "Settings snapshot"));
        assertEquals("只讀檢查 — 26項", I18n.tMenuLabel(DevTools.ROW_LOCKS, "Read-only check — 26 slots"));
        assertEquals("样片拍攝 — 77款", I18n.tMenuLabel(DevTools.ROW_SAMPLES, "Shoot samples — 77 recipes"));
        assertEquals("穩定延時 — 1.2 s", I18n.tMenuLabel(DevTools.ROW_SETTLE, "Settle delay — 1.2 s"));
    }

    @Test
    void fallbackReturnsOriginal() {
        assertEquals("unknown_string_test", I18n.t("unknown_string_test"));
    }

    @Test
    void chipValueCompact() {
        assertEquals("標準", I18n.tChipValue("Standard"));
        assertEquals("生动", I18n.tChipValue("Vivid"));
        assertEquals("玩具", I18n.tChipValue("Toy"));
        assertEquals("OFF", I18n.tChipValue("off"));
    }
}
