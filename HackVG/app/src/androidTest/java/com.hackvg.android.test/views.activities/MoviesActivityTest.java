/*
 * Copyright (C) 2015 Saúl Molinero.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackvg.android.test.views.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.hackvg.android.R;
import com.hackvg.android.views.activities.MoviesActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

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

    public void testRecyclerViewIsShown () throws InterruptedException {

        // Work around, it would be better use Espresso Idling resources
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.activity_movies_recycler))
            .check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.activity_movies_progress))
            .check(matches(not(isDisplayed())));
    }

    public void testDetailActivityOpen () throws InterruptedException {

        // Work around, it would be better use Espresso Idling resources
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.activity_movies_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));

        Espresso.onView(withId(R.id.activity_detail_scroll))
            .check(matches(isDisplayed()));
    }
}
