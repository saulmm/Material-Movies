package com.hackvg.android.presentation.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hackvg.android.R;
import com.hackvg.android.presentation.mvp_views.MovieDetailView;
import com.hackvg.android.presentation.presenter.MovieDetailPresenter;
import com.hackvg.android.presentation.presenter.MovieDetailPresenterImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saulmm on 31/01/15.
 */
public class MovieDetailActivity extends Activity
    implements MovieDetailView, Palette.PaletteAsyncListener, View.OnClickListener {

    @InjectView(R.id.activity_movie_detail_cover_wtf)           ImageView coverImageView;
    @InjectView(R.id.activity_movie_detail_title)               TextView titleTextView;
    @InjectView(R.id.activity_movie_detail_content)             TextView descriptionTextView;
    @InjectView(R.id.activity_detail_book_info)                 View overviewContainer;
    @InjectView(R.id.activity_detail_movie_description)         TextView descriptionTitle;

    private MovieDetailPresenter detailPresenter;
    private FloatingActionButton fabPending;
    private FloatingActionButton fabDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        int moviePosition = getIntent().getIntExtra("movie_position", 0);
        String movieID = getIntent().getStringExtra("movie_id");

        coverImageView.setTransitionName("cover" + moviePosition);

        // This views cannot be injected by ButterKnife
        fabDone = (FloatingActionButton) findViewById(R.id.activity_detail_fab_done);
        fabDone.setOnClickListener(this);

        fabPending = (FloatingActionButton) findViewById(R.id.activity_detail_fab_pending);
        fabPending.setOnClickListener(this);

        this.detailPresenter = new MovieDetailPresenterImpl(this, movieID);
    }

    @Override
    public void setImage(String url) {

        Bitmap bookCoverBitmap = PopularMoviesActivity.photoCache.get(0);
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
}
