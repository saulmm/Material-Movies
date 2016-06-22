/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackvg.android.views.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import com.hackvg.android.di.components.DaggerBasicMoviesUsecasesComponent;
import com.hackvg.android.di.modules.BasicMoviesUsecasesModule;
import com.hackvg.android.mvp.presenters.MoviesPresenter;
import com.hackvg.android.mvp.views.MoviesView;
import com.hackvg.android.utils.RecyclerInsetsDecoration;
import com.hackvg.android.utils.RecyclerViewClickListener;
import com.hackvg.android.views.adapters.MoviesAdapter;
import com.hackvg.model.entities.Movie;
import com.hackvg.model.entities.MoviesWrapper;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;


public class MoviesActivity extends AppCompatActivity implements
    MoviesView, RecyclerViewClickListener {

    public static SparseArray<Bitmap> sPhotoCache = new SparseArray<Bitmap>(1);

    private final static String BUNDLE_MOVIES_WRAPPER = "movies_wrapper";
    private final static String BUNDLE_BACK_TRANSLATION = "background_translation";
    public final static String EXTRA_MOVIE_ID = "movie_id";
    public final static String EXTRA_MOVIE_LOCATION = "view_location";
    public final static String EXTRA_MOVIE_POSITION = "movie_position";
    public final static String SHARED_ELEMENT_COVER = "cover";

    private MoviesAdapter mMoviesAdapter;

    public float mBackgroundTranslation;

    @Nullable
    @BindView(R.id.activity_movies_background_view)
    View mTabletBackground;

    @BindView(R.id.activity_movies_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.activity_movies_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.activity_movies_recycler)
    RecyclerView mRecycler;

    @Inject
    MoviesPresenter mMoviesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeDependencyInjector();
        initializeToolbar();
        initializeRecycler();

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
            .getSerializable(BUNDLE_MOVIES_WRAPPER);

        mMoviesPresenter.onPopularMoviesReceived(moviesWrapper);
    }

    private void initializeRecycler() {
        mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
    }

    private void initializeToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
    }

    private void initializeDependencyInjector() {
        MoviesApp app = (MoviesApp) getApplication();

        DaggerBasicMoviesUsecasesComponent.builder()
            .appComponent(app.getAppComponent())
            .basicMoviesUsecasesModule(new BasicMoviesUsecasesModule())
            .build().inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMoviesAdapter != null) {
            outState.putSerializable(BUNDLE_MOVIES_WRAPPER,
                new MoviesWrapper(mMoviesAdapter.getMovieList()));

            outState.putFloat(BUNDLE_BACK_TRANSLATION, mBackgroundTranslation);
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
            .text(getString(R.string.activity_movies_message_more_films))
            .actionLabel(getString(R.string.action_cancel))
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
        return (mMoviesAdapter == null) ||
            mMoviesAdapter.getMovieList().isEmpty();
    }

    @Override
    public void appendMovies(List<Movie> movieList) {
        mMoviesAdapter.appendMovies(movieList);
    }

    @Override
    public void onClick(View touchedView, int moviePosition, float touchedX, float touchedY) {
        Intent movieDetailActivityIntent = new Intent(
            MoviesActivity.this, MovieDetailActivity.class);

        String movieID = mMoviesAdapter.getMovieList().get(moviePosition).getId();
        movieDetailActivityIntent.putExtra(EXTRA_MOVIE_ID, movieID);
        movieDetailActivityIntent.putExtra(EXTRA_MOVIE_POSITION, moviePosition);

        ImageView mCoverImage = (ImageView) touchedView.findViewById(R.id.item_movie_cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mCoverImage.getDrawable();

        if (mMoviesAdapter.isMovieReady(moviePosition) || bitmapDrawable != null) {

            sPhotoCache.put(0, bitmapDrawable.getBitmap());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                startDetailActivityBySharedElements(touchedView, moviePosition,
                    movieDetailActivityIntent);
            else
                startDetailActivityByAnimation(touchedView, (int) touchedX,
                    (int) touchedY, movieDetailActivityIntent);

        } else {
            Toast.makeText(this, getString(R.string.activity_movies_message_loading_film),
                Toast.LENGTH_SHORT).show();
        }
    }

    private void startDetailActivityByAnimation(View touchedView,
        int touchedX, int touchedY, Intent movieDetailActivityIntent) {

        int[] touchedLocation = {touchedX, touchedY};
        int[] locationAtScreen = new int[2];
        touchedView.getLocationOnScreen(locationAtScreen);

        int finalLocationX = locationAtScreen[0] + touchedLocation[0];
        int finalLocationY = locationAtScreen[1] + touchedLocation[1];

        int[] finalLocation = {finalLocationX, finalLocationY};
        movieDetailActivityIntent.putExtra(EXTRA_MOVIE_LOCATION,
            finalLocation);

        startActivity(movieDetailActivityIntent);
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startDetailActivityBySharedElements(View touchedView,
        int moviePosition, Intent movieDetailActivityIntent) {

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
            this, new Pair<>(touchedView, SHARED_ELEMENT_COVER + moviePosition));

        startActivity(movieDetailActivityIntent, options.toBundle());
    }

    private void showToolbar() {
        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_off));
    }

    private void hideToolbar() {
        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.translate_up_on));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMoviesPresenter.stop();
    }
}
