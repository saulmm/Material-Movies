package com.hackvg.model.rest;

import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.entities.ReviewsWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Interface representing the MovieDatabaseAPI endpoints
 * used by retrofit
 */
public interface MovieDatabaseAPI {
    @GET("/movie/popular")
    Call<MoviesWrapper> getPopularMovies(
        @Query("api_key") String apiKey);

    @GET("/movie/{id}")
    Call<MovieDetail> getMovieDetail (
        @Query("api_key") String apiKey,
        @Path("id") String id
    );

    @GET("/movie/popular")
    Call<MoviesWrapper> getPopularMoviesByPage(
        @Query("api_key") String apiKey,
        @Query("page") String page
    );

    @GET("/configuration")
    Call<ConfigurationResponse> getConfiguration (
        @Query("api_key") String apiKey
    );

    @GET("/movie/{id}/reviews")
    Call<ReviewsWrapper> getReviews (
        @Query("api_key") String apiKey,
        @Path("id") String id
    );

    @GET("/movie/{id}/images")
    Call<ImagesWrapper> getImages (
        @Query("api_key") String apiKey,
        @Path("id") String movieId
    );
}
