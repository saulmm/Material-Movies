package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;

/**
 * Created by saulmm on 31/01/15.
 */
public class GetMovieDetailUsecaseController implements GetMovieDetail {

    private final String movieID;
    private final MediaDataSource movieDataSource;

    public GetMovieDetailUsecaseController(String movieID) {

        this.movieID = movieID;

        movieDataSource = RestMovieSource.getInstance();

        BusProvider.getRestBusInstance().register(this);
    }

    @Override
    public void requestMovieDetail(String id) {

        movieDataSource.getDetailMovie(id);
    }

    @Subscribe
    @Override
    public void sendResponseToPresenter(MovieDetailResponse response) {

        BusProvider.getUIBusInstance().post(response);
        BusProvider.getRestBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        requestMovieDetail(movieID);
    }
}
