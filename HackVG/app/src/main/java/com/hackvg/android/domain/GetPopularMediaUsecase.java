package com.hackvg.android.domain;

import com.hackvg.android.model.entities.PopularMoviesResponse;
import com.hackvg.android.model.entities.PopularShowsResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface GetPopularMediaUsecase extends Usecase {

    public static final int TV_SHOWS = 0;
    public static final int TV_MOVIES = 1;

    public void getPopularShows();

    public void getPopularMovies ();

    public void onPopularShowsReceived (PopularShowsResponse response);

    public void onPopularMoviesReceived(PopularMoviesResponse response);

    public void sendShowsToPresenter ();
}
