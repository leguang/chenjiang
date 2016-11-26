package com.shtoone.chenjiang.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Author：leguang on 2016/11/24 0024 20:07
 * Email：langmanleguang@qq.com
 */
public class DrawableUtils {

    /**
     * Return a tint drawable
     *
     * @param drawable
     * @param color
     * @param forceTint
     * @return
     */
    public static Drawable getTintDrawable(Drawable drawable, @ColorInt int color, boolean forceTint) {
        if (forceTint) {
            drawable.clearColorFilter();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawable.invalidateSelf();
            return drawable;
        }
        Drawable wrapDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrapDrawable, color);
        return wrapDrawable;
    }

}
