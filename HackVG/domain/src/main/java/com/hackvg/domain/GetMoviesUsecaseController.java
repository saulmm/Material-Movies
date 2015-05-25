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

import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.rest.RestDataSource;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetMoviesUsecase}
 */
public class GetMoviesUsecaseController implements GetMoviesUsecase {

    private final RestDataSource mDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;

    /**
     * Constructor of the class.
     *
     * @param uiBus The bus to communicate the domain module and the app module
     * @param dataSource The data source to retrieve the list of movies
     */
    @Inject
    public GetMoviesUsecaseController(RestDataSource dataSource, Bus uiBus) {

        mDataSource = dataSource;
        mUiBus = uiBus;

        mUiBus.register(this);
    }

    @Override
    public void requestPopularMovies() {

        mDataSource.getMoviesByPage(mCurrentPage);
    }

    @Override
    public void sendMoviesToPresenter (MoviesWrapper response) {

        mUiBus.post(response);
    }

    @Override
    public void unRegister() {

        mUiBus.unregister(this);
    }

    @Override
    public void execute() {

        requestPopularMovies();
        mCurrentPage++;
    }
}
