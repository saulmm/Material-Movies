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
package com.hackvg.model.entities;


import java.util.List;

public class ImagesWrapper {

    private int id;
    private List<MovieImage> backdrops;

    public int getId() {

        return id;
    }

    public List<MovieImage> getBackdrops() {

        return backdrops;
    }

    @SuppressWarnings("UnusedDeclaration")
    public class MovieImage {

        private int width;
        private int height;
        private String file_path;
        private String iso_639_1;
        private float aspect_ratio;
        private float vote_average;
        private int vote_count;

        public String getFile_path() {

            return file_path;
        }

        public int getWidth() {

            return width;
        }

        public int getHeight() {

            return height;
        }

        public String getIso_639_1() {

            return iso_639_1;
        }

        public float getAspect_ratio() {

            return aspect_ratio;
        }

        public float getVote_average() {

            return vote_average;
        }

        public int getVote_count() {

            return vote_count;
        }

    }

}
