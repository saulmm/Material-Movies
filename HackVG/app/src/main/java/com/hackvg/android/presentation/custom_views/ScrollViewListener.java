package com.hackvg.android.presentation.custom_views;


import android.widget.ScrollView;

public interface ScrollViewListener {

    void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

}