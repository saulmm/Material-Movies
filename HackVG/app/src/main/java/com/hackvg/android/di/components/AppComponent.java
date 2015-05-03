package com.hackvg.android.di.components;


import com.hackvg.android.di.modules.ApplicationModule;
import com.hackvg.android.di.modules.DomainModule;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    DomainModule.class,
})

public interface AppComponent {
    
    Bus bus();
    RestMovieSource restMovieSource();
}
