package com.hackvg.domain;


import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.ReviewsWrapper;

/**
 * Representation of an use case to get the details of a specific film
 */
@SuppressWarnings("UnusedDeclaration")
public interface GetMovieDetailUsecase extends Usecase {

    /**
     * Request datasource the details of a
     * movie.
     *
     * @param movieId of the movie
     */
    public void requestMovieDetail (String movieId);

    /**
     * Request datasource the reviews written about that movie
     * @param movieId of the film
     */
    public void requestMovieReviews (String movieId);

    /**
     * Request datasource the images of the film submited to the API
     *
     * @param movieId the id of the film
     */
    public void requestMovieImages(String movieId);

    /**
     * Callback used to be notified when the MovieDetail has been
     * received
     *
     * @param response the response containing the details of the film
     */
    public void onMovieDetailResponse (MovieDetailResponse response);

    void onMovieReviewsResponse (ReviewsWrapper reviewsWrapper);

    void onMovieImagesResponse (MovieImageWrapper imageWrapper);

    /**
     * Sends the MovieDetailResponse thought the communication system
     * to be received by the presenter
     *
     * @param response the response containing the details of the film
     */
    public void sendDetailMovieToPresenter (MovieDetailResponse response);
}
