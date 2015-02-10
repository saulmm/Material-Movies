package com.hackvg.android.views.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackvg.android.R;
import com.hackvg.android.mvp.presenters.MovieDetailPresenter;
import com.hackvg.android.mvp.presenters.MovieDetailPresenterImpl;
import com.hackvg.android.mvp.views.MVPDetailView;
import com.hackvg.android.utils.GUIUtils;
import com.hackvg.android.utils.HackVGTransitionListener;
import com.hackvg.android.views.custom_views.ObservableScrollView;
import com.hackvg.android.views.custom_views.ScrollViewListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by saulmm on 31/01/15.
 */
public class MovieDetailActivity extends Activity implements MVPDetailView,
    Palette.PaletteAsyncListener, ScrollViewListener {

    @InjectView(R.id.activity_detail_book_info)                     View overviewContainer;
    @InjectView(R.id.activity_detail_movie_description)             TextView descriptionTitle;
    @InjectView(R.id.activity_movie_detail_title)                   TextView titleTextView;
    @InjectView(R.id.activity_movie_detail_content)                 TextView descriptionTextView;
    @InjectView(R.id.activity_detail_homepage_value)                TextView homepageTextview;
    @InjectView(R.id.activity_detail_company_value)                 TextView companiesTextview;
    @InjectView(R.id.activity_detail_label_tagline)                 TextView taglineLabelTextview;
    @InjectView(R.id.activity_detail_tagline_value)                 TextView taglineTextView;
    @InjectView(R.id.activity_movie_detail_confirmation_text)       TextView confirmationText;
    @InjectView(R.id.activity_movie_detail_fab)                     ImageView fabButton;
    @InjectView(R.id.activity_movie_detail_cover_wtf)               ImageView coverImageView;
    @InjectView(R.id.activity_movide_detail_confirmation_image)     ImageView confirmationView;
    @InjectView(R.id.activity_movie_detai_confirmation_container)   FrameLayout confirmationContainer;
    @InjectView(R.id.activity_movie_detail_scroll)                  ObservableScrollView observableScrollView;

    private MovieDetailPresenter detailPresenter;
    private int coverImageHeight;

    private Drawable fabRipple;
    private int mScreenHeight;

    @Override
    protected void onResume() {

        super.onResume();
        detailPresenter.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().getSharedElementEnterTransition().addListener(transitionListener);
        ButterKnife.inject(this);

        int moviePosition = getIntent().getIntExtra("movie_position", 0);
        String movieID = getIntent().getStringExtra("movie_id");

        coverImageView.setTransitionName("cover" + moviePosition);

        fabRipple = getResources().getDrawable(R.drawable.ripple_round);
        fabButton.setBackground(fabRipple);

        fabButton.setScaleX(0);
        fabButton.setScaleY(0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mScreenHeight = size.y;


        observableScrollView.setScrollViewListener(this);

        detailPresenter = new MovieDetailPresenterImpl(this, movieID);
        detailPresenter.onCreate();
    }

    @Override
    protected void onStop() {

        super.onStop();
        detailPresenter.onStop();
    }

    @Override
    public void setImage(String url) {

        Bitmap bookCoverBitmap = MoviesActivityMVP.photoCache.get(0);
        coverImageView.setBackground(new BitmapDrawable(getResources(), bookCoverBitmap));

        Palette.generateAsync(bookCoverBitmap, this);
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

    @Override
    public void finish(String cause) {

        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showConfirmationView() {

        GUIUtils.showViewByRevealEffect(confirmationContainer,
            fabButton, mScreenHeight);

        animateConfirmationView();
        startClosingConfirmationView();
    }

    @Override
    public void animateConfirmationView() {

        Drawable drawable = confirmationView.getDrawable();

        if (drawable instanceof Animatable)
            ((Animatable) drawable).start();
    }

    @Override
    public void startClosingConfirmationView() {

        int milliseconds = 700;
        getWindow().setReturnTransition(new Slide());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                observableScrollView.setVisibility(View.GONE);
                MovieDetailActivity.this. finishAfterTransition();
            }

        }, milliseconds);
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

            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
            Palette.Swatch lightSwatch = palette.getLightVibrantSwatch();

            if (lightSwatch != null) {

                overviewContainer.setBackgroundColor(lightSwatch.getRgb());
                taglineTextView.setTextColor(lightSwatch.getTitleTextColor());
                descriptionTextView.setTextColor(lightSwatch.getTitleTextColor());
                companiesTextview.setTextColor(lightSwatch.getTitleTextColor());
                homepageTextview.setTextColor(lightSwatch.getTitleTextColor());
                fabRipple.setColorFilter(lightSwatch.getRgb(), PorterDuff.Mode.ADD);
                confirmationContainer.setBackgroundColor(lightSwatch.getRgb());
            }

            if (lightSwatch == null && vibrantSwatch != null) {
                colorBrightElements(vibrantSwatch);

                int primaryColor = getResources()
                    .getColor(R.color.theme_primary);

                fabRipple.setColorFilter(primaryColor, PorterDuff.Mode.ADD);
                confirmationView.setBackgroundColor(primaryColor);
                overviewContainer.setBackgroundColor(primaryColor);
            }

            if (darkVibrantSwatch != null && lightSwatch != null)
                colorBrightElements(darkVibrantSwatch);
        }
    }

    public void colorBrightElements (Palette.Swatch brightSwatch) {

        Drawable drawable = confirmationView.getDrawable();
        drawable.setColorFilter(brightSwatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        confirmationText.setTextColor(brightSwatch.getRgb());

        titleTextView.setTextColor(brightSwatch.getTitleTextColor());
        titleTextView.setBackgroundColor(brightSwatch.getRgb());

        if (brightSwatch != null) {

            if (companiesTextview.getVisibility() == View.VISIBLE)
                GUIUtils.tintAndSetCompoundDrawable(this, R.drawable.ic_domain_white_24dp,
                    brightSwatch.getRgb(), companiesTextview);

            if (homepageTextview.getVisibility() == View.VISIBLE)
                GUIUtils.tintAndSetCompoundDrawable(this, R.drawable.ic_public_white_24dp,
                    brightSwatch.getRgb(), homepageTextview);

            taglineLabelTextview.setTextColor(brightSwatch.getRgb());
            descriptionTitle.setTextColor(brightSwatch.getRgb());
        }
    }

    @OnClick(R.id.activity_movie_detail_fab)
    public void onClick(ImageButton v) {

        showConfirmationView();
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (coverImageHeight == 0)
            coverImageHeight = coverImageView.getHeight();



            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                coverImageView.getWidth(), (coverImageHeight - y));

            coverImageView.setLayoutParams(params);
    }

    private final HackVGTransitionListener transitionListener = new HackVGTransitionListener() {

        @Override
        public void onTransitionEnd(Transition transition) {

            super.onTransitionEnd(transition);
            GUIUtils.showViewByScale(fabButton);
        }
    };
}
