package com.hackvg.android.di.components;

import com.hackvg.android.di.modules.MovieUsecasesModule;
import com.hackvg.android.di.scopes.PerActivity;
import com.hackvg.android.views.activities.MovieDetailActivity;

import dagger.Component;

@PerActivity @Component(dependencies = AppComponent.class, modules = MovieUsecasesModule.class)
public interface MovieUsecasesComponent {

    void inject (MovieDetailActivity movieDetailActivity);
}
