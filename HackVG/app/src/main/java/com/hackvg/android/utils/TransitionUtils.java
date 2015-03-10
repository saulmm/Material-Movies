package com.hackvg.android.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Transition;

import com.hackvg.android.R;

/**
 * Created by saulmm on 10/03/15.
 */
public class TransitionUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition makeEnterTransition () {

        Explode explodeTransition = new Explode();
        return explodeTransition;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition makeSharedElementEnterTransition(Context context) {

        Transition changeBounds = new ChangeBounds();
        changeBounds.addTarget(R.id.item_movie_cover);
        return changeBounds;
    }
}
