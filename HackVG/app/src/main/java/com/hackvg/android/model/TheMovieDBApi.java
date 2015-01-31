package com.hackvg.android.model;

import com.hackvg.android.model.entities.MovieDetailResponse;
import com.hackvg.android.model.entities.PopularMoviesResponse;
import com.hackvg.android.model.entities.PopularShowsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface TheMovieDBApi {

    @GET("/tv/popular")
    void getPopularShows(
        @Query("api_key") String apiKey,
        Callback<PopularShowsResponse> callback);

    @GET("/movie/popular")
    void getPopularMovies(
        @Query("api_key") String apiKey,
        Callback<PopularMoviesResponse> callback);

    @GET("/movie/{id}")
    void getMovieDetail (
        @Query("api_key") String apiKey,
        @Path("id") String id,
        Callback<MovieDetailResponse> callback
    );

}
