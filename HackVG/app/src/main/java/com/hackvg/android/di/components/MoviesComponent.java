package com.hackvg.android.di.components;


import com.hackvg.android.di.ActivityScope;
import com.hackvg.android.di.modules.MoviesModule;
import com.hackvg.android.views.activities.MoviesActivity;

import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,

    modules = {
        MoviesModule.class
    }
)
public interface MoviesComponent {
    void inject(MoviesActivity moviesActivity);
}
