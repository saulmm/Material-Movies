package com.hackvg.android.views.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
        coverImageView.setPivotY(1);

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
    public void onGenerated(Palette palette) {

        if (palette != null) {

            Palette.Swatch vibrantSwatch    = palette.getVibrantSwatch();
            Palette.Swatch lightSwatch      = palette.getLightVibrantSwatch();

            if (vibrantSwatch != null) {

                titleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                titleTextView.setBackgroundColor(vibrantSwatch.getRgb());
                descriptionTitle.setTextColor(vibrantSwatch.getRgb());

                fabPending.setColorNormal(vibrantSwatch.getRgb());
                fabDone.setColorNormal(vibrantSwatch.getRgb());
            }

            if (lightSwatch != null) {

                overviewContainer.setBackgroundColor(lightSwatch.getRgb());
                descriptionTextView.setTextColor(lightSwatch.getTitleTextColor());

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

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                coverImageView.getWidth(), (coverImageHeight - y));

            coverImageView.setLayoutParams(params);

        } else {

            Log.d("[DEBUG]", "DetailActivity onScrollChanged - No more®");

        }
    }
}
