package com.hackvg.android.mvp.views;


/**
 * Created by saulmm on 31/01/15.
 */
public interface MovieDetailView extends MovieView {

    public void setImage (String url);

    public void setName (String title);

    public void setDescription(String description);

    public void finish(String cause);

    public void changePendingIcon(int drawable);

    public void changeViewedIcon(int drawable);
}
