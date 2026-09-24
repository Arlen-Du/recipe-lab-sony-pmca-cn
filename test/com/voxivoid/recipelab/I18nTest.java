package com.voxivoid.recipelab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class I18nTest {

    @Test
    void hintBarTranslations() {
        assertEquals("浏览", I18n.t("browse"));
        assertEquals("应用", I18n.t("pick"));
        assertEquals("长按收藏", I18n.t("fav (hold)"));
        assertEquals("恢复默认", I18n.t("factory"));
        assertEquals("隐藏", I18n.t("hide"));
        assertEquals("退出", I18n.t("exit"));
        assertEquals("调节", I18n.t("edit"));
        assertEquals("完成", I18n.t("done"));
    }

    @Test
    void browserTranslations() {
        assertEquals("品牌分类", I18n.t("BRAND"));
        assertEquals("我的收藏", I18n.t("Favourites"));
        assertEquals("暂无收藏配方", I18n.t("No favourites yet"));
    }

    @Test
    void toastDynamicTranslations() {
        assertEquals("Velvia 已加入收藏", I18n.tToast("Velvia added to Favourites"));
        assertEquals("Velvia 已移出收藏", I18n.tToast("Velvia removed from Favourites"));
        assertEquals("已应用 — 写入 5 项参数，重启相机即可全局生效",
                I18n.tToast("Picked — 5 values written, power-cycle the camera to apply everywhere"));
        assertEquals("已应用 — 写入 1 项参数，重启相机即可全局生效",
                I18n.tToast("Picked — 1 value written, power-cycle the camera to apply everywhere"));
        assertEquals("Kodak Portra 400 预览中 — 按中央键确认应用",
                I18n.tToast("Kodak Portra 400 previewed — ENTER to pick"));
    }

    @Test
    void menuTranslations() {
        assertEquals("设置差异对比", I18n.tMenuLabel(DevTools.ROW_SNAPSHOT, "Settings diff"));
        assertEquals("创建设置快照", I18n.tMenuLabel(DevTools.ROW_SNAPSHOT, "Settings snapshot"));
        assertEquals("只读检查 — 26 个槽位", I18n.tMenuLabel(DevTools.ROW_LOCKS, "Read-only check — 26 slots"));
        assertEquals("样片连拍 — 77 个配方", I18n.tMenuLabel(DevTools.ROW_SAMPLES, "Shoot samples — 77 recipes"));
        assertEquals("稳定延迟 — 1.2 s", I18n.tMenuLabel(DevTools.ROW_SETTLE, "Settle delay — 1.2 s"));
    }

    @Test
    void fallbackReturnsOriginal() {
        assertEquals("unknown_string_test", I18n.t("unknown_string_test"));
    }
}
