package com.hackvg.android.view.mvp_views;

import com.hackvg.android.model.entities.TvMovie;

import java.util.List;

public interface PopularMoviesView extends MovieView {

    void showMovies (List<TvMovie> movieList);

    void showLoading ();

    void hideLoading ();

    void showError (String error);

    void hideError ();

}
