package com.hackvg.android.mvp.presenters;

import com.hackvg.android.mvp.views.MoviesView;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.common.utils.Constants;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.domain.Usecase;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;


public class MoviesPresenter extends Presenter {

    private final MoviesView mMoviesView;
    private GetMoviesUsecaseController mGetPopularShows;
    private boolean isLoading = false;


    public MoviesPresenter(MoviesView moviesView) {

        mMoviesView = moviesView;

        mGetPopularShows = new GetMoviesUsecaseController(
            RestMovieSource.getInstance(), BusProvider.getUIBusInstance());
    }

    @Subscribe
    public void onPopularMoviesReceived(PopularMoviesApiResponse popularMovies) {

        mMoviesView.hideLoading();
        mMoviesView.showMovies(popularMovies.getResults());
    }

    @Subscribe
    public void onConfigurationFinished (String baseImageUrl) {

        Constants.BASIC_STATIC_URL = baseImageUrl;
        mGetPopularShows.execute();
    }


    public void onEndListReached () {

//        mGetPopularShows.execute();
        mMoviesView.showLoadingLabel ();
        isLoading = true;
    }

    @Override
    public void start() {

        BusProvider.getUIBusInstance().register(this);

        mMoviesView.showLoading();

        Usecase configureUsecase = new ConfigurationUsecaseController(
            RestMovieSource.getInstance(), BusProvider.getUIBusInstance());

        configureUsecase.execute();
    }

    @Override
    public void stop() {

        BusProvider.getUIBusInstance().unregister(this);
    }

    public boolean isLoading() {

        return isLoading;
    }

    public void setLoading(boolean isLoading) {

        this.isLoading = isLoading;
    }
}
