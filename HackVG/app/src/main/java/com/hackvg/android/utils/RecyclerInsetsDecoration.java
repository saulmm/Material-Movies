/*
 * Copyright (C) 2015 Saúl Molinero.
 *
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

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hackvg.android.R;

/**
 * ItemDecoration implementation that applies an inset margin
 * around each child of the RecyclerView. The inset value is controlled
 * by a dimension resource.
 *
 * by Dave Smith at: https://github.com/devunwired/recyclerview-playground
 */
public class RecyclerInsetsDecoration extends RecyclerView.ItemDecoration {

    private int mInsets;

    public RecyclerInsetsDecoration(Context context) {
        mInsets = context.getResources().getDimensionPixelSize(R.dimen.insets);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //We can supply forced insets for each item view here in the Rect
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}