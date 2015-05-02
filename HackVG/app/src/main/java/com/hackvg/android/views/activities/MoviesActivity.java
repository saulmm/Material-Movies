package com.hackvg.android.views.activities;

import android.annotation.TargetApi;
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

import com.hackvg.android.MoviesApp;
import com.hackvg.android.R;
import com.hackvg.android.di.ApplicationModule;
import com.hackvg.android.di.BasicMoviesUsecasesModule;
import com.hackvg.android.di.DaggerAppComponent;
import com.hackvg.android.di.DaggerMoviesComponent;
import com.hackvg.android.di.DomainModule;
import com.hackvg.android.di.MovieUsecasesModule;
import com.hackvg.android.di.MoviesComponent;
import com.hackvg.android.mvp.presenters.MoviesPresenter;
import com.hackvg.android.mvp.views.MoviesView;
import com.hackvg.android.utils.RecyclerInsetsDecoration;
import com.hackvg.android.utils.RecyclerViewClickListener;
import com.hackvg.android.views.adapters.MoviesAdapter;
import com.hackvg.android.views.fragments.NavigationDrawerFragment;
import com.hackvg.model.entities.Movie;
import com.hackvg.model.entities.MoviesWrapper;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class MoviesActivity extends ActionBarActivity implements
    MoviesView, RecyclerViewClickListener, View.OnClickListener {

    public static SparseArray<Bitmap> sPhotoCache = new SparseArray<Bitmap>(1);

    private final static String EXTRA_MOVIES_WRAPPER = "movies_wrapper";

    private MoviesAdapter mMoviesAdapter;
    public float mBackgroundTranslation;

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @InjectView(R.id.activity_movies_toolbar)                   Toolbar mToolbar;
    @InjectView(R.id.activity_movies_progress)                  ProgressBar mProgressBar;
    @InjectView(R.id.activity_movies_recycler)                  RecyclerView mRecycler;

    @Optional @InjectView(R.id.activity_movies_background_view) View mTabletBackground;

    @Inject MoviesPresenter mMoviesPresenter;

    private ImageView mCoverImage;
    private MoviesComponent activityComponent;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initializeDependencyInjector();
        initializeToolbar();
        initializeRecycler();
        initializeDrawer();

        if (savedInstanceState == null)
            mMoviesPresenter.attachView(this);

         else
            initializeFromParams(savedInstanceState);
    }

    @Override
    protected void onStart() {

        super.onStart();
        mMoviesPresenter.start();
    }

    private void initializeFromParams(Bundle savedInstanceState) {

        MoviesWrapper moviesWrapper = (MoviesWrapper) savedInstanceState
            .getSerializable(EXTRA_MOVIES_WRAPPER);

        mMoviesPresenter.onPopularMoviesReceived(moviesWrapper);
    }

    private void initializeRecycler() {

        mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRecycler.setOnScrollListener(recyclerScrollListener);
    }

    private void initializeDrawer() {

        mNavigationDrawerFragment = (NavigationDrawerFragment)
            getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void initializeToolbar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeAsUpIndicator(
            R.drawable.ic_menu_white_24dp);

        mToolbar.setNavigationOnClickListener(this);
    }

    private void initializeDependencyInjector() {

        MoviesApp app = (MoviesApp) getApplication();

        DaggerAppComponent.builder()
            .domainModule(new DomainModule())
            .applicationModule(new ApplicationModule(app))
            .build();

        activityComponent = DaggerMoviesComponent.builder()
            .appComponent(app.getAppComponent())
            .basicMoviesUsecasesModule(new BasicMoviesUsecasesModule())
            .movieUsecasesModule(new MovieUsecasesModule("test"))
            .build();

        activityComponent.inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if (mMoviesAdapter != null) {

            outState.putSerializable("movies_wrapper",
                new MoviesWrapper(mMoviesAdapter.getMovieList()));

            outState.putFloat("background_translation", mBackgroundTranslation);
        }
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public void showMovies(List<Movie> movieList) {

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
    public void appendMovies(List<Movie> movieList) {

        mMoviesAdapter.appendMovies(movieList);
    }


    @Override
    public void onClick(View touchedView, int moviePosition, float touchedX, float touchedY) {

        Intent movieDetailActivityIntent = new Intent (
            MoviesActivity.this, MovieDetailActivity.class);

        String movieID = mMoviesAdapter.getMovieList().get(moviePosition).getId();
        movieDetailActivityIntent.putExtra("movie_id", movieID);
        movieDetailActivityIntent.putExtra("movie_position", moviePosition);

        mCoverImage = (ImageView) touchedView.findViewById(R.id.item_movie_cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mCoverImage.getDrawable();

        if (mMoviesAdapter.isMovieReady(moviePosition) || bitmapDrawable != null) {

            sPhotoCache.put(0, bitmapDrawable.getBitmap());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startSharedElementPosition(touchedView,
                    moviePosition, movieDetailActivityIntent);
            }

            else
                startDetailActivityByAnimation(touchedView,
                    (int) touchedX, (int) touchedY, movieDetailActivityIntent);

        } else {

            Toast.makeText(this, "Movie loading, please wait", Toast.LENGTH_SHORT)
                .show();
        }
    }



    private void startDetailActivityByAnimation(View touchedView,
        int touchedX, int touchedY, Intent movieDetailActivityIntent) {

        int[] touchedLocation = {touchedX, touchedY};
        int[] locationAtScreen = new int [2];
        touchedView.getLocationOnScreen(locationAtScreen);

        int finalLocationX = locationAtScreen[0] + touchedLocation[0];
        int finalLocationY = locationAtScreen[1] + touchedLocation[1];

        int [] finalLocation = {finalLocationX, finalLocationY};
        movieDetailActivityIntent.putExtra("view_location",
            finalLocation);

        startActivity(movieDetailActivityIntent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startSharedElementPosition(View touchedView,
        int moviePosition, Intent movieDetailActivityIntent) {

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
            this, new Pair<>(touchedView, "cover" + moviePosition));

        startActivity(movieDetailActivityIntent, options.toBundle());
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public boolean flag;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = mRecycler.getLayoutManager().getChildCount();
            totalItemCount = mRecycler.getLayoutManager().getItemCount();
            pastVisiblesItems = ((GridLayoutManager) mRecycler.getLayoutManager()).findFirstVisibleItemPosition();

            if((visibleItemCount+pastVisiblesItems) >= totalItemCount && !mMoviesPresenter.isLoading()) {
                mMoviesPresenter.onEndListReached();
            }

            if (mTabletBackground != null) {

                mBackgroundTranslation = mTabletBackground.getY() - (dy / 2);
                mTabletBackground.setTranslationY(mBackgroundTranslation);
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

    @Override
    protected void onStop() {

        super.onStop();
        mMoviesPresenter.stop();
    }
}
