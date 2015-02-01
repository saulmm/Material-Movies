package com.hackvg.android.presenter;

import com.hackvg.android.mvp_views.PopularMoviesView;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.domain.GetMoviesUsecase;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.domain.Usecase;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.squareup.otto.Subscribe;


public class PopularShowsPresenterImpl implements PopularShowsPresenter {

    private final PopularMoviesView popularMoviesView;

    public PopularShowsPresenterImpl(PopularMoviesView popularMoviesView) {

        this.popularMoviesView = popularMoviesView;
    }

    @Override
    public void onCreate() {

        BusProvider.getUIBusInstance().register(this);

        popularMoviesView.showLoading();

        Usecase getPopularShows = new GetMoviesUsecaseController(GetMoviesUsecase.TV_MOVIES);
        getPopularShows.execute();
    }

    @Override
    public void onStop() {

        BusProvider.getUIBusInstance().unregister(this);
    }


    @Subscribe
    @Override
    public void onPopularMoviesReceived(PopularMoviesApiResponse popularMovies) {

        popularMoviesView.hideLoading();
        popularMoviesView.showMovies(popularMovies.getResults());
    }
}
