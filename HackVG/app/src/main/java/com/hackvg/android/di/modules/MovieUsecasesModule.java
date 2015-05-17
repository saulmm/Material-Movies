package com.hackvg.android.di.modules;

import com.hackvg.domain.GetMovieDetailUsecase;
import com.hackvg.domain.GetMovieDetailUsecaseController;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieUsecasesModule {

    private final String movieId;

    public MovieUsecasesModule(String movieId) {

        this.movieId = movieId;
    }

    @Provides
    GetMovieDetailUsecase provideGetMovieDetailUsecase(Bus bus, RestMovieSource movieSource) {
        return new GetMovieDetailUsecaseController(movieId, bus, movieSource);
    }
}
