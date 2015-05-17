package com.hackvg.android.di.modules;

import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    RestMovieSource provideDataSource(Bus bus) {
        return new RestMovieSource(bus);
    }

}
