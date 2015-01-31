package com.hackvg.android.model.client;

import android.util.Log;

import com.hackvg.android.model.MediaDataSource;
import com.hackvg.android.model.TheMovieDBApi;
import com.hackvg.android.model.entities.PopularShowsResponse;
import com.hackvg.android.model.entities.TvShow;
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
    private final TheMovieDBApi moviesDBApi;

    private RestMovieSource() {

        RestAdapter parkappRest = new RestAdapter.Builder()
            .setEndpoint(Constants.MOVIE_DB_HOST)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

        moviesDBApi = parkappRest.create(TheMovieDBApi.class);

    }

    public static RestMovieSource getInstance() {

        if (INSTANCE == null)
            INSTANCE = new RestMovieSource();

        return INSTANCE;
    }

    @Override
    public void getShows() {

        moviesDBApi.getPopularShows(Constants.API_KEY, new Callback<PopularShowsResponse>() {
            @Override
            public void success(PopularShowsResponse popularSeriesResponse, Response response) {

                for (TvShow show : popularSeriesResponse.getTvShows()) {

                    Log.d("[DEBUG]", "RestMovieSource success - " + show.toString());

                }

                BusProvider.getBusInstance().post(popularSeriesResponse);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("[ERROR]", "RestMovieSource 60 Error:  - "+error.getMessage());
            }
        });

    }
}
