package com.hackvg.android.view.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hackvg.android.R;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.view.HackVGClickListener;
import com.hackvg.android.view.adapters.MoviesAdapter;
import com.hackvg.android.view.mvp_views.PopularMoviesView;
import com.hackvg.android.view.presenter.PopularShowsPresenter;
import com.hackvg.android.view.presenter.PopularShowsPresenterImpl;
import com.hackvg.android.view.utils.RecyclerInsetsDecoration;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopularMoviesActivity extends Activity implements PopularMoviesView, HackVGClickListener {

    private PopularShowsPresenter popularShowsPresenter;
    private static final int COLUMNS = 2;

    @InjectView(R.id.recycler_popular_movies) RecyclerView popularMoviesRecycler;
    @InjectView(R.id.activity_main_progress) ProgressBar progress;
    @InjectView(R.id.activity_main_toolbar) Toolbar toolbar;

    private MoviesAdapter moviesAdapter;
    public static SparseArray<Bitmap> photoCache = new SparseArray<Bitmap>(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.inject(this);
        popularMoviesRecycler.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        popularMoviesRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        popularMoviesRecycler.setOnScrollListener(recyclerScrollListener);
        popularShowsPresenter = new PopularShowsPresenterImpl(this);
        popularShowsPresenter.onCreate();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public void showMovies(List<TvMovie> movieList) {

        moviesAdapter = new MoviesAdapter(movieList);
        moviesAdapter.setHackVGClickListener(this);
        popularMoviesRecycler.setAdapter(moviesAdapter);
    }

    @Override
    public void showLoading() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void onClick(View v, int position) {

        Log.d("[DEBUG]", "PopularMoviesActivity onClick - Pressed: " + position);

        Intent i = new Intent (PopularMoviesActivity.this, MovieDetailActivity.class);
        String movieID = moviesAdapter.getMovieList().get(position).getId();
        i.putExtra("movie_id", movieID);
        i.putExtra("movie_position", position);

        ImageView coverImage = (ImageView) v.findViewById(R.id.item_movie_cover);
        photoCache.put(0, coverImage.getDrawingCache());

        ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);

        // Setup the transition to the detail activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(v, "cover" + position));

        startActivity(i, options.toBundle());
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public int lastDy;
        public boolean flag;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            if (toolbar == null)
                throw new IllegalStateException("BooksFragment has not a reference of the main toolbar");

            // Is scrolling up
            if (dy > 10) {

                if (!flag) {

                    showToolbar();
                    flag = true;
                }

            // is scrolling down
            } else if (dy < -10) {

               if (flag) {

                   hideToolbar();
                   flag = false;
               }
            }

            lastDy = dy;
        }
    };

    private void showToolbar() {

        toolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_off));
    }

    private void hideToolbar() {

        toolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_on));
    }
}
