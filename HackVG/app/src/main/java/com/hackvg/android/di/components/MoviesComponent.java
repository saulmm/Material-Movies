package com.hackvg.android.di.components;

import com.hackvg.android.di.modules.BasicMoviesUsecasesModule;
import com.hackvg.android.di.modules.MovieUsecasesModule;
import com.hackvg.android.di.scopes.PerActivity;
import com.hackvg.android.views.activities.MovieDetailActivity;
import com.hackvg.android.views.activities.MoviesActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {
    BasicMoviesUsecasesModule.class,
    MovieUsecasesModule.class,
})
public interface MoviesComponent {

    void inject (MoviesActivity moviesActivity);

    void inject (MovieDetailActivity movieDetailActivity);
}
