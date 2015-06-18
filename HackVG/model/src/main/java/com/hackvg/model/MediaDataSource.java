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
package com.hackvg.model;

public interface MediaDataSource {

    void getMovies();

    void getDetailMovie (String id);

    /**
     * Get the reviews for a particular movie id.
     *
     * @param id movie id
     */
    void getReviews (String id);

    void getConfiguration ();

    /**
     * Get a list of images represented by a MoviesWrapper
     * class
     *
     * @param movieId the movie id
     */
    void getImages (String movieId);
}
