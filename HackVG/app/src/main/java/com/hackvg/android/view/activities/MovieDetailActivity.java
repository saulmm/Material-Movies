package com.hackvg.android.view.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackvg.android.R;
import com.hackvg.android.view.mvp_views.MovieDetailView;
import com.hackvg.android.view.presenter.MovieDetailPresenter;
import com.hackvg.android.view.presenter.MovieDetailPresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saulmm on 31/01/15.
 */
public class MovieDetailActivity extends Activity implements MovieDetailView {

    @InjectView(R.id.activity_movie_detail_cover_wtf)   ImageView coverImageView;
    @InjectView(R.id.activity_movie_detail_title)   TextView titleTextView;
    @InjectView(R.id.activity_movie_detail_content) TextView descriptionTextView;

    private MovieDetailPresenter detailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        findViewById(R.id.activity_detail_fab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPresenter.onPendingPressed();
            }
        });

        findViewById(R.id.activity_detail_fab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPresenter.onViewedPressed();
            }
        });

        String movieID = getIntent().getStringExtra("movie_id");
        this.detailPresenter = new MovieDetailPresenterImpl(this, movieID);
    }

    @Override
    public void setImage(String url) {

        Picasso.with(this)
            .load(url)
            .into(coverImageView);
    }

    @Override
    protected void onResume() {

        super.onResume();
        detailPresenter.onResume();
    }

    @Override
    public void setTagLine(String tagLine) {

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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {

        return this;
    }

    public void finish (String cause) {

        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
