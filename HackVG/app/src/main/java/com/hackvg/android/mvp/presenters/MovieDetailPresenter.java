package com.hackvg.android.mvp.presenters;

import com.hackvg.model.entities.MovieDetailResponse;
import com.hackvg.model.entities.Production_companies;

import java.util.List;


@SuppressWarnings("UnusedDeclaration")
public interface MovieDetailPresenter  {

    public void showDescription (String description);

    public void showCover (String url);

    public void onResume();

    public void onCreate ();

    public void onStop ();

    public void showTagline (String tagLine);

    public void showName (String title);

    public void showCompanies (List<Production_companies> companies);

    public void setChecked ();

    public void setPending ();

    public void onDetailInformationReceived (MovieDetailResponse response);

    public void onViewedPressed();

    public void onPendingPressed();

    public void showHomepage (String homepage);
}
