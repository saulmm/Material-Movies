package com.hackvg.android.test.views.activity;

import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import com.hackvg.android.R;
import com.hackvg.android.views.activities.MoviesActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by saulmm on 17/02/15.
 */
public class MoviesActivityTest extends ActivityInstrumentationTestCase2<MoviesActivity> {

    private MoviesActivity mMoviesActivity;

    public MoviesActivityTest() {

        super(MoviesActivity.class);
    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        // For each test method invocation, the Activity will not actually be created
        // until the first time this method is called.
        mMoviesActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    public void testContainsLoadingIndicator () {

        Espresso.onView(withId(R.id.activity_movies_progress))
            .check(matches(isDisplayed()));
    }

}
