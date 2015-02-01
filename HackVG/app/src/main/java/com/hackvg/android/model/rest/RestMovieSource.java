package com.hackvg.android.model.rest;

import android.util.Log;

import com.hackvg.android.model.MediaDataSource;
import com.hackvg.android.model.entities.ApiResponse;
import com.hackvg.android.model.entities.MovieDetailResponse;
import com.hackvg.android.model.entities.PopularMoviesApiResponse;
import com.hackvg.android.utils.BusProvider;
import com.hackvg.android.utils.Constants;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by saulmm on 31/01/15.
 */
public class RestMovieSource implements MediaDataSource {

    public static RestMovieSource INSTANCE;
    private final MovieDatabaseAPI moviesDBApi;

    private RestMovieSource() {

        RestAdapter parkappRest = new RestAdapter.Builder()
            .setEndpoint(Constants.MOVIE_DB_HOST)
            .build();

        moviesDBApi = parkappRest.create(MovieDatabaseAPI.class);

    }

    public static RestMovieSource getInstance() {

        if (INSTANCE == null)
            INSTANCE = new RestMovieSource();

        return INSTANCE;
    }

    @Override
    public void getShows() {

//        moviesDBApi.getPopularShows(Constants.API_KEY, moviesResponseCallback);
    }



    @Override
    public void getMovies() {

        moviesDBApi.getPopularMovies(Constants.API_KEY, moviesResponseCallback);
    }

    @Override
    public void getDetailMovie(String id) {

        moviesDBApi.getMovieDetail(Constants.API_KEY, id, new Callback<MovieDetailResponse>() {
            @Override
            public void success(MovieDetailResponse movieDetailResponse, Response response) {

                BusProvider.getBusInstance().post(movieDetailResponse);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("[ERROR]", "RestMovieSource 71  - "+error.getMessage());
            }
        });
    }

    Callback<ApiResponse> responseCallback = new Callback<ApiResponse>() {
        @Override
        public void success(ApiResponse response, Response response2) {

            if (response instanceof PopularMoviesApiResponse) {

                BusProvider.getBusInstance().post(
                    (PopularMoviesApiResponse) response);
            }
        }

        @Override
        public void failure(RetrofitError error) {

            Log.e("[ERROR]", "RestMovieSource, failure (89)- " +
                "Error: "+error.getMessage());
        }
    };


    Callback<PopularMoviesApiResponse> moviesResponseCallback = new Callback<PopularMoviesApiResponse>() {
        @Override
        public void success(PopularMoviesApiResponse popularMoviesResponse, Response response) {

            BusProvider.getBusInstance().post(popularMoviesResponse);
        }

        @Override
        public void failure(RetrofitError error) {

            Log.e("[ERROR]", "RestMovieSource 60 Error:  - "+error.getMessage());
        }
    };

}
