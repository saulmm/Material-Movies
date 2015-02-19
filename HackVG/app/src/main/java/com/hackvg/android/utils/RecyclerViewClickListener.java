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
     *
     * @param v The view within the RecyclerView.Adapter
     * @param position The position of the view in the adapter
     */
    public void onClick(View v, int position);
}