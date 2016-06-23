package com.hackvg.model.rest;


import com.hackvg.common.utils.Constants;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.entities.ReviewsWrapper;
import com.squareup.otto.Bus;

import java.util.logging.Level;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

    public void getMovies() {
        moviesDBApi.getPopularMovies(Constants.API_KEY).enqueue(new Callback<MoviesWrapper>() {
            @Override
            public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                MoviesWrapper moviesApiResponse = response.body();
                bus.post(moviesApiResponse);
            }

            @Override
            public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getDetailMovie(String id) {
        moviesDBApi.getMovieDetail(Constants.API_KEY, id).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail detailResponse = response.body();
                bus.post(detailResponse);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getReviews(String id) {
        moviesDBApi.getReviews(Constants.API_KEY, id).enqueue(new Callback<ReviewsWrapper>() {
            @Override
            public void onResponse(Call<ReviewsWrapper> call, Response<ReviewsWrapper> response) {
                ReviewsWrapper reviewsWrapper = response.body();
                bus.post(reviewsWrapper);
            }

            @Override
            public void onFailure(Call<ReviewsWrapper> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getConfiguration() {
        moviesDBApi.getConfiguration(Constants.API_KEY).enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {
                ConfigurationResponse configurationResponse = response.body();
                bus.post(configurationResponse);
            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getImages(String movieId) {
        moviesDBApi.getImages(Constants.API_KEY, movieId).enqueue(new Callback<ImagesWrapper>() {
            @Override
            public void onResponse(Call<ImagesWrapper> call, Response<ImagesWrapper> response) {
                ImagesWrapper imagesWrapper = response.body();
                bus.post(imagesWrapper);
            }

            @Override
            public void onFailure(Call<ImagesWrapper> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getMoviesByPage(int page) {
        moviesDBApi.getPopularMoviesByPage(Constants.API_KEY, page + "").enqueue(
            new Callback<MoviesWrapper>() {
                @Override
                public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                    MoviesWrapper moviesApiResponse = response.body();
                    bus.post(moviesApiResponse);
                }

                @Override
                public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                    System.out.println(t);
                }
            }
        );
    }
}
