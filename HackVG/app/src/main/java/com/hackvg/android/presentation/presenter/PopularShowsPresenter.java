package com.hackvg.android.presentation.presenter;

import com.hackvg.android.model.entities.TvMovie;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public interface PopularShowsPresenter {


    public void onCreate ();

    void onPopularMoviesReceived(List<TvMovie> popularMovies);
}
