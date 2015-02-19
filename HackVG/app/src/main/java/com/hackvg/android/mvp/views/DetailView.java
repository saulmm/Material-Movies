package com.hackvg.android.mvp.views;


/**
 * Created by saulmm on 31/01/15.
 */
public interface DetailView extends MVPView {

    public void setImage (String url);

    public void setName (String title);

    public void setDescription(String description);

    public void setHomepage (String homepage);

    public void setCompanies (String companies);

    public void setTagline(String tagline);

    public void finish(String cause);

    public void showConfirmationView ();

    public void animateConfirmationView ();

    public void startClosingConfirmationView();
}
