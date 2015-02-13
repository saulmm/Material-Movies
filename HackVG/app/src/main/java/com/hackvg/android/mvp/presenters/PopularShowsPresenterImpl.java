package com.hackvg.android.mvp.presenters;

import com.hackvg.android.mvp.views.MVPPopularMoviesView;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.domain.GetMoviesUsecase;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.domain.Usecase;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.squareup.otto.Subscribe;


public class PopularShowsPresenterImpl implements PopularShowsPresenter {

    private final MVPPopularMoviesView MVPPopularMoviesView;

    public PopularShowsPresenterImpl(MVPPopularMoviesView MVPPopularMoviesView) {

        this.MVPPopularMoviesView = MVPPopularMoviesView;
    }

    @Override
    public void onCreate() {

        BusProvider.getUIBusInstance().register(this);

        MVPPopularMoviesView.showLoading();

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

        MVPPopularMoviesView.hideLoading();
        MVPPopularMoviesView.showMovies(popularMovies.getResults());
    }
}
