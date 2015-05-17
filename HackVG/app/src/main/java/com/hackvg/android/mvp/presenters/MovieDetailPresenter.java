package com.hackvg.android.mvp.presenters;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.hackvg.android.mvp.views.DetailView;
import com.hackvg.android.views.activities.MoviesActivity;
import com.hackvg.common.utils.Constants;
import com.hackvg.domain.GetMovieDetailUsecase;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.Production_companies;
import com.hackvg.model.entities.ReviewsWrapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;


public class MovieDetailPresenter extends Presenter {

    private final Bus mBus;
    private DetailView mMovieDetailView;
    private final GetMovieDetailUsecase mMovieDetailUsecase;

    @Inject
    public MovieDetailPresenter(GetMovieDetailUsecase movieDetailUsecase, Bus bus) {

        mMovieDetailUsecase = movieDetailUsecase;
        mBus = bus;
    }

    public void attachView(DetailView movieDetailView) {

        mMovieDetailView = movieDetailView;
        mMovieDetailView.showFilmCover(MoviesActivity.sPhotoCache.get(0));
        mMovieDetailUsecase.execute();
    }

    public void showDescription(String description) {

        mMovieDetailView.setDescription(description);
    }

    @Override
    public void start() {

        mBus.register(this);
    }

    @Override
    public void stop() {

        mBus.unregister(this);
    }

    public void showTagline(String tagLine) {

        mMovieDetailView.setTagline(tagLine);
    }

    public void showTitle(String title) {

        mMovieDetailView.setName(title);
    }

    public void showCompanies(List<Production_companies> companies) {

        String companiesString = "";

        for (int i = 0; i < companies.size(); i++) {

            Production_companies company = companies.get(i);
            companiesString += company.getName();

            if (i != companies.size() -1)
                companiesString += ", ";
        }

        if (!companies.isEmpty())
            mMovieDetailView.setCompanies(companiesString);
    }

    @Subscribe
    public void onDetailInformationReceived(MovieDetail response) {

        showDescription(response.getOverview());
        showTitle(response.getTitle());
        showTagline(response.getTagline());
        showCompanies(response.getProduction_companies());
        showHomepage(response.getHomepage());
        showFilmImage(response.getMovieImagesList());
    }

    private void showFilmImage(List<ImagesWrapper.MovieImage> movieImagesList) {

        if (movieImagesList != null && movieImagesList.size() > 0) {

            int randomIndex = new Random().nextInt(movieImagesList.size());
            Log.d("[DEBUG]", "MovieDetailPresenter showFilmImage - Random index: "+randomIndex);

            mMovieDetailView.showMovieImage (Constants.BASIC_STATIC_URL +
                movieImagesList.get(randomIndex).getFile_path());
        }
    }

    @Subscribe
    public void onReviewsReceived (final ReviewsWrapper reviewsWrapper) {

        // Wait 300 milliseconds to ensure that Palette generates the colors
        // before show the reviews
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (reviewsWrapper.getResults().size() > 0)
                    mMovieDetailView.showReviews(reviewsWrapper.getResults());
            }
        }, 300);

    }

    public void showHomepage(String homepage) {

        if (!TextUtils.isEmpty(homepage))
            mMovieDetailView.setHomepage(homepage);
    }
}
