package com.hackvg.android.view.presenter;

import com.hackvg.android.model.entities.TvShow;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public interface PopularShowsPresenter {

    public void onPopularShowsReceived(List<TvShow> shows);
}
