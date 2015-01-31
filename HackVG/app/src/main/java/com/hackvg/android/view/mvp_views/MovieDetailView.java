package com.hackvg.android.view.mvp_views;


/**
 * Created by saulmm on 31/01/15.
 */
public interface MovieDetailView extends MovieView {

    public void setImage (String url);

    public void setTagLine (String tagLine);

    public void setName (String title);

    public void setDescription(String description);

    public void showLoading ();

    public void hideLoading ();

    public void finish(String cause);



}
