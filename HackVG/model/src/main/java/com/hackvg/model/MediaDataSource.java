package com.hackvg.model;

/**
 * Created by saulmm on 31/01/15.
 */
public interface MediaDataSource {

    public void getShows ();

    public void getMovies();

    public void getDetailMovie (String id);

    public void getConfiguration ();
}
