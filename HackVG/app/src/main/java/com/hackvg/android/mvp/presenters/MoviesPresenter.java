package com.hackvg.android.mvp.presenters;

import com.hackvg.android.mvp.views.MoviesView;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.common.utils.Constants;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;


public class MoviesPresenter extends Presenter {

    private final MoviesView mMoviesView;
    private GetMoviesUsecaseController mGetPopularShows;
    private boolean isLoading = false;
    private ConfigurationUsecaseController mConfigureUsecase;


    public MoviesPresenter(MoviesView moviesView) {

        mMoviesView = moviesView;

        mGetPopularShows = new GetMoviesUsecaseController(
            RestMovieSource.getInstance(), BusProvider.getUIBusInstance());

        mConfigureUsecase = new ConfigurationUsecaseController(
            RestMovieSource.getInstance(), BusProvider.getUIBusInstance());
    }

    public MoviesPresenter(MoviesView moviesView, MoviesWrapper moviesWrapper) {

        mMoviesView = moviesView;

        mGetPopularShows = new GetMoviesUsecaseController(
            RestMovieSource.getInstance(), BusProvider.getUIBusInstance());

        onPopularMoviesReceived(moviesWrapper);
    }

    @Subscribe
    public void onPopularMoviesReceived(MoviesWrapper moviesWrapper) {

        if (mMoviesView.isTheListEmpty()) {

            mMoviesView.hideLoading();
            mMoviesView.showMovies(moviesWrapper.getResults());

        } else {

            mMoviesView.hideActionLabel();
            mMoviesView.appendMovies(moviesWrapper.getResults());
        }

        isLoading = false;
    }

    @Subscribe
    public void onConfigurationFinished (String baseImageUrl) {

        Constants.BASIC_STATIC_URL = baseImageUrl;
        mGetPopularShows.execute();
    }

    public void onEndListReached () {

        mGetPopularShows.execute();
        mMoviesView.showLoadingLabel ();
        isLoading = true;
    }

    @Override
    public void start() {

        if (mMoviesView.isTheListEmpty()) {

            BusProvider.getUIBusInstance().register(this);

            mMoviesView.showLoading();
            mConfigureUsecase.execute();
        }
    }

    @Override
    public void stop() {

        if (mGetPopularShows != null) {

            mGetPopularShows.unRegister();
        }

        if (mConfigureUsecase != null) {

            BusProvider.getUIBusInstance()
                .unregister(this);
        }
    }

    public boolean isLoading() {

        return isLoading;
    }

    public void setLoading(boolean isLoading) {

        this.isLoading = isLoading;
    }
}