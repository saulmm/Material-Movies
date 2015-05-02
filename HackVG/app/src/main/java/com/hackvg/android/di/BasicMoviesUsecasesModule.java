package com.hackvg.android.di;

import com.hackvg.domain.ConfigurationUsecase;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.domain.GetMoviesUsecase;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class BasicMoviesUsecasesModule {

    @Provides ConfigurationUsecase provideConfigurationUsecase (Bus bus, RestMovieSource moviesSource) {
        return new ConfigurationUsecaseController(moviesSource, bus);
    }

    @Provides GetMoviesUsecase provideMoviesUsecase (Bus bus, RestMovieSource movieSource) {
        return new GetMoviesUsecaseController(movieSource, bus);
    }
}
