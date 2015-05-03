package com.hackvg.android.di.modules;

import com.hackvg.domain.ConfigurationUsecase;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class UsecaseModule {

    @Provides
    ConfigurationUsecase provideConfigurationUsecase (Bus bus, RestMovieSource restMovieSource) {
        return new ConfigurationUsecaseController(restMovieSource, bus);
    }
}
