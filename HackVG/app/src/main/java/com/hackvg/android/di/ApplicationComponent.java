package com.hackvg.android.di;

import android.content.Context;

import com.hackvg.android.MaterialMoviesApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MaterialMoviesApplication materialMoviesApplication);

    Context context();
}
