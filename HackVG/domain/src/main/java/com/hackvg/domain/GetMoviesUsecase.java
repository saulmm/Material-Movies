package com.hackvg.domain;

import com.hackvg.model.entities.MoviesWrapper;

/**
 * Representation of an use case to get the most popular movies
 */
@SuppressWarnings("UnusedDeclaration")
public interface GetMoviesUsecase extends Usecase {


    /**
     * Request datasource the most popular movies
     */
    public void requestPopularMovies();

    /**
     * Sends the PopularMoviesApiResponse thought the communication system
     * to be received by the presenter in another module
     *
     * @param response the response containing a list with movies
     */
    public void sendMoviesToPresenter (MoviesWrapper response);

    public void unRegister ();
}
