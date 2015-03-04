package com.hackvg.android.mvp.presenters;

import android.text.TextUtils;

import com.hackvg.android.mvp.views.DetailView;
import com.hackvg.common.utils.BusProvider;
import com.hackvg.common.utils.Constants;
import com.hackvg.domain.GetMovieDetailUsecaseController;
import com.hackvg.domain.Usecase;
import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.Production_companies;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;

import java.util.List;


public class MovieDetailPresenter extends Presenter {

    // Boolean that indicates if this activity is shown in a tablet or not

    private final DetailView mMovieDetailView;
    private final String mMovieID;

    public MovieDetailPresenter(DetailView movieDetailView, String movieID) {

        mMovieDetailView = movieDetailView;
        mMovieID = movieID;
    }

    public void showDescription(String description) {

        mMovieDetailView.setDescription(description);
    }

    public void showCover(String url) {

        String coverUrl = Constants.BASIC_STATIC_URL + url;
        mMovieDetailView.setImage(coverUrl);
    }

    @Override
    public void start() {

        BusProvider.getUIBusInstance().register(this);

        Usecase getDetailUsecase = new GetMovieDetailUsecaseController(
            mMovieID, BusProvider.getUIBusInstance(), RestMovieSource.getInstance());

        getDetailUsecase.execute();
    }

    @Override
    public void stop() {

        BusProvider.getUIBusInstance().unregister(this);
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
    public void onDetailInformationReceived(MovieDetailResponse response) {

        showDescription(response.getOverview());
        showTitle(response.getTitle());
        showCover(response.getPoster_path());
        showTagline(response.getTagline());
        showCompanies(response.getProduction_companies());
        showHomepage(response.getHomepage());
    }

    public void showHomepage(String homepage) {

        if (!TextUtils.isEmpty(homepage))
            mMovieDetailView.setHomepage(homepage);
    }
}
