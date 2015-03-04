package com.hackvg.android.mvp.views;

import com.hackvg.model.entities.TvMovie;

import java.util.List;

public interface MoviesView extends MVPView {

    void showMovies(List<TvMovie> movieList);

    void showLoading ();

    void hideLoading ();

    void showError (String error);

    void hideError ();

    void showLoadingLabel();

    void hideActionLabel ();

    boolean isTheListEmpty ();

    void appendMovies (List<TvMovie> movieList);
}
