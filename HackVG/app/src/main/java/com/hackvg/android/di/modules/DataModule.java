package com.hackvg.android.di.modules;

import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DataModule {
    @Provides @Singleton
    RestMovieSource provideRestMovieSource(Bus bus) {
        return new RestMovieSource(bus);
    }
}
