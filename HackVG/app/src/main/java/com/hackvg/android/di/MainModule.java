package com.hackvg.android.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final Activity activity;

    public MainModule(Activity activity) {

        this.activity = activity;
    }

    @Provides Activity activity() { return activity; }
}
