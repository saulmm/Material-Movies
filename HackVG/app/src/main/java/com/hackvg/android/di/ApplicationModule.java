package com.hackvg.android.di;

import android.content.Context;

import com.hackvg.android.MaterialMoviesApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final MaterialMoviesApplication application;

    public ApplicationModule(MaterialMoviesApplication application) {

        this.application = application;
    }

    // Here is where the application singletons live
    @Provides @Singleton Context provideApplicationContext() {
        return this.application;
    }

}
