package com.hackvg.model.rest;


import com.hackvg.common.utils.BusProvider;
import com.hackvg.common.utils.Constants;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.entities.ReviewsWrapper;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by saulmm on 31/01/15.
 */
public class RestMovieSource implements RestDataSource {

    public static RestMovieSource INSTANCE;
    private final MovieDatabaseAPI moviesDBApi;

    private RestMovieSource() {

        RestAdapter movieAPIRest = new RestAdapter.Builder()
            .setEndpoint(Constants.MOVIE_DB_HOST)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
            .build();

        moviesDBApi = movieAPIRest.create(MovieDatabaseAPI.class);
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

        moviesDBApi.getPopularMovies(Constants.API_KEY, retrofitCallback);
    }

    @Override
    public void getDetailMovie(String id) {

        moviesDBApi.getMovieDetail(Constants.API_KEY, id,
            retrofitCallback);
    }

    @Override
    public void getReviews(String id) {

        moviesDBApi.getMovieDetail(Constants.API_KEY, id,
            retrofitCallback);
    }

    @Override
    public void getConfiguration() {

        moviesDBApi.getConfiguration(Constants.API_KEY, retrofitCallback);
    }

    public Callback retrofitCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if (o instanceof MovieDetailResponse) {

                MovieDetailResponse detailResponse = (MovieDetailResponse) o;
                BusProvider.getRestBusInstance().post(detailResponse);

            } else if (o instanceof MoviesWrapper) {

                MoviesWrapper moviesApiResponse = (MoviesWrapper) o;

                BusProvider.getRestBusInstance().post(
                    moviesApiResponse);

            } else if (o instanceof ConfigurationResponse) {

                ConfigurationResponse configurationResponse = (ConfigurationResponse) o;

                BusProvider.getRestBusInstance().post(
                    configurationResponse
                );

            } else if (o instanceof ReviewsWrapper) {

                ReviewsWrapper reviewsWrapper = (ReviewsWrapper) o;

                BusProvider.getRestBusInstance().post(
                    reviewsWrapper
                );
            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.printf("[DEBUG] RestMovieSource failure - " + error.getMessage());
        }
    };

    @Override
    public void getMoviesByPage(int page) {

        moviesDBApi.getPopularMoviesByPage(
            Constants.API_KEY,
            page + "",
            retrofitCallback
        );
    }
}
