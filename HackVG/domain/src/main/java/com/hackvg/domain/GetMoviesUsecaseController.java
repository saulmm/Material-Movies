package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.model.entities.PopularShowsApiResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by saulmm on 31/01/15.
 */

public class GetMoviesUsecaseController implements GetMoviesUsecase, GetMoviesUsecase.MoviesCallback {

    private final MediaDataSource dataSource;
    private final int mode;
    private final Bus uiBus;

    private PopularMoviesApiResponse response;

    public GetMoviesUsecaseController(
        int mode, MediaDataSource dataSource, Bus uiBus) {

        if (mode == GetMoviesUsecase.TV_SHOWS)
            throw  new IllegalArgumentException("The Shows feature is not implemented yet");

        if (dataSource == null || uiBus == null)
            throw new IllegalArgumentException("MediaDataSource & the event bus cannot be null");

        this.dataSource = dataSource;
        this.mode = mode;
        this.uiBus = uiBus;

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

        // TODO
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
                uiBus.post(response);
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
