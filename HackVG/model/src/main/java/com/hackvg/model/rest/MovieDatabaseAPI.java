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
import rx.Observable;


/**
 * Interface representing the MovieDatabaseAPI endpoints
 * used by retrofit
 */
public interface MovieDatabaseAPI {
    @GET("/3/movie/popular")
    Observable<MoviesWrapper> getPopularMovies(
        @Query("api_key") String apiKey);

    @GET("/3/movie/{id}")
    Observable<MovieDetail> getMovieDetail(
        @Path("id") String id,
        @Query("api_key") String apiKey
    );

    @GET("/3/movie/popular")
    Observable<MoviesWrapper> getPopularMoviesByPage(
        @Query("api_key") String apiKey,
        @Query("page") int page
    );

    @GET("/3/configuration")
    Observable<ConfigurationResponse> getConfiguration(
        @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/reviews")
    Observable<ReviewsWrapper> getReviews(
        @Path("id") String id,
        @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/images")
    Observable<ImagesWrapper> getImages(
        @Path("id") String movieId,
        @Query("api_key") String apiKey
    );
}
