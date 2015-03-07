package com.hackvg.android.mvp.views;


import android.graphics.Bitmap;

import com.hackvg.model.entities.Review;

import java.util.List;


public interface DetailView extends MVPView {

    void showFilmCover(Bitmap bitmap);

    public void setName (String title);

    public void setDescription(String description);

    public void setHomepage (String homepage);

    public void setCompanies (String companies);

    public void setTagline(String tagline);

    public void finish(String cause);

    public void showConfirmationView ();

    public void animateConfirmationView ();

    public void startClosingConfirmationView();

    public void showReviews(List<Review> results);

    public void showLoadingIndicator ();

    public void hideLoadingIndicator ();
}
