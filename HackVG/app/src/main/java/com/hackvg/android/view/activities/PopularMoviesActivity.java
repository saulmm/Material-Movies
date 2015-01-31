package com.hackvg.android.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.hackvg.android.R;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.view.HackVGClickListener;
import com.hackvg.android.view.adapters.MoviesAdapter;
import com.hackvg.android.view.mvp_views.PopularMoviesView;
import com.hackvg.android.view.presenter.PopularMediaPresenter;
import com.hackvg.android.view.presenter.PopularShowsPresenterImpl;
import com.hackvg.android.view.utils.RecyclerInsetsDecoration;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularMoviesActivity extends Activity implements PopularMoviesView, HackVGClickListener {

    private PopularMediaPresenter popularMediaPresenter;
    private static final int COLUMNS = 2;

    @InjectView(R.id.recycler_popular_movies) RecyclerView popularMoviesRecycler;
    @InjectView(R.id.activity_main_progress) ProgressBar progress;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        popularMoviesRecycler.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        popularMoviesRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
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

        moviesAdapter = new MoviesAdapter(movieList);
        moviesAdapter.setHackVGClickListener(this);
        popularMoviesRecycler.setAdapter(moviesAdapter);
    }

    @Override
    public void showLoading() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void onClick(View v, int position) {

        Log.d("[DEBUG]", "PopularMoviesActivity onClick - Pressed: " + position);

        Intent i = new Intent (PopularMoviesActivity.this, MovieDetailActivity.class);
        String movieID = moviesAdapter.getMovieList().get(position).getId();
        i.putExtra("movie_id", movieID);

        startActivity(i);
    }
}
