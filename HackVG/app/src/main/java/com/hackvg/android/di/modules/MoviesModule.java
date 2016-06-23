package com.hackvg.android.di.modules;

import com.hackvg.android.mvp.views.MoviesView;

import dagger.Module;
import dagger.Provides;



@Module
public class MoviesModule {
    private final MoviesView mMoviesView;

    public MoviesModule(MoviesView moviesView) {
        mMoviesView = moviesView;
    }

    @Provides
    MoviesView providesMoviewView() { return mMoviesView; };
}
