package com.hackvg.android.views.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.hackvg.android.utils.HackVGClickListener;
import com.hackvg.android.R;
import com.hackvg.android.views.adapters.MoviesAdapter;
import com.hackvg.android.views.fragments.NavigationDrawerFragment;
import com.hackvg.android.mvp.views.PopularMoviesView;
import com.hackvg.android.mvp.presenters.PopularShowsPresenterImpl;
import com.hackvg.android.utils.RecyclerInsetsDecoration;
import com.hackvg.model.entities.TvMovie;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MoviesActivity extends ActionBarActivity implements
    PopularMoviesView, HackVGClickListener, View.OnClickListener {

    private static final int COLUMNS = 2;

    @InjectView(R.id.recycler_popular_movies)   RecyclerView popularMoviesRecycler;
    @InjectView(R.id.activity_main_progress)    ProgressBar progress;
    @InjectView(R.id.activity_main_toolbar)     Toolbar toolbar;

    private MoviesAdapter moviesAdapter;
    public static SparseArray<Bitmap> photoCache = new SparseArray<Bitmap>(1);
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PopularShowsPresenterImpl popularShowsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        // Set the toolbar as the SupportActionbar
        setActionBar(toolbar);
        getActionBar().setTitle("");
        getActionBar().setHomeAsUpIndicator(
            getDrawable(R.drawable.ic_menu_white_24dp));

        toolbar.setNavigationOnClickListener(this);

        popularMoviesRecycler.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        popularMoviesRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        popularMoviesRecycler.setOnScrollListener(recyclerScrollListener);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
            getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));

        popularShowsPresenter = new PopularShowsPresenterImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        popularShowsPresenter.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
        popularShowsPresenter.onStop();
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

        Intent i = new Intent (MoviesActivity.this, MVPDetailActivity.class);
        String movieID = moviesAdapter.getMovieList().get(position).getId();
        i.putExtra("movie_id", movieID);
        i.putExtra("movie_position", position);

        ImageView coverImage = (ImageView) v.findViewById(R.id.item_movie_cover);
        photoCache.put(0, coverImage.getDrawingCache());

        // Setup the transition to the detail activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
            this, new Pair<View, String>(v, "cover" + position));

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

    @Override
    public void onClick(View v) {

        mNavigationDrawerFragment.openFragment();
    }
}
