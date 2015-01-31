package com.hackvg.android.domain;

import com.hackvg.android.model.MediaDataSource;
import com.hackvg.android.model.client.RestMovieSource;
import com.hackvg.android.model.entities.MovieDetailResponse;
import com.hackvg.android.utils.BusProvider;
import com.hackvg.android.view.presenter.MovieDetailPresenter;
import com.squareup.otto.Subscribe;

/**
 * Created by saulmm on 31/01/15.
 */
public class GetMovieDetailUsecaseController implements GetMovieDetail {

    private final MovieDetailPresenter presenter;
    private final String movieID;
    private final MediaDataSource movieDataSource;

    public GetMovieDetailUsecaseController(MovieDetailPresenter presenter, String movieID) {

        this.presenter = presenter;
        this.movieID = movieID;

        movieDataSource = RestMovieSource.getInstance();

        BusProvider.getBusInstance().register(this);
    }

    @Override
    public void requestMovieDetail(String id) {

        movieDataSource.getDetailMovie(id);

    }

    @Subscribe
    @Override
    public void sendResponseToPresenter(MovieDetailResponse response) {

        presenter.onDetailInformationReceived(response);
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        requestMovieDetail(movieID);
    }
}
