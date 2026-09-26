package com.voxivoid.recipelab;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * The bundled CJK font. The camera firmware's system font has no Chinese glyphs, so a character it does not carry
 * is drawn as .notdef — the "口" box. {@link I18n} works around that by only ever spelling Chinese with glyphs the
 * firmware happens to have (traditional forms, two-character synonyms); this class removes the constraint at the
 * source instead: every text control and every Canvas text Paint is given a typeface that ships with the app, so
 * ordinary simplified Chinese renders and the wording is free again.
 *
 * <p>Both layers are meant to stay: {@code Cn} fixes the glyphs, {@link I18n} still translates the strings and
 * falls back to English for anything it does not know. See docs/I18N.md and NOTICE.md for the font's origin.
 */
final class Cn {
    private Cn() {}

    private static Typeface t;

    /** the bundled font, or the system default if it could not be loaded */
    static Typeface font(Context c) {
        if (t == null) {
            try {
                t = Typeface.createFromAsset(c.getAssets(), "cn.ttf");
            } catch (Throwable e) {
                t = null;
            }
            if (t == null && c != null) {
                try {
                    java.io.File f = new java.io.File(c.getCacheDir(), "cn.ttf");
                    if (!f.exists() || f.length() == 0) {
                        java.io.InputStream in = c.getAssets().open("cn.ttf");
                        java.io.FileOutputStream out = new java.io.FileOutputStream(f);
                        byte[] b = new byte[8192];
                        int n;
                        while ((n = in.read(b)) > 0) out.write(b, 0, n);
                        in.close();
                        out.close();
                    }
                    if (f.exists() && f.length() > 0) {
                        t = Typeface.createFromFile(f);
                    }
                } catch (Throwable e) {
                    t = null;
                }
            }
        }
        return t != null ? t : Typeface.DEFAULT;
    }

    /**
     * Gives a TextView the bundled font and keeps the weight it already asked for ({@code android:textStyle="bold"},
     * a {@code DEFAULT_BOLD} chip value), so the recipe name does not come out thinner than before.
     *
     * <p>Bold is applied as the framework's algorithmic bold rather than through
     * {@code setTypeface(tf, BOLD)}: the subset has no bold face, and the two-argument setter resolves the style
     * through the font manager, which can hand back a *system* face — exactly the font without Chinese glyphs this
     * class exists to avoid. {@code setTypeface(tf)} never touches the paint's fake-bold flag, so setting it
     * ourselves survives.
     */
    static void apply(Context c, TextView v) {
        if (v == null) return;
        Typeface f = font(c);
        if (f != Typeface.DEFAULT) {
            Typeface cur = v.getTypeface();
            boolean bold = cur != null && (cur.getStyle() & Typeface.BOLD) != 0;
            v.setTypeface(f);
            if (bold) v.getPaint().setFakeBoldText(true);
        }
    }

    /** the Canvas path: every Paint that draws text in a custom view. */
    static void apply(Context c, Paint... ps) {
        Typeface f = font(c);
        if (f != Typeface.DEFAULT) {
            for (Paint p : ps) if (p != null) p.setTypeface(f);
        }
    }
}
