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

import android.view.View;

/**
 * Interface definition for a callback to be invoked when an item in a
 * RecyclerView has been clicked.
 */
public interface RecyclerViewClickListener {

    /**
     * Callback method to be invoked when a item in a
     * RecyclerView is clicked
     *  @param v The view within the RecyclerView.Adapter
     * @param position The position of the view in the adapter
     * @param x
     * @param y
     */
    public void onClick(View v, int position, float x, float y);
}