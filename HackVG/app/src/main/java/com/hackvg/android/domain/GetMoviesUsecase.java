package com.hackvg.android.domain;

import com.hackvg.android.model.entities.PopularShowsResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface GetMoviesUsecase extends Usecase {

    public void getPopularShows ();

    public void onPopularShowsReceived (PopularShowsResponse response);

    public void sendShowsToPresenter ();
}
