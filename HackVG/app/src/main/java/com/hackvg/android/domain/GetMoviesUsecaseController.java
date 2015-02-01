package com.hackvg.android.domain;

import com.hackvg.android.model.MediaDataSource;
import com.hackvg.android.model.rest.RestMovieSource;
import com.hackvg.android.model.entities.PopularMoviesApiResponse;
import com.hackvg.android.model.entities.PopularShowsApiResponse;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.model.entities.TvShow;
import com.hackvg.android.utils.BusProvider;
import com.hackvg.android.presentation.presenter.PopularShowsPresenter;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public class GetMoviesUsecaseController implements GetPopularMediaUsecase {

    private final PopularShowsPresenter popularMediaPresenter;
    private final MediaDataSource dataSource;
    private final int mode;

    private List<TvShow> popularShows;
    private List<TvMovie> popularMovies
        ;

    public GetMoviesUsecaseController(PopularShowsPresenter popularMediaPresenter, int mode) {

        this.popularMediaPresenter = popularMediaPresenter;
        this.dataSource = RestMovieSource.getInstance();

        this.mode = mode;

        BusProvider.getBusInstance().register(this);
    }

    @Override
    public void getPopularShows() {

        dataSource.getShows();
    }

    @Override
    public void getPopularMovies() {

        dataSource.getMovies ();
    }

    @Subscribe
    @Override
    public void onPopularShowsReceived(PopularShowsApiResponse response) {

        this.popularShows = response.getResults();
    }

    @Subscribe
    @Override
    public void onPopularMoviesReceived(PopularMoviesApiResponse response) {

        this.popularMovies = response.getResults();
        sendShowsToPresenter();
    }

    @Override
    public void sendShowsToPresenter() {

        switch (mode) {

            case GetPopularMediaUsecase.TV_MOVIES:
                popularMediaPresenter.onPopularMoviesReceived(popularMovies);
                break;

            case GetPopularMediaUsecase.TV_SHOWS:
                break;
        }

        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        switch (mode) {

            case GetPopularMediaUsecase.TV_MOVIES:
                getPopularMovies();
                break;

            case GetPopularMediaUsecase.TV_SHOWS:
//                popularMediaPresenter.onPopularShowsReceived(popularShows);
                getPopularShows();
                break;
        }
    }
}
