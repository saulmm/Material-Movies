package com.hackvg.model.rest;

import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.PopularMoviesApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Interface representing the MovieDatabaseAPI endpoints
 * used by retrofit
 */
public interface MovieDatabaseAPI {

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

    @GET("/movie/popular")
    void getPopularMoviesByPage(
        @Query("api_key") String apiKey,
        @Query("page") String page,
        Callback<PopularMoviesApiResponse> callback
    );

    @GET("/configuration")
    void getConfiguration (
        @Query("api_key") String apiKey,
        Callback<ConfigurationResponse> response
    );
}
