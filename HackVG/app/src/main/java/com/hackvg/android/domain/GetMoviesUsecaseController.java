package com.hackvg.android.domain;

import android.util.Log;

import com.hackvg.android.model.MediaDataSource;
import com.hackvg.android.model.client.RestMovieSource;
import com.hackvg.android.model.entities.PopularShowsResponse;
import com.hackvg.android.model.entities.TvShow;
import com.hackvg.android.utils.BusProvider;
import com.hackvg.android.view.presenter.PopularShowsPresenter;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public class GetMoviesUsecaseController implements GetMoviesUsecase {

    private final PopularShowsPresenter popularShowsPresenter;
    private final MediaDataSource dataSource;
    private List<TvShow> popularShows;

    public GetMoviesUsecaseController(PopularShowsPresenter popularShowsPresenter) {

        this.popularShowsPresenter = popularShowsPresenter;
        this.dataSource = RestMovieSource.getInstance();

        BusProvider.getBusInstance().register(this);
    }

    @Override
    public void getPopularShows() {

        dataSource.getShows();
    }

    @Subscribe
    @Override
    public void onPopularShowsReceived(PopularShowsResponse response) {

        Log.d("[DEBUG]", "GetMoviesUsecaseController onPopularShowsReceived - Received " + response);

        this.popularShows = response.getTvShows();
    }

    @Override
    public void sendShowsToPresenter() {

        popularShowsPresenter.onPopularShowsReceived(popularShows);
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        getPopularShows();
    }
}
