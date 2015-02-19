package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Subscribe;

/**
 * Created by saulmm on 15/02/15.
 */
public class ConfigurationUsecaseController implements ConfigurationUsecase {

    private final String DESIRED_QUALITY = "w780";

    private final MediaDataSource movieDataSource;

    public ConfigurationUsecaseController() {

        this.movieDataSource = RestMovieSource.getInstance();
        BusProvider.getRestBusInstance().register(this);
    }

    @Override
    public void execute() {

        movieDataSource.getConfiguration();
    }

    @Subscribe
    @Override
    public void onConfigurationReceived(ConfigurationResponse configuration) {

        BusProvider.getRestBusInstance().unregister(this);
        configure(configuration);
    }

    @Override
    public void configure(ConfigurationResponse configuration) {

        String url = configuration.getImages().getBase_url();
        String imageQuality = "";

        for (String quality : configuration.getImages().getBackdrop_sizes()) {

            if (quality.equals(DESIRED_QUALITY)) {

                imageQuality = DESIRED_QUALITY;
                break;
            }
        }

        if (imageQuality.equals(""))
            imageQuality = "original";

        url += imageQuality;
        BusProvider.getUIBusInstance().post(url);
   }
}
