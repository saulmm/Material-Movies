package com.hackvg.android.di.modules;

import com.hackvg.model.MediaDataSource;
import com.hackvg.model.rest.RestDataSource;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DataModule {
    @Provides @Singleton
    RestDataSource provideRestMovieSource(Bus bus) {
        return new RestMovieSource(bus);
    }

    @Provides @Singleton
    MediaDataSource provideMediaDataSource(Bus bus) {
        return new RestMovieSource(bus);
    }
}
