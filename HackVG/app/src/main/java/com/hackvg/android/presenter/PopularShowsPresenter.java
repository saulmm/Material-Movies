package com.hackvg.android.presenter;

import com.hackvg.model.entities.PopularMoviesApiResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface PopularShowsPresenter {

    public void onCreate ();

    public void onStop ();

    public void onPopularMoviesReceived(PopularMoviesApiResponse popularMovies);
}
