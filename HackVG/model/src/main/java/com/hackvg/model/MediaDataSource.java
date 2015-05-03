package com.hackvg.model;

public interface MediaDataSource {

    void getMovies();

    void getDetailMovie (String id);

    /**
     * Get the reviews for a particular movie id.
     *
     * @param id movie id
     */
    void getReviews (String id);

    void getConfiguration ();

    /**
     * Get a list of images represented by a MoviesWrapper
     * class
     *
     * @param movieId the movie id
     */
    void getImages (String movieId);
}
