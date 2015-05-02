package com.hackvg.android.di;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.hackvg.android.MoviesApp;

public class BaseActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        return ((MoviesApp) getApplication()).getAppComponent();
    }
}
