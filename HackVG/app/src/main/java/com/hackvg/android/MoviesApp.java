/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.hackvg.android;

import android.app.Application;

import com.hackvg.android.di.AppComponent;
import com.hackvg.android.di.ApplicationModule;
import com.hackvg.android.di.DaggerAppComponent;

/**
 * Android Main Application
 */
public class MoviesApp extends Application {

    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {

        appComponent = DaggerAppComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();
    }

    public AppComponent getAppComponent() {

        return appComponent;
    }
}
