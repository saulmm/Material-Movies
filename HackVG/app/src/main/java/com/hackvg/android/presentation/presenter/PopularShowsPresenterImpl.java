package com.hackvg.android.presentation.presenter;

import android.os.Handler;
import android.util.Log;

import com.hackvg.android.domain.GetMoviesUsecaseController;
import com.hackvg.android.domain.GetPopularMediaUsecase;
import com.hackvg.android.domain.Usecase;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.presentation.mvp_views.PopularMoviesView;

import java.util.List;


public class PopularShowsPresenterImpl implements PopularShowsPresenter {

    private final PopularMoviesView popularMoviesView;

    public PopularShowsPresenterImpl(PopularMoviesView popularMoviesView) {

        this.popularMoviesView = popularMoviesView;
    }

    @Override
    public void onCreate() {
        popularMoviesView.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Usecase getPopularShows = new GetMoviesUsecaseController(PopularShowsPresenterImpl.this,
                    GetPopularMediaUsecase.TV_MOVIES);

                getPopularShows.execute();


            }

        }, 2000);


    }


    @Override
    public void onPopularMoviesReceived(List<TvMovie> popularMovies) {

        Log.d("[DEBUG]", "PopularShowsPresenterImpl onPopularMoviesReceived - movies: " + popularMovies);
        popularMoviesView.hideLoading();
        popularMoviesView.showMovies(popularMovies);
    }
}
