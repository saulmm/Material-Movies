package com.hackvg.android.views.activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackvg.android.R;
import com.hackvg.android.mvp.presenters.MovieDetailPresenter;
import com.hackvg.android.mvp.views.DetailView;
import com.hackvg.android.utils.GUIUtils;
import com.hackvg.android.views.custom_views.ObservableScrollView;
import com.hackvg.android.views.custom_views.ScrollViewListener;
import com.hackvg.model.entities.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.Optional;

import static android.support.v7.graphics.Palette.Swatch;
import static android.widget.LinearLayout.LayoutParams;

public class MovieDetailActivity extends Activity implements DetailView,
    Palette.PaletteAsyncListener, ScrollViewListener {

    private static final int TAGLINE_HEADER         = 0;
    private static final int DESCRIPTION_HEADER     = 1;
    private static final int REVIEWS_HEADER         = 2;

    private static final int TITLE                  = 0;
    private static final int DESCRIPTION            = 1;
    private static final int HOMEPAGE               = 2;
    private static final int COMPANY                = 3;
    private static final int TAGLINE                = 4;
    private static final int CONFIRMATION           = 5;

    private int mReviewsColor                       = -1;
    private int mReviewsAuthorColor                 = -1;

    // Boolean that indicates if the activity is shown in a tablet or not
    boolean mIsTablet;

    // The time that the confirmation view will be shown (milliseconds)
    private static final int CONFIRMATION_VIEW_DELAY = 1500;
    private MovieDetailPresenter mDetailPresenter;

    private Swatch mBrightSwatch;

    @InjectViews({
        R.id.activity_detail_title,
        R.id.activity_detail_content,
        R.id.activity_detail_homepage,
        R.id.activity_detail_company,
        R.id.activity_detail_tagline,
        R.id.activity_detail_confirmation_text,
    })
    List<TextView> mMovieInfoTextViews;

    @InjectViews({
        R.id.activity_detail_header_tagline,
        R.id.activity_detail_header_description,
        R.id.activity_detail_header_reviews
    })
    List<TextView> movieHeaders;

    @InjectView(R.id.activity_detail_progress)              ProgressBar progressBar;

    @InjectView(R.id.activity_detail_book_info)             LinearLayout mMovieDescriptionContainer;
    @InjectView(R.id.activity_detail_fab)                   ImageView mFabButton;
    @InjectView(R.id.activity_detail_cover)                 ImageView mCoverImageView;
    @InjectView(R.id.activity_detail_confirmation_image)    ImageView mConfirmationView;
    @InjectView(R.id.activity_detail_confirmation_container)FrameLayout mConfirmationContainer;

    @InjectView(R.id.activity_movie_detail_scroll)          ObservableScrollView mObservableScrollView;
    @InjectView(R.id.activity_detail_information_container) View mInformationContainer;

    @Optional
    @InjectView(R.id.activity_detail_image)                 ImageView mMovieImageView;
    private int[] mViewLastLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        mIsTablet = getContext().getResources()
            .getBoolean(R.bool.is_tablet);

        // Completes the SharedElement transition on Lollipop and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int moviePosition = getIntent().getIntExtra("movie_position", 0);
            mCoverImageView.setTransitionName("cover" + moviePosition);
            GUIUtils.makeTheStatusbarTranslucent(this);

            getWindow().getSharedElementEnterTransition().addListener(
                new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {

                        GUIUtils.showViewByScale(mFabButton);
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });

        } else {

            mViewLastLocation = getIntent().getIntArrayExtra("view_location");

            if (!mIsTablet) {

                GUIUtils.startScaleAnimationFromPivot(
                    mViewLastLocation[0], mViewLastLocation[1],
                    mObservableScrollView, mIsTablet, null);
            }
        }

        String movieID = getIntent().getStringExtra("movie_id");
        mDetailPresenter = new MovieDetailPresenter(this, movieID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mIsTablet)
            mObservableScrollView.setScrollViewListener(this);
    }

    @Override
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            GUIUtils.hideScaleAnimationFromPivot(mObservableScrollView, mIsTablet,
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        overridedBackPressed();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
        }
    }

    public void overridedBackPressed () {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mDetailPresenter.stop();
    }

    @Override
    protected void onStart() {

        super.onStart();
        mDetailPresenter.start();
    }

    @Override
    public void setHomepage(String homepage) {

        mMovieInfoTextViews.get(HOMEPAGE).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(HOMEPAGE).setText(homepage);
    }

    @Override
    public void setCompanies(String companies) {

        mMovieInfoTextViews.get(COMPANY).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(COMPANY).setText(companies);
    }

    @Override
    public void showFilmCover(Bitmap bitmap) {

        mCoverImageView.setImageBitmap(bitmap);
        Palette.generateAsync(bitmap, this);
    }

    @Override
    public void showMovieImage(String url) {

        if (mIsTablet && mMovieImageView != null) {

            Picasso.with(this).load(url)
                .fit().centerCrop()
                .into(mMovieImageView);
        }
    }

    @Override
    public void setName(String title) {

        mMovieInfoTextViews.get(TITLE).setText(title);
    }

    @Override
    public void setDescription(String description) {

        movieHeaders.get(DESCRIPTION_HEADER).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(DESCRIPTION).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(DESCRIPTION).setText(description);
    }

    @Override
    public void setTagline(String tagline) {

        movieHeaders.get(TAGLINE_HEADER).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(TAGLINE).setVisibility(View.VISIBLE);
        mMovieInfoTextViews.get(TAGLINE).setText(tagline);
    }

    /**
     * Creates a TextView with a Spannable format programmatically
     * containing the author by whom was the review written and the
     * review text
     *
     * @param reviewList a list containing reviews of the film
     */
    @Override
    public void showReviews(List<Review> reviewList) {

        final int reviewMarginTop = getResources().getDimensionPixelOffset(
            R.dimen.activity_vertical_margin_half);

        final LayoutParams layoutParams = new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, reviewMarginTop, 0, 0);

        movieHeaders.get(REVIEWS_HEADER).setVisibility(View.VISIBLE);

        for (Review result : reviewList) {

            // Creates a TextView from programmatically
            TextView reviewTextView = new TextView(this);
            reviewTextView.setTextAppearance(this,
                R.style.MaterialMoviesReviewTextView);

            if (mReviewsColor != -1)
                reviewTextView.setTextColor(mReviewsColor);

            // Configure the review text
            String reviewCredit = "Review written by " + result.getAuthor();

            String reviewText = String.format("%s - %s",
                reviewCredit, result.getContent());

            Spannable spanColorString = new SpannableString(reviewText);
            spanColorString.setSpan(new ForegroundColorSpan(mReviewsAuthorColor),
                0, reviewCredit.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            reviewTextView.setText(spanColorString);

            mMovieDescriptionContainer.addView(reviewTextView,
                layoutParams);
        }
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

    /**
     * Show a confirmation view with a reveal animation if the android version
     * is v21 or higher, otherwise the view visibility is set to visible without
     * an animation
     */
    @Override
    public void showConfirmationView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            GUIUtils.showViewByRevealEffect(mConfirmationContainer,
                mFabButton, GUIUtils.getWindowWidth(this));

        } else {

            mConfirmationContainer.setVisibility(View.VISIBLE);
        }

        animateConfirmationView();
        startClosingConfirmationView();
    }

    /**
     * Starts an animation provided by a <animation-drawable> on Lollipop &
     * higher versions, in lower versions a simple set with a scale and a rotate
     * animation is shown
     */
    @Override
    public void animateConfirmationView() {

        Drawable drawable = mConfirmationView.getDrawable();

        // Animated drawables are supported on Lollipop and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (drawable instanceof Animatable)
                ((Animatable) drawable).start();
//
        } else {

            mConfirmationView.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.appear_rotate));
        }
    }

    @Override
    public void startClosingConfirmationView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setReturnTransition(new Slide());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mObservableScrollView.setVisibility(View.GONE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    MovieDetailActivity.this.finishAfterTransition();

                else
                    MovieDetailActivity.this.finish();

            }

        }, CONFIRMATION_VIEW_DELAY);
    }

    @Override
    public void showLoadingIndicator() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {

        progressBar.setVisibility(View.GONE);
    }

    public void setBackgroundAndContentColors (Swatch swatch) {

        if (swatch != null) {

            mReviewsColor = swatch.getTitleTextColor();

            mObservableScrollView.setBackgroundColor(swatch.getRgb());

            mConfirmationContainer.setBackgroundColor(swatch.getRgb());

            ButterKnife.apply(mMovieInfoTextViews, GUIUtils.setter,
                swatch.getTitleTextColor());

        } // else use colors of the layout
    }

    public void setHeadersTitleAndFabColors (Swatch swatch) {

        if (swatch != null) {

            mBrightSwatch = swatch;

            mReviewsAuthorColor = swatch.getRgb();

            mFabButton.getBackground().setColorFilter(swatch.getRgb(),
                PorterDuff.Mode.MULTIPLY);

            mMovieInfoTextViews.get(CONFIRMATION).setTextColor(
                swatch.getRgb());

            GUIUtils.tintAndSetCompoundDrawable(this, R.drawable.ic_domain_white_24dp,
                swatch.getRgb(), mMovieInfoTextViews.get(HOMEPAGE));

            GUIUtils.tintAndSetCompoundDrawable(this, R.drawable.ic_public_white_24dp,
                swatch.getRgb(), mMovieInfoTextViews.get(COMPANY));

            ButterKnife.apply(movieHeaders, GUIUtils.setter,
                swatch.getRgb());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Drawable drawable = mConfirmationView.getDrawable();
                drawable.setColorFilter(swatch.getRgb(),
                    PorterDuff.Mode.MULTIPLY);

            } else {

                mConfirmationView.setColorFilter(swatch.getRgb(),
                    PorterDuff.Mode.MULTIPLY);
            }
        }  // else use colors of the layout
    }

    @Override
    public void onGenerated(Palette palette) {

        if (palette != null) {

            final Swatch darkVibrantSwatch    = palette.getDarkVibrantSwatch();
            final Swatch darkMutedSwatch      = palette.getDarkMutedSwatch();
            final Swatch lightVibrantSwatch   = palette.getLightVibrantSwatch();
            final Swatch lightMutedSwatch     = palette.getLightMutedSwatch();

            final Swatch backgroundAndContentColors = (darkVibrantSwatch != null)
                ? darkVibrantSwatch : darkMutedSwatch;

            final Swatch titleAndFabColors = (darkVibrantSwatch != null)
                ? lightVibrantSwatch : lightMutedSwatch;

            setBackgroundAndContentColors(backgroundAndContentColors);

            setHeadersTitleAndFabColors(titleAndFabColors);

            setVibrantElements(backgroundAndContentColors, titleAndFabColors);
        }
    }

    private void setVibrantElements(Swatch lightSwatch, Swatch darkSwatch) {

        if (mBrightSwatch != null)
            mMovieInfoTextViews.get(TITLE).setBackgroundColor(
                mBrightSwatch.getRgb());

        if (darkSwatch != null)
            mMovieInfoTextViews.get(TITLE).setTextColor(
                darkSwatch.getTitleTextColor());
    }

    @OnClick(R.id.activity_detail_fab)
    public void onClick() {

        showConfirmationView();
    }

    boolean isTranslucent = false;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (y > mCoverImageView.getHeight()) {

            mMovieInfoTextViews.get(TITLE).setTranslationY(
                y - mCoverImageView.getHeight());

            if (!isTranslucent) {

                isTranslucent = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    GUIUtils.setTheStatusbarNotTranslucent(this);
                    getWindow().setStatusBarColor(mBrightSwatch.getRgb());
                }
            }
        }

        if (y < mCoverImageView.getHeight() && isTranslucent) {

            mMovieInfoTextViews.get(TITLE).setTranslationY(0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                GUIUtils.makeTheStatusbarTranslucent(this);
                isTranslucent = false;
            }
        }
    }
}
