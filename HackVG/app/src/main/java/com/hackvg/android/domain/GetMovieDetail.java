package com.hackvg.android.domain;

import com.hackvg.android.model.entities.MovieDetailResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface GetMovieDetail extends Usecase{

    public void requestMovieDetail (String id);

    public void sendResponseToPresenter (MovieDetailResponse response);
}
