package com.hackvg.android.model;

import com.hackvg.android.model.entities.PopularSeriesResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by saulmm on 31/01/15.
 */
public interface TheMovieDBApi {

    @GET("/tv/popular")
    void getBookings(
        @Query("api_key") String apiKey,
        Callback<PopularSeriesResponse> callback);

}
