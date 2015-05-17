package com.hackvg.android.di.modules;

import android.content.Context;

import com.hackvg.android.MoviesApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final MoviesApp application;

    public ApplicationModule(MoviesApp application) {

        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    public static class MovieUsecasesModule {
    }
}
