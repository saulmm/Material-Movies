package com.hackvg.domain;

import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.model.entities.PopularShowsApiResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface GetMoviesUsecase extends Usecase {

    public static final int TV_SHOWS = 0;
    public static final int TV_MOVIES = 1;

    public void getPopularShows();

    public void getPopularMovies ();

    public void onPopularShowsReceived (PopularShowsApiResponse response);

    public void onPopularMoviesReceived(PopularMoviesApiResponse response);

    public void sendShowsToPresenter ();
}
