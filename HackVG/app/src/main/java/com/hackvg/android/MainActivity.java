package com.hackvg.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.hackvg.android.view.mvp_views.PopularMoviesView;
import com.hackvg.android.view.presenter.PopularMediaPresenter;
import com.hackvg.android.view.presenter.PopularShowsPresenterImpl;


public class MainActivity extends Activity implements PopularMoviesView {

    private PopularMediaPresenter popularMediaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularMediaPresenter = new PopularShowsPresenterImpl(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        popularMediaPresenter.onResume();
    }

    @Override
    public Context getContext() {

        return this;
    }
}
