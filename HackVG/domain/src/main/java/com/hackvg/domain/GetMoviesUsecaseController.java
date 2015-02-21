package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * This class is an implementation of {@link GetMoviesUsecase}
 */
public class GetMoviesUsecaseController implements GetMoviesUsecase {

    private final MediaDataSource mDataSource;
    private final Bus mUiBus;

    /**
     * Constructor of the class.
     *
     * @param uiBus The bus to communicate the domain module and the app module
     * @param dataSource The data source to retrieve the list of movies
     */
    public GetMoviesUsecaseController(MediaDataSource dataSource, Bus uiBus) {

        if (dataSource == null)
            throw new IllegalArgumentException("MediaDataSource cannot be null");

        if (uiBus == null)
            throw new IllegalArgumentException("Bus cannot be null");

        mDataSource = dataSource;
        mUiBus = uiBus;

        BusProvider.getRestBusInstance().register(this);
    }


    @Override
    public void requestPopularMovies() {

        mDataSource.getMovies();
    }

    @Subscribe
    @Override
    public void onPopularMoviesReceived(PopularMoviesApiResponse response) {

        sendMoviesToPresenter(response);
    }

    @Override
    public void sendMoviesToPresenter (PopularMoviesApiResponse response) {

        mUiBus.post(response);

        BusProvider.getRestBusInstance().unregister(this);
    }

    @Override
    public void execute() {

        requestPopularMovies();
    }
}
