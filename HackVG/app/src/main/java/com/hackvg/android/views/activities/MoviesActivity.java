package com.hackvg.android.views.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hackvg.android.R;
import com.hackvg.android.mvp.presenters.MoviesPresenter;
import com.hackvg.android.mvp.views.MoviesView;
import com.hackvg.android.utils.RecyclerInsetsDecoration;
import com.hackvg.android.utils.RecyclerViewClickListener;
import com.hackvg.android.views.adapters.MoviesAdapter;
import com.hackvg.android.views.fragments.NavigationDrawerFragment;
import com.hackvg.model.entities.TvMovie;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MoviesActivity extends ActionBarActivity implements
    MoviesView, RecyclerViewClickListener, View.OnClickListener {

    /**
     * Number of columns in the RecyclerView
     */
    private static final int COLUMNS = 2;

    /**
     * A container used between this activity and MovieDetailActivity
     * to share a Bitmap with a SharedElementTransition
     */
    public static SparseArray<Bitmap> sPhotoCache = new SparseArray<Bitmap>(1);

    private MoviesAdapter mMoviesAdapter;
    private MoviesPresenter mMoviesPresenter;
    private GridLayoutManager mGridLayoutManager;

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    @InjectView(R.id.activity_movies_toolbar)   Toolbar mToolbar;
    @InjectView(R.id.activity_movies_progress)  ProgressBar mProgressBar;
    @InjectView(R.id.recycler_popular_movies)   RecyclerView mRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(this);

        mGridLayoutManager = new GridLayoutManager(this, COLUMNS);
        mRecycler.setLayoutManager(mGridLayoutManager);
        mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRecycler.setOnScrollListener(recyclerScrollListener);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
            getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));

        mMoviesPresenter = new MoviesPresenter(this);
    }

    @Override
    protected void onStop() {

        super.onStop();
        mMoviesPresenter.stop();
    }

    @Override
    protected void onStart() {

        super.onStart();
        mMoviesPresenter.start();
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public void showMovies(ArrayList<TvMovie> movieList) {

        mMoviesAdapter = new MoviesAdapter(movieList);
        mMoviesAdapter.setRecyclerListListener(this);
        mRecycler.setAdapter(mMoviesAdapter);
    }

    @Override
    public void showLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

        // TODO
    }

    @Override
    public void hideError() {

        // TODO
    }

    @Override
    public void showLoadingLabel() {

        Snackbar loadingSnackBar = Snackbar.with(this)
            .text("Loading more films")
            .actionLabel("Cancel")
            .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
            .color(getResources().getColor(R.color.theme_primary))
            .actionColor(getResources().getColor(R.color.theme_accent));

        SnackbarManager.show(loadingSnackBar);
    }

    @Override
    public void hideActionLabel() {

        SnackbarManager.dismiss();
    }

    @Override
    public boolean isTheListEmpty() {

        return (mMoviesAdapter == null) || mMoviesAdapter.getMovieList().isEmpty();

    }

    @Override
    public void appendMovies(List<TvMovie> movieList) {

        mMoviesAdapter.appendMovies(movieList);
    }


    @Override
    public void onClick(View v, int position, float x, float y) {

        Intent movieDetailActivityIntent = new Intent (
            MoviesActivity.this, MovieDetailActivity.class);

        String movieID = mMoviesAdapter.getMovieList().get(position).getId();
        movieDetailActivityIntent.putExtra("movie_id", movieID);

        ImageView coverImage = (ImageView) v.findViewById(R.id.item_movie_cover);
        sPhotoCache.put(0, ((BitmapDrawable)coverImage.getDrawable()).getBitmap());

        if (mMoviesAdapter.isMovieReady(position)) {

            // Perform a SharedElement transition on Lollipop and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                movieDetailActivityIntent.putExtra("movie_position", position);

                // Setup the transition to the detail activity
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, new Pair<>(v, "cover" + position));

                startActivity(movieDetailActivityIntent, options.toBundle());

            // Collect the necessary parameters for scaling
            } else {

                int[] location = {(int) x, (int) y};
                int[] locationAtScreen = new int [2];
                v.getLocationOnScreen(locationAtScreen);

                int finalLocationX = locationAtScreen[0] + location[0];
                int finalLocationY = locationAtScreen[1] + location[1];

                int [] finalLocation = {finalLocationX, finalLocationY};
                movieDetailActivityIntent.putExtra("view_location", finalLocation);
                startActivity(movieDetailActivityIntent);
            }

        } else {

            Toast.makeText(this, "Movie loading, please wait", Toast.LENGTH_SHORT)
                .show();
        }
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public boolean flag;


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = mGridLayoutManager.getChildCount();
            totalItemCount = mGridLayoutManager.getItemCount();
            pastVisiblesItems = mGridLayoutManager.findFirstVisibleItemPosition();

            if((visibleItemCount+pastVisiblesItems) >= totalItemCount && !mMoviesPresenter.isLoading()) {
                mMoviesPresenter.onEndListReached();
            }

            // Is scrolling up
            if (dy > 10) {

                if (!flag) {

                    showToolbar();
                    flag = true;
                }

            // Is scrolling down
            } else if (dy < -10) {

                if (flag) {

                    hideToolbar();
                    flag = false;
                }
            }

        }
    };

    private void showToolbar() {

        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_off));
    }

    private void hideToolbar() {

        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_on));
    }

    @Override
    public void onClick(View v) {

        mNavigationDrawerFragment.openFragment();
    }
}
