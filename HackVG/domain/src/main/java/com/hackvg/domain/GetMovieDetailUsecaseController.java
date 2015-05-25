/**
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
package com.hackvg.domain;

import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ImagesWrapper;
import com.hackvg.model.entities.MovieDetail;
import com.hackvg.model.entities.ReviewsWrapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * This class is an implementation of {@link com.hackvg.domain.GetMovieDetailUsecase}
 */
public class GetMovieDetailUsecaseController implements GetMovieDetailUsecase {

    private final MediaDataSource mMovieDataSource;
    private final String mMovieId;
    private final Bus mUiBus;
    private MovieDetail mMovieDetail;


    public GetMovieDetailUsecaseController(String movieId, Bus uiBus,
        MediaDataSource dataSource) {

        mMovieId        = movieId;
        mUiBus          = uiBus;
        mMovieDataSource= dataSource;

        mUiBus.register(this);
    }

    @Override
    public void requestMovieDetail(String movieId) {

        mMovieDataSource.getDetailMovie(movieId);
    }

    @Subscribe
    @Override
    public void onMovieDetailResponse(MovieDetail movieDetail) {

        mMovieDetail = movieDetail;
        requestMovieImages(mMovieId);
    }

    @Subscribe
    @Override
    public void onMovieReviewsResponse (ReviewsWrapper reviewsWrapper) {

        sendDetailMovieToPresenter(mMovieDetail);

        mUiBus.post(reviewsWrapper);
        mUiBus.unregister(this);
    }

    @Subscribe
    @Override
    public void onMovieImagesResponse(ImagesWrapper imageWrapper) {

        mMovieDetail.setMovieImagesList(imageWrapper.getBackdrops());
        requestMovieReviews(mMovieId);
    }

    @Override
    public void sendDetailMovieToPresenter(MovieDetail response) {

        mUiBus.post(response);
    }

    @Override
    public void requestMovieReviews(String movieId) {

        mMovieDataSource.getReviews(movieId);
    }

    @Override
    public void requestMovieImages(String movieId) {

        mMovieDataSource.getImages(movieId);
    }

    @Override
    public void execute() {

        requestMovieDetail(mMovieId);
    }
}
