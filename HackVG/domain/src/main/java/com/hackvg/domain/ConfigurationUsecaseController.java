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
import com.hackvg.model.entities.ConfigurationResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


@SuppressWarnings("FieldCanBeLocal")
public class ConfigurationUsecaseController implements ConfigurationUsecase {

    private final String QUALITY_DESIRED    = "w780";
    private final String QUALITY_ORIGINAL   = "original";

    private final MediaDataSource mMediaDataSource;
    private final Bus mMainBus;

    @Inject
    public ConfigurationUsecaseController(MediaDataSource mediaDataSource, Bus mainBus) {

        mMediaDataSource    = mediaDataSource;
        mMainBus            = mainBus;

        mMainBus.register(this);
    }

    @Override
    public void requestConfiguration () {

        mMediaDataSource.getConfiguration();
    }

    @Override
    public void execute() {

        requestConfiguration();
    }

    @Subscribe
    @Override
    public void onConfigurationReceived(ConfigurationResponse configuration) {

        mMainBus.unregister(this);
        configureImageUrl(configuration);
    }

    public void configureImageUrl (ConfigurationResponse configurationResponse) {

        String url;

        if (configurationResponse.getImages() != null) {

            String imageQuality = "";
            url = configurationResponse.getImages().getBase_url();

            for (String quality : configurationResponse.getImages().getBackdrop_sizes()) {

                if (quality.equals(QUALITY_DESIRED)) {

                    imageQuality = QUALITY_DESIRED;
                    break;
                }
            }

            if (imageQuality.equals(""))
                imageQuality = QUALITY_ORIGINAL;

            url += imageQuality;
            sendConfiguredUrlToPresenter(url);
        }
    }


    @Override
    public void sendConfiguredUrlToPresenter (String url) {

        mMainBus.post(url);
   }
}
