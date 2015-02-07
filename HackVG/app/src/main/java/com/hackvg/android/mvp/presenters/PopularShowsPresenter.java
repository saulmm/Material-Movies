package com.hackvg.android.mvp.presenters;

import com.hackvg.model.entities.PopularMoviesApiResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface PopularShowsPresenter {

    public void onStart ();

    public void onStop ();

    public void onPopularMoviesReceived(PopularMoviesApiResponse popularMovies);
}
