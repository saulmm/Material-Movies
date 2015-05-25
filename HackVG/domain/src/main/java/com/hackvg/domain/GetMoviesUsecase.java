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

/**
 * Representation of an use case to get the most popular movies
 */
@SuppressWarnings("UnusedDeclaration")
public interface GetMoviesUsecase extends Usecase {


    /**
     * Request datasource the most popular movies
     */
    public void requestPopularMovies();

    /**
     * Sends the PopularMoviesApiResponse thought the communication system
     * to be received by the presenter in another module
     *
     * @param response the response containing a list with movies
     */
    public void sendMoviesToPresenter (MoviesWrapper response);

    public void unRegister ();
}
