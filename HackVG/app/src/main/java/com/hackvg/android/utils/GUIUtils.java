package com.hackvg.android.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.hackvg.android.R;

/**
 * Created by saulmm on 08/02/15.
 */
public class GUIUtils {

    public static final int DEFAULT_DELAY = 30;

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

    /**
     * Shows a view by scaling
     *
     * @param v the view to be scaled
     *
     * @return the ViewPropertyAnimation to manage the animation
     */
    public static ViewPropertyAnimator showViewByScale (View v) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(DEFAULT_DELAY)
            .scaleX(1).scaleY(1);

        return propertyAnimator;
    }

    public static void showViewByRevealEffect (View hidenView, View centerPointView, int height) {

        int cx = (centerPointView.getLeft() + centerPointView.getRight())   / 2;
        int cy = (centerPointView.getTop()  + centerPointView.getBottom())  / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(
            hidenView, cx, cy, 0, height);

        // make the view visible and start the animation
        hidenView.setVisibility(View.VISIBLE);
        anim.start();
    }
}
