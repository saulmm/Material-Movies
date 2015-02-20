package com.hackvg.domain;


import com.hackvg.model.entities.MovieDetailResponse;

/**
 * Representation of an use case to get the details of a specific film
 */
@SuppressWarnings("UnusedDeclaration")
public interface GetMovieDetailUsecase extends Usecase {

    /**
     * Request datasource the details of a
     * movie.
     *
     * @param id of the movie
     */
    public void requestMovieDetail (String id);

    /**
     * Callback used to be notified when the MovieDetail has been
     * received
     *
     * @param response the response containing the details of the film
     */
    public void onMovieDetailResponse (MovieDetailResponse response);

    /**
     * Sends the MovieDetailResponse throught the communication system
     * to be received by the presenter
     *
     * @param response the response containing the details of the film
     */
    public void sendDetailMovieToPresenter (MovieDetailResponse response);
}
