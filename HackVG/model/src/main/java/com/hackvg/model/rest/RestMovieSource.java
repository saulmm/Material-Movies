package com.hackvg.model.rest;


import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ApiResponse;
import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.common.utils.Constants;

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

                BusProvider.getRestBusInstance().post(movieDetailResponse);
            }

            @Override
            public void failure(RetrofitError error) {

                System.out.println("[ERROR] " + error.getMessage());
            }
        });
    }

    Callback<ApiResponse> responseCallback = new Callback<ApiResponse>() {
        @Override
        public void success(ApiResponse response, Response response2) {

            if (response instanceof PopularMoviesApiResponse) {

                BusProvider.getRestBusInstance().post(
                    (PopularMoviesApiResponse) response);
            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.println("[ERROR] "+error.getMessage());
        }
    };


    Callback<PopularMoviesApiResponse> moviesResponseCallback = new Callback<PopularMoviesApiResponse>() {
        @Override
        public void success(PopularMoviesApiResponse popularMoviesResponse, Response response) {

            BusProvider.getRestBusInstance().post(popularMoviesResponse);
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.println("[ERROR] "+error.getMessage());
        }
    };

}
