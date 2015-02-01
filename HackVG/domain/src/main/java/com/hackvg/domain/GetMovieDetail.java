package com.hackvg.domain;


import com.hackvg.model.entities.MovieDetailResponse;

/**
 * Created by saulmm on 31/01/15.
 */
public interface GetMovieDetail extends Usecase{

    public void requestMovieDetail (String id);

    public void sendResponseToPresenter (MovieDetailResponse response);
}
