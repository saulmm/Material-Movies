package com.hackvg.android.di.components;


import com.hackvg.android.di.ActivityScope;
import com.hackvg.android.di.modules.MoviesDetailModule;
import com.hackvg.android.views.activities.MovieDetailActivity;

import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,

    modules = {
        MoviesDetailModule.class
    }
)
public interface MoviesDetailComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
