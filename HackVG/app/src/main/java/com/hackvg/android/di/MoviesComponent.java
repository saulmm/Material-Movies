package com.hackvg.android.di;

import com.hackvg.android.views.activities.MoviesActivity;

import dagger.Component;

@PerActivity @Component(dependencies = AppComponent.class, modules = {
    BasicMoviesUsecasesModule.class,
    MovieUsecasesModule.class,
})
public interface MoviesComponent {

    void inject (MoviesActivity moviesActivity);

//    void inject (MovieDetailActivity movieDetailActivity);

}
