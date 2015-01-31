package com.hackvg.android.view.presenter;

import com.hackvg.android.model.entities.MovieDetailResponse;

/**
 * Created by saulmm on 31/01/15.
 */
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
