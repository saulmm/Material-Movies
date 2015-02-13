package com.hackvg.android.mvp.views;

import com.hackvg.model.entities.TvMovie;

import java.util.List;

public interface MVPPopularMoviesView extends MVPView {

    void showMovies (List<TvMovie> movieList);

    void showLoading ();

    void hideLoading ();

    void showError (String error);

    void hideError ();
}
