package com.hackvg.android.presentation.presenter;

import com.hackvg.android.model.entities.MovieDetailResponse;


@SuppressWarnings("UnusedDeclaration")
public interface MovieDetailPresenter  {

    public void showDescription (String description);

    public void showCover (String url);

    public void onResume();

    public void showTagline (String tagLine);

    public void showName (String title);

    public void setChecked ();

    public void setPending ();

    public void onDetailInformationReceived (MovieDetailResponse response);

    void onViewedPressed();

    void onPendingPressed();
}
