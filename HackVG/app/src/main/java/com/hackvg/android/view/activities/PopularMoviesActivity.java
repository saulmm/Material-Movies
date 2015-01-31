package com.hackvg.android.view.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hackvg.android.R;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.view.adapters.MoviesAdapter;
import com.hackvg.android.view.mvp_views.PopularMoviesView;
import com.hackvg.android.view.presenter.PopularMediaPresenter;
import com.hackvg.android.view.presenter.PopularShowsPresenterImpl;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularMoviesActivity extends Activity implements PopularMoviesView {

    private PopularMediaPresenter popularMediaPresenter;
    private static final int COLUMNS = 2;

    @InjectView(R.id.recycler_popular_movies) RecyclerView popularMoviesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        popularMoviesRecycler.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        popularMoviesRecycler.setAdapter(new MoviesAdapter(null));

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

    @Override
    public void showMovies(List<TvMovie> movieList) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }
}
