package com.hackvg.model.rest;


import com.hackvg.common.utils.Constants;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.entities.ReviewsWrapper;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RestMovieSource {
    private final MovieDatabaseAPI moviesDBApi;
    private final Bus bus;

    public RestMovieSource(Bus bus) {
        RestAdapter movieAPIRest = new RestAdapter.Builder()
            .setEndpoint(Constants.MOVIE_DB_HOST)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
            .build();

        moviesDBApi = movieAPIRest.create(MovieDatabaseAPI.class);
        this.bus = bus;
    }

    public void getMovies() {
        moviesDBApi.getPopularMovies(Constants.API_KEY, retrofitCallback);
    }

    public void getDetailMovie(String id) {
        moviesDBApi.getMovieDetail(Constants.API_KEY, id,
            retrofitCallback);
    }

    public void getReviews(String id) {
        moviesDBApi.getReviews(Constants.API_KEY, id,
            retrofitCallback);
    }

    public void getConfiguration() {

        moviesDBApi.getConfiguration(Constants.API_KEY, retrofitCallback);
    }

    public void getImages(String movieId) {

        moviesDBApi.getImages(Constants.API_KEY, movieId,
            retrofitCallback);
    }

    public Callback retrofitCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if (o instanceof MovieDetail) {

                MovieDetail detailResponse = (MovieDetail) o;
                bus.post(detailResponse);

            } else if (o instanceof MoviesWrapper) {

                MoviesWrapper moviesApiResponse = (MoviesWrapper) o;
                bus.post(moviesApiResponse);

            } else if (o instanceof ConfigurationResponse) {

                ConfigurationResponse configurationResponse = (ConfigurationResponse) o;
                bus.post(configurationResponse);

            } else if (o instanceof ReviewsWrapper) {

                ReviewsWrapper reviewsWrapper = (ReviewsWrapper) o;
                bus.post(reviewsWrapper);

            } else if (o instanceof ImagesWrapper) {

                ImagesWrapper imagesWrapper = (ImagesWrapper) o;
                bus.post(imagesWrapper);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.out.printf("[DEBUG] RestMovieSource failure - " + error.getMessage());
        }
    };

    public void getMoviesByPage(int page) {

        moviesDBApi.getPopularMoviesByPage(
            Constants.API_KEY,
            page + "",
            retrofitCallback
        );
    }
}
