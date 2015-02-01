package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.model.entities.PopularShowsApiResponse;
import com.hackvg.model.entities.TvMovie;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public class GetMoviesUsecaseController implements GetMoviesUsecase {

    private final MediaDataSource dataSource;
    private final int mode;

    private PopularMoviesApiResponse response;

    public GetMoviesUsecaseController(int mode) {

        this.dataSource = RestMovieSource.getInstance();
        this.mode = mode;

        BusProvider.getRestBusInstance().register(this);
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

    }

    @Subscribe
    @Override
    public void onPopularMoviesReceived(PopularMoviesApiResponse response) {

        this.response = response;
        sendShowsToPresenter();
    }

    @Override
    public void sendShowsToPresenter() {

        switch (mode) {

            case GetMoviesUsecase.TV_MOVIES:
                BusProvider.getUIBusInstance().post(response);
                break;

            case GetMoviesUsecase.TV_SHOWS:
                break;
        }

        BusProvider.getRestBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        switch (mode) {

            case GetMoviesUsecase.TV_MOVIES:
                getPopularMovies();
                break;

            case GetMoviesUsecase.TV_SHOWS:
                getPopularShows();
                break;
        }
    }
}
