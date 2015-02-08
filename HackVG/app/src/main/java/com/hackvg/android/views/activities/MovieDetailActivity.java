package com.hackvg.android.views.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hackvg.android.R;
import com.hackvg.android.mvp.presenters.MovieDetailPresenter;
import com.hackvg.android.mvp.presenters.MovieDetailPresenterImpl;
import com.hackvg.android.mvp.views.MVPDetailView;
import com.hackvg.android.views.custom_views.ObservableScrollView;
import com.hackvg.android.views.custom_views.ScrollViewListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saulmm on 31/01/15.
 */
public class MovieDetailActivity extends Activity
    implements MVPDetailView, Palette.PaletteAsyncListener, View.OnClickListener, ScrollViewListener {

    @InjectView(R.id.activity_movie_detail_cover_wtf)           ImageView coverImageView;
    @InjectView(R.id.activity_movie_detail_title)               TextView titleTextView;
    @InjectView(R.id.activity_movie_detail_content)             TextView descriptionTextView;
    @InjectView(R.id.activity_detail_book_info)                 View overviewContainer;
    @InjectView(R.id.activity_detail_movie_description)         TextView descriptionTitle;
    @InjectView(R.id.activity_movie_detail_scroll)              ObservableScrollView observableScrollView;
    @InjectView(R.id.activity_detail_homepage_value)            TextView homepageTextview;
    @InjectView(R.id.activity_detail_company_value)             TextView companiesTextview;
    @InjectView(R.id.activity_detail_label_tagline)             TextView taglineLabelTextview;
    @InjectView(R.id.activity_detail_tagline_value)             TextView taglineTextView;

    private MovieDetailPresenter detailPresenter;
    private FloatingActionButton fabPending;
    private FloatingActionButton fabDone;
    private int mActionBarSize;
    private int coverImageHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);
        mActionBarSize = (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material);


        int moviePosition = getIntent().getIntExtra("movie_position", 0);
        String movieID = getIntent().getStringExtra("movie_id");

        coverImageView.setTransitionName("cover" + moviePosition);

        // This views cannot be injected by ButterKnife
        fabDone = (FloatingActionButton) findViewById(R.id.activity_detail_fab_done);
        fabDone.setOnClickListener(this);

        observableScrollView.setScrollViewListener(this);

        fabPending = (FloatingActionButton) findViewById(R.id.activity_detail_fab_pending);
        fabPending.setOnClickListener(this);

        this.detailPresenter = new MovieDetailPresenterImpl(this, movieID);
        this.detailPresenter.onCreate();
    }

    @Override
    protected void onStop() {

        super.onStop();
        this.detailPresenter.onStop();
    }

    @Override
    public void setImage(String url) {

        Bitmap bookCoverBitmap = MoviesActivityMVP.photoCache.get(0);
        coverImageView.setBackground(new BitmapDrawable(getResources(), bookCoverBitmap));

        Palette.generateAsync(bookCoverBitmap, this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        detailPresenter.onResume();
    }


    @Override
    public void setName(String title) {

        titleTextView.setText(title);
    }

    @Override
    public void setDescription(String description) {

        descriptionTextView.setText(description);
    }

    @Override
    public Context getContext() {

        return this;
    }

    public void finish(String cause) {

        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void changePendingIcon(int drawable) {
        fabPending.setIcon(drawable);
    }

    @Override
    public void changeViewedIcon(int drawable) {
        fabDone.setIcon(drawable);
    }

    @Override
    public void setHomepage(String homepage) {

        homepageTextview.setVisibility(View.VISIBLE);
        homepageTextview.setText(homepage);
    }

    @Override
    public void setCompanies(String companies) {

        companiesTextview.setVisibility(View.VISIBLE);
        companiesTextview.setText(companies);
    }

    @Override
    public void setTagline(String tagline) {

        taglineTextView.setText(tagline);
    }

    @Override
    public void onGenerated(Palette palette) {

        if (palette != null) {

            Palette.Swatch vibrantSwatch    = palette.getVibrantSwatch();
            Palette.Swatch lightSwatch      = palette.getLightVibrantSwatch();

            if (vibrantSwatch != null) {

                titleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                titleTextView.setBackgroundColor(vibrantSwatch.getRgb());
                taglineLabelTextview.setTextColor(vibrantSwatch.getRgb());
                descriptionTitle.setTextColor(vibrantSwatch.getRgb());

                fabPending.setColorNormal(vibrantSwatch.getRgb());
                fabDone.setColorNormal(vibrantSwatch.getRgb());

                if (companiesTextview.getVisibility() == View.VISIBLE) {

                    Drawable companyIcon = getResources().getDrawable(R.drawable.ic_domain_white_24dp);
                    companyIcon.setColorFilter(vibrantSwatch.getRgb(), PorterDuff.Mode.MULTIPLY);
                    companiesTextview.setCompoundDrawablesRelativeWithIntrinsicBounds(companyIcon, null, null, null);
                    companiesTextview.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
                }

                if (homepageTextview.getVisibility() == View.VISIBLE) {

                    Drawable homePage = getResources().getDrawable(R.drawable.ic_public_white_24dp);
                    homePage.setColorFilter(vibrantSwatch.getRgb(), PorterDuff.Mode.MULTIPLY);
                    homepageTextview.setCompoundDrawablesRelativeWithIntrinsicBounds(homePage, null, null, null);
                    homepageTextview.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
                }
            }

            if (lightSwatch != null) {

                overviewContainer.setBackgroundColor(lightSwatch.getRgb());
                taglineTextView.setTextColor(lightSwatch.getTitleTextColor());
                descriptionTextView.setTextColor(lightSwatch.getTitleTextColor());
                companiesTextview.setTextColor(lightSwatch.getTitleTextColor());
                homepageTextview.setTextColor(lightSwatch.getTitleTextColor());

            } else {

                overviewContainer.setBackgroundColor(
                    getResources().getColor(R.color.theme_primary));
            }

        } else {

            Log.e("[ERROR]", "MovieDetailActivity 139  - Error ");
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.activity_detail_fab_pending) {

            detailPresenter.onPendingPressed();

        } else if (v.getId() == R.id.activity_detail_fab_done) {

            detailPresenter.onViewedPressed();
        }
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {


        if (coverImageHeight == 0) {
            coverImageHeight = coverImageView.getHeight();
        }

        if (y < 400) {

            Log.d("[DEBUG]", "MovieDetailActivity onScrollChanged - "+(coverImageHeight - y));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                coverImageView.getWidth(), (coverImageHeight - y));

            coverImageView.setLayoutParams(params);

        } else {

            Log.d("[DEBUG]", "DetailActivity onScrollChanged - No more®");
        }
    }
}
