package com.hackvg.android.di;

import android.util.Log;

import com.hackvg.domain.ConfigurationUsecase;

import javax.inject.Inject;

public class BasePresenter {

    private final ConfigurationUsecase configurationUsecase;

    @Inject
    public BasePresenter (ConfigurationUsecase configurationUsecase) {

        this.configurationUsecase = configurationUsecase;

    }

    public void sayHi () {

        Log.d("[DEBUG]", "BasePresenter <unnamed> - " +
            "lol;");

    }
}
