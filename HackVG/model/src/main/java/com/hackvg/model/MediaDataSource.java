package com.hackvg.model;

/**
 * Created by saulmm on 31/01/15.
 */
public interface MediaDataSource {

    public void getShows ();

    public void getMovies();

    public void getDetailMovie (String id);

    /**
     * Get the reviews for a particular movie id.
     *
     * @param id movie id
     */
    public void getReviews (String id);

    public void getConfiguration ();
}
