package com.hackvg.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.TextView;

import com.hackvg.android.R;

/**
 * Created by saulmm on 08/02/15.
 */
public class GUIUtils {

    public static void tintAndSetCompoundDrawable (Context context, @DrawableRes
    int drawableRes, int color, TextView textview) {

        Resources res = context.getResources();
        int padding = (int) res.getDimension(R.dimen.activity_horizontal_margin);

        Drawable drawable = res.getDrawable(drawableRes);
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,
            null, null, null);

        textview.setCompoundDrawablePadding(padding);



    }
}
