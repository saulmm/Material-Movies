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

/**
 * Class that represents the configuration in the model layer,
 *
 * The configuration gets the system wide configuration information. Some elements of the API
 * require some knowledge of this configuration data. The purpose of this is to try and keep the
 * actual API responses as light as possible.
 * It is recommended you cache this data every few days.
 */
public class ConfigurationResponse {

    private ConfigurationImages images;
    private String [] change_keys;

    public ConfigurationImages getImages() {

        return images;
    }

    public class ConfigurationImages {

        private String base_url;
        private String secure_base_url;
        private String [] backdrop_sizes;
        private String [] logo_sizes;
        private String [] poster_sizes;
        private String [] profile_sizes;
        private String [] still_sizes;

        public String getBase_url() {

            return base_url;
        }

        public String getSecure_base_url() {

            return secure_base_url;
        }

        public String[] getBackdrop_sizes() {

            return backdrop_sizes;
        }

        public String[] getLogo_sizes() {

            return logo_sizes;
        }

        public String[] getPoster_sizes() {

            return poster_sizes;
        }

        public String[] getProfile_sizes() {

            return profile_sizes;
        }

        public String[] getStill_sizes() {

            return still_sizes;
        }
    }
}


