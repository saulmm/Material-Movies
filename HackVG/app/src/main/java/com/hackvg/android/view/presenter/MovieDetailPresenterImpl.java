package com.hackvg.android.view.presenter;

import com.hackvg.android.domain.GetMovieDetailUsecaseController;
import com.hackvg.android.domain.Usecase;
import com.hackvg.android.model.entities.MovieDetailResponse;
import com.hackvg.android.utils.Constants;
import com.hackvg.android.view.mvp_views.MovieDetailView;

/**
 * Created by saulmm on 31/01/15.
 */
public class MovieDetailPresenterImpl implements MovieDetailPresenter {

    private final MovieDetailView movieDetailView;
    private final String movieID;


    public MovieDetailPresenterImpl(MovieDetailView movieDetailView, String movieID) {

        this.movieDetailView = movieDetailView;
        this.movieID = movieID;
    }

    @Override
    public void showDescription(String description) {

        movieDetailView.setDescription(description);

    }

    @Override
    public void showCover(String url) {

        String coverUrl = Constants.POSTER_PREFIX + url;
        movieDetailView.setImage(coverUrl);

    }

    @Override
    public void onResume() {

        Usecase getDetailUsecase = new GetMovieDetailUsecaseController(this, movieID);
        getDetailUsecase.execute();
    }

    @Override
    public void showTagline(String tagLine) {

    }

    @Override
    public void showName(String title) {

        movieDetailView.setName(title);
    }

    @Override
    public void setChecked() {

    }

    @Override
    public void setPending() {

    }

    @Override
    public void onDetailInformationReceived(MovieDetailResponse response) {

        showDescription(response.getOverview());
        showName(response.getTitle());
        showCover(response.getPoster_path());
    }

    @Override
    public void onViewedPressed() {

        movieDetailView.finish("Viewed");
    }

    @Override
    public void onPendingPressed() {

        movieDetailView.finish("Pending");
    }
}
