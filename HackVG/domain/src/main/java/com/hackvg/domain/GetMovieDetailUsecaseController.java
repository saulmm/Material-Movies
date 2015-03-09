package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
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

    /**
     * Constructor of the class.
     *
     * @param movieId The id of the movie to retrieve the details
     * @param uiBus The bus to communicate the domain module and the app module
     * @param dataSource The data source to retrieve the details of the movie
     */
    public GetMovieDetailUsecaseController(String movieId,
        Bus uiBus, MediaDataSource dataSource) {

        if (movieId == null)
            throw new IllegalArgumentException("Movie Id cannot be null");

        if (uiBus == null)
            throw new IllegalArgumentException("UiBus cannot be null");

        if (dataSource == null)
            throw  new IllegalArgumentException("MediaData source cannot be null");

        mMovieId = movieId;
        mUiBus = uiBus;
        mMovieDataSource = dataSource;

        BusProvider.getRestBusInstance().register(this);
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
        BusProvider.getRestBusInstance().unregister(this);

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
