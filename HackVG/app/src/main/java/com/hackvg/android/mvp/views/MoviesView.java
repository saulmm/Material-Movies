package com.hackvg.android.mvp.views;

import com.hackvg.model.entities.Movie;

import java.util.List;

public interface MoviesView extends MVPView {

    void showMovies(List<Movie> movieList);

    void showLoading ();

    void hideLoading ();

    void showLoadingLabel();

    void hideActionLabel ();

    boolean isTheListEmpty ();

    void appendMovies (List<Movie> movieList);
}
