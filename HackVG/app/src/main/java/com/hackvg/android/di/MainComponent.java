package com.hackvg.android.di;

import android.app.Activity;

import com.hackvg.android.views.activities.MoviesActivity;

import dagger.Component;

@PerActivity
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject (MoviesActivity moviesActivity);

    Activity activity(); // Expose the activity to sub-graphs.

}
