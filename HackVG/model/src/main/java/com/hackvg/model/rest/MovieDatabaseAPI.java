package com.hackvg.model.rest;

import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.model.entities.PopularShowsApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface MovieDatabaseAPI {

    @GET("/tv/popular")
    void getPopularShows(
        @Query("api_key") String apiKey,
        Callback<PopularShowsApiResponse> callback);

    @GET("/movie/popular")
    void getPopularMovies(
        @Query("api_key") String apiKey,
        Callback<PopularMoviesApiResponse> callback);

    @GET("/movie/{id}")
    void getMovieDetail (
        @Query("api_key") String apiKey,
        @Path("id") String id,
        Callback<MovieDetailResponse> callback
    );

}
