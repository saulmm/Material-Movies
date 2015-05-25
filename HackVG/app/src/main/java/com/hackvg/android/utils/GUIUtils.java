/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackvg.android.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.hackvg.android.R;

import butterknife.ButterKnife;

import static android.animation.Animator.AnimatorListener;


public class GUIUtils {

    public static final int DEFAULT_DELAY           = 30;
    public static final int SCALE_DELAY             = 300;
    public static final float SCALE_START_ANCHOR    = 0.3f;

    public static void tintAndSetCompoundDrawable (Context context, @DrawableRes
        int drawableRes, int color, TextView textview) {

            Resources res = context.getResources();
            int padding = (int) res.getDimension(R.dimen.activity_horizontal_margin_half);

            Drawable drawable = res.getDrawable(drawableRes);
            drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,
                null, null, null);

            textview.setCompoundDrawablePadding(padding);
        }

    public static ViewPropertyAnimator showViewByScale (View v) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(DEFAULT_DELAY)
            .scaleX(1).scaleY(1);

        return propertyAnimator;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showViewByRevealEffect (View hidenView, View centerPointView, int height) {

        int cx = (centerPointView.getLeft() + centerPointView.getRight())   / 2;
        int cy = (centerPointView.getTop()  + centerPointView.getBottom())  / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(
            hidenView, cx, cy, 0, height);

        hidenView.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void makeTheStatusbarTranslucent (Activity activity) {

        Window w = activity.getWindow();
        w.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTheStatusbarNotTranslucent(Activity activity) {

        WindowManager.LayoutParams attrs = activity.getWindow()
            .getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static int getWindowWidth (Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public static final ButterKnife.Setter<TextView, Integer> setter = new ButterKnife.Setter<TextView, Integer>() {

        @Override
        public void set(TextView view, Integer value, int index) {

            view.setTextColor(value);
        }
    };

    public static void startScaleAnimationFromPivotY (int pivotX, int pivotY, final View v,
        final AnimatorListener animatorListener) {

        final AccelerateDecelerateInterpolator interpolator =
            new AccelerateDecelerateInterpolator();

        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);

        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                    .setInterpolator(interpolator)
                    .scaleY(1)
                    .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();

                return true;
            }
        });
    }


    public static void startScaleAnimationFromPivot (int pivotX, int pivotY, final View v,
        final AnimatorListener animatorListener) {

        final AccelerateDecelerateInterpolator interpolator =
            new AccelerateDecelerateInterpolator();

        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);

        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                    .setInterpolator(interpolator)
                    .scaleY(1).scaleX(1)
                    .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();

                return true;
            }
        });
    }

    /**
     * Shows a view by scaling
     *
     * @param v the view to be scaled
     *
     * @return the ViewPropertyAnimation to manage the animation
     */
    public static ViewPropertyAnimator showViewByScaleY (View v, AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
           .scaleY(1);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static ViewPropertyAnimator showViewByScale (View v, AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
            .scaleY(1).scaleX(1);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static ViewPropertyAnimator hideViewByScaleY (View v, AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
            .scaleY(0);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static void hideScaleAnimationFromPivot(View v, AnimatorListener animatorListener) {

        ViewPropertyAnimator viewPropertyAnimator = v.animate()
            .setInterpolator(new AccelerateDecelerateInterpolator())
            .scaleY(SCALE_START_ANCHOR)
            .setDuration(SCALE_DELAY);

        if (animatorListener != null)
            viewPropertyAnimator.setListener(animatorListener);

        viewPropertyAnimator.start();

    }
}
