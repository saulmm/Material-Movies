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

public class Belongs_to_collection {
    private String backdrop_path;
    private Number id;
    private String name;
    private String poster_path;

    public String getBackdrop_path() {

        return this.backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {

        this.backdrop_path = backdrop_path;
    }

    public Number getId() {

        return this.id;
    }

    public void setId(Number id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPoster_path() {

        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {

        this.poster_path = poster_path;
    }
}
