package com.hackvg.android.view.mvp_views;

import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.view.MovieView;

import java.util.List;

public interface PopularMoviesView extends MovieView {

    void showMovies (List<TvMovie> movieList);

    void showLoading ();

    void hideLoading ();

    void showError (String error);

    void hideError ();

}
