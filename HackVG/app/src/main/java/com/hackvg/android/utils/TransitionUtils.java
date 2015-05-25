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
