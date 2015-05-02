package com.hackvg.domain;

import dagger.Module;
import dagger.Provides;

@Module
public class DummyModule {

    @Provides DummyClass provideDummy () {
        return new DummyClass();
    }
}
