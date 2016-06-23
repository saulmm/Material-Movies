package com.hackvg.model.rest;


import com.hackvg.common.utils.Constants;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.entities.ReviewsWrapper;
import com.squareup.otto.Bus;

import java.util.Observable;
import java.util.logging.Level;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Func0;


public class RestMovieSource {
    private final MovieDatabaseAPI moviesDBApi;
    private final Bus bus;

    public RestMovieSource(Bus bus) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build();

        moviesDBApi = new Retrofit.Builder()
            .baseUrl(Constants.MOVIE_DB_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(MovieDatabaseAPI.class);

        this.bus = bus;
    }

    public rx.Observable<MoviesWrapper> getMovies() {
        return rx.Observable.defer(new Func0<rx.Observable<MoviesWrapper>>() {
            @Override
            public rx.Observable<MoviesWrapper> call() {
                return moviesDBApi.getPopularMovies(Constants.API_KEY);
            }
        });
    }

    public rx.Observable<MovieDetail> getDetailMovie(final String id) {
        return rx.Observable.defer(new Func0<rx.Observable<MovieDetail>>() {
            @Override
            public rx.Observable<MovieDetail> call() {
                return moviesDBApi.getMovieDetail(id, Constants.API_KEY);
            }
        });
    }

    public rx.Observable<ReviewsWrapper> getReviews(final String id) {
        return rx.Observable.defer(new Func0<rx.Observable<ReviewsWrapper>>() {
            @Override
            public rx.Observable<ReviewsWrapper> call() {
                return moviesDBApi.getReviews(id, Constants.API_KEY);
            }
        });
    }

    public rx.Observable<ConfigurationResponse> getConfiguration() {
        return rx.Observable.defer(new Func0<rx.Observable<ConfigurationResponse>>() {
            @Override
            public rx.Observable<ConfigurationResponse> call() {
                return moviesDBApi.getConfiguration(Constants.API_KEY);
            }
        });
    }

    public rx.Observable<ImagesWrapper> getImages(final String movieId) {
        return rx.Observable.defer(new Func0<rx.Observable<ImagesWrapper>>() {
            @Override
            public rx.Observable<ImagesWrapper> call() {
                return moviesDBApi.getImages(movieId, Constants.API_KEY);
            }
        });
    }

    public rx.Observable<MoviesWrapper> getMoviesByPage(final int page) {
        return rx.Observable.defer(new Func0<rx.Observable<MoviesWrapper>>() {
            @Override
            public rx.Observable<MoviesWrapper> call() {
            return moviesDBApi.getPopularMoviesByPage(Constants.API_KEY, page);
            }
        });
    }
}
