package com.hackvg.android.di.components;

import com.hackvg.android.di.modules.BasicMoviesUsecasesModule;
import com.hackvg.android.di.scopes.PerActivity;
import com.hackvg.android.views.activities.MoviesActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = BasicMoviesUsecasesModule.class)
public interface BasicMoviesUsecasesComponent {

    void inject (MoviesActivity moviesActivity);
}
