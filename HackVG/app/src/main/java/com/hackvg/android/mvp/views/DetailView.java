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
package com.hackvg.android.mvp.views;


import android.graphics.Bitmap;

import com.hackvg.model.entities.Review;

import java.util.List;


public interface DetailView extends BaseView {

    void showFilmCover(Bitmap bitmap);

    public void setName (String title);

    public void setDescription(String description);

    public void setHomepage (String homepage);

    public void setCompanies (String companies);

    public void setTagline(String tagline);

    public void finish(String cause);

    public void showConfirmationView ();

    public void animateConfirmationView ();

    public void startClosingConfirmationView();

    public void showReviews(List<Review> results);

    void showMovieImage(String url);
}
