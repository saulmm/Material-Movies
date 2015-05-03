package com.hackvg.domain;

import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.rest.RestDataSource;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetMoviesUsecase}
 */
public class GetMoviesUsecaseController implements GetMoviesUsecase {

    private final RestDataSource mDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;

    /**
     * Constructor of the class.
     *
     * @param uiBus The bus to communicate the domain module and the app module
     * @param dataSource The data source to retrieve the list of movies
     */
    @Inject
    public GetMoviesUsecaseController(RestDataSource dataSource, Bus uiBus) {

        mDataSource = dataSource;
        mUiBus = uiBus;

        mUiBus.register(this);
    }

    @Override
    public void requestPopularMovies() {

        mDataSource.getMoviesByPage(mCurrentPage);
    }

    @Override
    public void sendMoviesToPresenter (MoviesWrapper response) {

        mUiBus.post(response);
    }

    @Override
    public void unRegister() {

        mUiBus.unregister(this);
    }

    @Override
    public void execute() {

        requestPopularMovies();
        mCurrentPage++;
    }
}
